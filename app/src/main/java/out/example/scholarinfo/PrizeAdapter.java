package out.example.scholarinfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PrizeAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;

    private List<Prize> prizes;

    public PrizeAdapter(Context context, List<Prize> prizes) {
        this.prizes = prizes;
        this.context = context;
    }

    public void setPrizes(List<Prize> temp, boolean isIn) {
        prizes = new ArrayList<>();

        for(Prize prize : temp) {
            if(prize.isIn() == isIn) {
                prizes.add(prize);
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int position = (Integer)v.getTag();
        Prize prize = prizes.get(position);
        //Toast.makeText(context, String.valueOf(prize.getName()), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context,ScholarInfoViewActivity.class);
        intent.putExtra("id",prize.getId());

        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    public void setPrizes(List<Prize> temp) {
        prizes = new ArrayList<>();
        prizes.addAll(temp);

        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return prizes.size();
    }

    @Override
    public Object getItem(int i) {
        return prizes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return prizes.get(i).hashCode();
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) { // prize를 받아와 listView를 업데이트 해준다
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_prize, viewGroup, false);
        }

        Prize prize = prizes.get(i);

        ImageView imageView = view.findViewById(R.id.fragment_prize_img);
        TextView title = view.findViewById(R.id.fragment_prize_title);
        TextView term = view.findViewById(R.id.fragment_prize_term);
        CheckBox checkBox = view.findViewById(R.id.fragment_prize_checked_img);
        view.setTag(i);
        view.setOnClickListener(this);
        imageView.setImageURI(prize.getImageRoute());
        title.setText(prize.getName());
        term.setText(prize.getStartData() + " - " + prize.getEndData());

        SharedPreferences pref = context.getSharedPreferences("prize", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        checkBox.setChecked(pref.getBoolean("prize" + prize.getId(), false));

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = checkBox.isChecked(); // 관심 목록 등록 선택 여부를 얻어온다

                edit.putBoolean("prize" + prize.getId(), isChecked);
                edit.commit();

                Log.d("scholarInfo", "set: " + prize.getId() + ", " + isChecked);


                if(isChecked) {
                    Toast.makeText(context, "관심 목록으로 등록되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "관심 목록에서 해제되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }





}
