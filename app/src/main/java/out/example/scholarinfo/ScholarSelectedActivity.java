package out.example.scholarinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ScholarSelectedActivity extends AppCompatActivity {

    private ListView listView;
    private PrizeAdapter adapter;

    private ScholarInfoApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar_selected);

        application = (ScholarInfoApplication) getApplication();

        listView = findViewById(R.id.scholar_selected_listview);

        List<Prize> prizesFiltered = new ArrayList<>();

        SharedPreferences pref = getSharedPreferences("prize", MODE_PRIVATE);

        for(Prize prize : application.prizes) { // 관심 목록으로 등록된 장학금만 따로 필터링해서
            boolean selected = pref.getBoolean("prize" + prize.getId(), false);

            Log.d("scholarInfo", prize.getId() + ": " + selected);
            if(selected) {
                prizesFiltered.add(prize);
            }
        }

        ScholarInfoApplication.setActionBarName(getApplicationContext(), getSupportActionBar(), "관심 장학금 등록 목록");

        adapter = new PrizeAdapter(getApplicationContext(), prizesFiltered); // 보여준다
        listView.setAdapter(adapter);
    }
}
