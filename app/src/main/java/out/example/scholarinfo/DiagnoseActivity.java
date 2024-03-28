package out.example.scholarinfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import out.example.scholarinfo.questions.IQuestionView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DiagnoseActivity extends AppCompatActivity {

    TextView index;
    TextView title;
    Button next;

    ScholarInfoApplication application;

    int lastIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);

        application = (ScholarInfoApplication) getApplication();

        index = findViewById(R.id.diagnose_number);
        title = findViewById(R.id.diagnose_title);
        next = findViewById(R.id.diagnose_next);

        ScholarInfoApplication.setActionBarName(getApplicationContext(), getSupportActionBar(), "진단하기"); // 상단 액션바 타이틀 지정

        application.lastAnswer.clear(); // 이전에 답변한 적이 있으면 초기화한다
        setFragment(0); // 먼저 첫번째 질문부터 물어본다

        next.setOnClickListener(e-> {
            setFragment(lastIndex + 1); // 다음 질문을 물어본다
        });
    }

    public void setFragment(int index) {
        if(lastIndex != -1) {
            Pair<String, IQuestionView> pair = application.questions.get(lastIndex);
            application.lastAnswer.add(new Pair<>(pair.first, pair.second.getSelectedItem())); // 이전 질문을 잠시 저장해둔다.
        }
        if(index < application.questions.size()) {


            lastIndex = index;
            String title = application.questions.get(index).first;
            IQuestionView iQuestionView = application.questions.get(index).second;

            this.title.setText(title);
            this.index.setText(String.valueOf(index + 1)); // 제목과 번호 보여주기

            FragmentManager fm = getSupportFragmentManager(); // IQuestionView 프래그먼트 보여주기
            FragmentTransaction fragmentTransaction = fm.beginTransaction();

            fragmentTransaction.replace(R.id.dignose_fragment, iQuestionView);
            fragmentTransaction.commit();
        } else { // 할 질문이 없으면 결과창으로 넘어간다
            Intent intent = new Intent(getApplicationContext(), DiagnoseResultActivity.class);
            startActivity(intent);

            finish();
        }
    }
}
