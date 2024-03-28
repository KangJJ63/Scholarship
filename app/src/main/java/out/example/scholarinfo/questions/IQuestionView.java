package out.example.scholarinfo.questions;

import android.view.View;

import androidx.fragment.app.Fragment;
import out.example.scholarinfo.questions.values.IValue;

public abstract class IQuestionView extends Fragment { // 원하는 자료형의 질의를 만들 수 있는 fragment
    public abstract IValue getSelectedItem();

    public abstract String getType();
}
