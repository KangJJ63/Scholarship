package out.example.scholarinfo.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import out.example.scholarinfo.R;
import out.example.scholarinfo.questions.values.IValue;
import out.example.scholarinfo.questions.values.ValueInt;

public class QuestionViewInt extends IQuestionView {

    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_int, container, false);

        editText = view.findViewById(R.id.view_int_value);

        return view;
    }

    @Override
    public IValue getSelectedItem() {
        return new ValueInt(Integer.parseInt(editText.getText().toString()));
    }

    @Override
    public String getType() {
        return "int";
    }
}
