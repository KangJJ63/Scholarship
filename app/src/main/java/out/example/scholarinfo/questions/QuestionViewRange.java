package out.example.scholarinfo.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import out.example.scholarinfo.R;
import out.example.scholarinfo.questions.values.IValue;
import out.example.scholarinfo.questions.values.ValueInt;
import out.example.scholarinfo.questions.values.ValueIntRange;

public class QuestionViewRange extends IQuestionView {

    private ValueIntRange range;

    private SeekBar seekBar;

    public QuestionViewRange(ValueIntRange range) { // 1, 10
        this.range = range;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_range, container, false);

        seekBar = view.findViewById(R.id.view_range_seekBar);

        final TextView textView = view.findViewById(R.id.view_range_value);
        textView.setText(String.valueOf(range.getStart())); // 기본 값 설정

        seekBar.setMax(range.getFinish() - range.getStart()); // setMin이 안되므로 max - min 사이의 값을 지정할 수 있게 하고 여기에 + min을 해주는 식으로 구현한다
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText(String.valueOf(getSelectedValue())); // 값을 몇 선택했는지 보여준다
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

    private int getSelectedValue() {
        return seekBar.getProgress() + range.getStart(); // setMin이 안되므로 max - min 사이의 값을 지정할 수 있게 하고 여기에 + min을 해주는 식으로 구현한다
    }

    @Override // 선택한 값 리턴
    public IValue getSelectedItem() {
        return new ValueInt(getSelectedValue());
    }

    @Override // 타입
    public String getType() {
        return "range";
    }
}
