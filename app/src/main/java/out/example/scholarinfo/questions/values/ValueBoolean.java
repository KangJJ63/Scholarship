package out.example.scholarinfo.questions.values;

import androidx.annotation.NonNull;

public class ValueBoolean implements IValue {

    private boolean value;

    public ValueBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public boolean isContains(IValue obj) {
        if(obj instanceof ValueBoolean) {
            ValueBoolean value = (ValueBoolean) obj;

            return this.value == value.value;
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        if(value)
        return "O";
        else
            return "X";
    }
}
