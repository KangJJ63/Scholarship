package out.example.scholarinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.HashMap;

import out.example.scholarinfo.questions.IQuestionView;
import out.example.scholarinfo.questions.values.IValue;


public class ScholarInfoViewActivity extends AppCompatActivity {

    private ScholarInfoApplication application;
    String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application=(ScholarInfoApplication)getApplication();
        setContentView(R.layout.scholar_info_view);
        ImageView imageView =(ImageView)findViewById(R.id.imageView);
        TextView date=(TextView)findViewById(R.id.scholar_view_date);
        TextView isIn=(TextView)findViewById(R.id.scholar_view_isIn);
        TextView appway=(TextView)findViewById(R.id.scholar_view_appway);
        TextView amount=(TextView)findViewById(R.id.scholar_view_amount);
        TextView pmethod=(TextView)findViewById(R.id.scholar_view_pmethod);
        TextView doc=(TextView)findViewById(R.id.scholar_view_doc);
        TextView subplace=(TextView)findViewById(R.id.scholar_view_subplace);
        TextView url=(TextView)findViewById(R.id.scholar_view_url);
        TextView qual=(TextView)findViewById(R.id.scholar_view_qual);

        Intent intent=getIntent();
        long idx = intent.getLongExtra("id",0);

        for(Prize prize : application.prizes)
        {
            if(prize.getId()==idx)
            {
                imageView.setImageURI(prize.getImageRoute());
                name=prize.getName();
                date.setText(prize.getStartData()+" - "+prize.getEndData());
                if(prize.isIn()) isIn.setText("교내");
                else isIn.setText("교외");
                appway.setText(prize.getAppway());
                amount.setText(prize.getAmount());
                pmethod.setText(prize.getPmethod());

                String result="";
                for(String key : prize.getValues().keySet())
                {
                   result=result.concat(key+" : "+prize.getValue(key)+"\n");
                }
                qual.setText(result);
                doc.setText(prize.getDoc());
                subplace.setText(prize.getSubplace());
                url.setText(prize.getUrl());
                Linkify.addLinks(url,Linkify.WEB_URLS); //링크설정
                break;
            }

        }

        ScholarInfoApplication.setActionBarName(getApplicationContext(), getSupportActionBar(), name); // 상단 액션바 타이틀 지정
    }

}
