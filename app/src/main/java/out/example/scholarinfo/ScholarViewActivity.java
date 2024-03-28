package out.example.scholarinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ScholarViewActivity extends AppCompatActivity {

    private int color = Color.rgb(221, 221, 221);

    ListView listView;
    Button in;
    Button out;

    PrizeAdapter adapter;

    ScholarInfoApplication application;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholar_view);


        application = (ScholarInfoApplication) getApplication();

        listView = findViewById(R.id.scholar_view_listview);
        in = findViewById(R.id.scholar_view_in);
        out = findViewById(R.id.scholar_view_out);

        adapter = new PrizeAdapter(getApplicationContext(), null);
        adapter.setPrizes(application.prizes, true);
        listView.setAdapter(adapter);

        in.setOnClickListener(it->{ // 교내 선택시 out 버튼은 회색으로 만들고 자기 버튼은 화려한 색으로 바꾼다 그리고 isIn이 true이면 교내 목록만 필터링한다
            adapter.setPrizes(application.prizes, true);
            in.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            out.setBackgroundColor(color);
        });
        out.setOnClickListener(it->{
            adapter.setPrizes(application.prizes, false);
            in.setBackgroundColor(color);
            out.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        });
        ScholarInfoApplication.setActionBarName(getApplicationContext(), getSupportActionBar(), "장학금 목록");

    }
}
