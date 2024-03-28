package out.example.scholarinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_KEY = 100;


    ScholarInfoApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (ScholarInfoApplication) getApplication();

        // 버튼 클릭시 지정한 액티비티로 이동한다
        View prizelist = findViewById(R.id.main_prizelist);
        View diagnose = findViewById(R.id.main_diagnose);
        View bookmarklist = findViewById(R.id.main_prizebookmark);

        prizelist.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ScholarViewActivity.class);
            startActivity(intent);
        });
        diagnose.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DiagnoseActivity.class);
            startActivity(intent);
        });
        bookmarklist.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ScholarSelectedActivity.class);
            startActivity(intent);
        });

        // 퍼미션이 없으면 요구한다
        List<String> permissions = Arrays.asList(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(!ScholarInfoApplication.isAllPermissionGranted(this, permissions)) {
            ScholarInfoApplication.requestPermission(this, permissions, REQUEST_KEY);
        } else {
            application.getValues();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode) {
            case REQUEST_KEY:
                if(ScholarInfoApplication.isAllPermissionGranted(grantResults)) {
                    Toast.makeText(getApplicationContext(), "모든 권한을 부여받았습니다.", Toast.LENGTH_SHORT).show();
                    application.getValues();
                } else {
                    Toast.makeText(getApplicationContext(), "모든 권한을 허용해야 서비스를 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }
}
