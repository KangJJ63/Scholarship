package out.example.scholarinfo;

import androidx.appcompat.app.AppCompatActivity;
import out.example.scholarinfo.questions.IQuestionView;
import out.example.scholarinfo.questions.values.IValue;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DiagnoseResultActivity extends AppCompatActivity {

    ScholarInfoApplication application;
    ListView listView;
    PrizeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose_result);

        application = (ScholarInfoApplication) getApplication();

        listView = findViewById(R.id.diagnose_result_listView);

        List<Prize> prizes = new ArrayList<>();


        for(Prize prize : application.prizes) {
            boolean isAccord = true; // prize에 대한 모든 조건에 부합하는지 고른다.

            Log.d("scholarInfo", " === prize " + prize.getName() + " === ");
            for(Pair<String, IValue> questions : application.lastAnswer) {
                IValue value = prize.getValue(questions.first);

                if(value != null) {
                    Log.d("scholarInfo", "값: " + value+ ", " + questions.second);
                    isAccord = isAccord & prize.getValue(questions.first).isContains(questions.second);
                }
            }

            if(isAccord) prizes.add(prize); // 부합했다면 필터링된 리스트에 넣어준다
        }

        ScholarInfoApplication.setActionBarName(getApplicationContext(), getSupportActionBar(), "진단 결과");

        Toast.makeText(getApplicationContext(), "진단 수: " + application.prizes.size() + ", " + prizes.size(), Toast.LENGTH_SHORT).show();

        adapter = new PrizeAdapter(getApplicationContext(), prizes); // 그대로 출력
        listView.setAdapter(adapter);
    }
}
