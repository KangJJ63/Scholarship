package out.example.scholarinfo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import out.example.scholarinfo.questions.IQuestionView;
import out.example.scholarinfo.questions.QuestionViewBoolean;
import out.example.scholarinfo.questions.QuestionViewFloat;
import out.example.scholarinfo.questions.QuestionViewInt;
import out.example.scholarinfo.questions.QuestionViewRange;
import out.example.scholarinfo.questions.QuestionViewString;
import out.example.scholarinfo.questions.values.IValue;
import out.example.scholarinfo.questions.values.ValueBoolean;
import out.example.scholarinfo.questions.values.ValueFloat;
import out.example.scholarinfo.questions.values.ValueInt;
import out.example.scholarinfo.questions.values.ValueIntRange;
import out.example.scholarinfo.questions.values.ValueString;

public class ScholarInfoApplication extends Application {

    public List<Prize> prizes = new ArrayList<>();
    public List<Pair<String, IQuestionView>> questions = new ArrayList<>();
    public List<Pair<String, IValue>> lastAnswer = new ArrayList<>();
    private DatabaseReference database;
    private StorageReference storage;


    public boolean completed = false;

    public void getValues() { // 권한을 받았거나 이미 받았을때 파이어베이스에서 데이터와 사용할 사진을 불러온다.
        Toast.makeText(getApplicationContext(), "정보와 이미지를 받아오는 중입니다. 받아오는 도중에 이미지가 제대로 표시되지 않을 수 있습니다.", Toast.LENGTH_LONG).show();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                new Thread() {
                    public void run() {
                        prizes.clear();
                        Iterable<DataSnapshot> prizesSnapshots = dataSnapshot.child("prizes").getChildren();
                        Iterable<DataSnapshot> questionSnapshots = dataSnapshot.child("questions").getChildren();

                /*
                구조
                main
                    prizes
                        제목
                            data
                                id = int
                                start = String
                                finish = String
                                isIn = boolean
                            questions
                                문제이름: range, 5, 10
                    questions
                        문제이름: range, 1, 10
                 */

                        for(DataSnapshot questionSnapshot : questionSnapshots) { // 질문 불러오기
                            String name = questionSnapshot.getKey();
                            String[] split = ((String)questionSnapshot.getValue()).split(", ");

                            switch(split[0]) {
                                case "int":
                                    questions.add(new Pair<>(name, new QuestionViewInt()));
                                    break;
                                case "float":
                                    questions.add(new Pair<>(name,new QuestionViewFloat()));
                                    break;
                                case "string":
                                    questions.add(new Pair<>(name, new QuestionViewString()));
                                    break;
                                case "range":
                                    questions.add(new Pair<>(name, new QuestionViewRange(new ValueIntRange(Integer.parseInt(split[1]), Integer.parseInt(split[2])))));
                                    break;
                                case "boolean":
                                    questions.add(new Pair<>(name, new QuestionViewBoolean()));
                                    break;
                                default:
                                    Log.d("scholarInfo", split[0] + "에 대한 값이 없습니다");
                                    break;
                            }
                        }


                        for(DataSnapshot prizeSnapshot : prizesSnapshots) { // 장학금 불러오기
                            String prizeName = prizeSnapshot.getKey();
                            Prize prize = new Prize(
                                    ((long) prizeSnapshot.child("data").child("id").getValue()), prizeName,
                                    (String) prizeSnapshot.child("data").child("start").getValue(),
                                    (String) prizeSnapshot.child("data").child("finish").getValue(),
                                    (boolean) prizeSnapshot.child("data").child("isIn").getValue(),
                                    (String) prizeSnapshot.child("data").child("appway").getValue(),
                                    (String) prizeSnapshot.child("data").child("amount").getValue(),
                                    (String) prizeSnapshot.child("data").child("doc").getValue(),
                                    (String) prizeSnapshot.child("data").child("subplace").getValue(),
                                    (String) prizeSnapshot.child("data").child("pmethod").getValue(),
                                    (String) prizeSnapshot.child("data").child("url").getValue());

                            try {
                                try {
                                    //tempFile을 이용하면 앱이 종료될때 파일이 지워진다
                                    File localFile = File.createTempFile("pic" + prize.getId(), "jpg");

                                    //파일을 다운로드하는 Task 생성, 비동기식으로 진행

                                    final FileDownloadTask fileDownloadTask = storage.child("pic" + prize.getId() + ".jpg").getFile(localFile);
                                    fileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            prize.setImageRoute(Uri.fromFile(localFile));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            Toast.makeText(getApplicationContext(), "다운로드에 실패했습니다: " + prize.getId() + ": " + prizeName, Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        //진행상태 표시
                                        public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch(Exception e){
                                e.printStackTrace();
                            }

                            for(DataSnapshot problemSnapshot : prizeSnapshot.child("questions").getChildren()) { // 장학금과 관련된 조건 불러오기
                                String name = problemSnapshot.getKey();
                                String[] split = ((String)problemSnapshot.getValue()).split(", ");
                                switch(split[0]) {
                                    case "int":
                                        prize.addValue(name, new ValueInt(Integer.parseInt(split[1])));
                                        break;
                                    case "float":
                                        prize.addValue(name,new ValueFloat(Float.parseFloat(split[1])));
                                        break;
                                    case "string":
                                        prize.addValue(name,new ValueString(split[1]));
                                        break;
                                    case "range":
                                        ValueIntRange range = new ValueIntRange(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                                        prize.addValue(name, range);
                                        break;
                                    case "boolean":
                                        prize.addValue(name,  new ValueBoolean(Boolean.parseBoolean(split[1])));
                                    default:
                                        Log.d("scholarInfo", split[0] + "에 대한 값이 없습니다");
                                        break;
                                }
                            }
                            prizes.add(prize);
                        }

                        completed = true;
                    }
                }.start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
                Toast.makeText(getApplicationContext(), "값 받기 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();

        database = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance("gs://janghack-67a74.appspot.com").getReference(); // 우선 파이어베이스 관련 정보를 초기화한다

    }


    public static void setActionBarName(Context context, ActionBar ab, String name) {

        // 커스텀 뷰 사용 ON / 기본 title 사용 OFF
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        //ListView처럼 inflate후 안에 있는 textview 수정
        View view = LayoutInflater.from(context).inflate(R.layout.view_actionbar, null);
        TextView textView = view.findViewById(R.id.actionbar_text);
        textView.setText(name);

        ab.setCustomView(view);
    }


    public static boolean isAllPermissionGranted(Activity activity, List<String> permissions) { // 모든 퍼미션을 부여받았는지 검사하는 코드
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED && !ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permission)) {
                return false;
            }
        }

        return true;
    }

    public static void requestPermission(Activity activity, List<String> permissions, int key) { // 퍼미션을 요구하는 코드
        List<String> toRequest = new ArrayList<>();

        // 아직 권한을 부여받지 않은 것만 추려내서
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED && !ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permission)) {
                toRequest.add(permission);
            }
        }

        // 요청
        if (toRequest.size() > 0) {
            ActivityCompat.requestPermissions(activity, (String[]) permissions.toArray(),
                    key);
        }
    }

    public static boolean isAllPermissionGranted(int[] grantResults) { // 모든 퍼미션을 부여받았는지 검사하는 코드
        for(int grantResult : grantResults) {
            if(grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }

        return true;
    }
}
