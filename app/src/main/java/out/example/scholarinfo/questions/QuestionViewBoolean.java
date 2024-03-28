package out.example.scholarinfo.questions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import out.example.scholarinfo.R;
import out.example.scholarinfo.questions.values.IValue;
import out.example.scholarinfo.questions.values.ValueBoolean;

public class QuestionViewBoolean extends IQuestionView {

    private CheckBox checkBox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_boolean, container, false);

        checkBox = view.findViewById(R.id.view_boolean_value);

        return view;
    }

    @Override
    public IValue getSelectedItem() {
        return new ValueBoolean(checkBox.isChecked());
    }

    @Override
    public String getType() {
        return "boolean";
    }

}
