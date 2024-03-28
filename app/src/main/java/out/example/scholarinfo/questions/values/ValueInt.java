package out.example.scholarinfo.questions.values;

import androidx.annotation.NonNull;

public class ValueInt implements IValue {
    private int value;

    public ValueInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean isContains(IValue obj) {
        if(obj instanceof ValueInt) {
            return value == ((ValueInt)obj).value;
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return String.valueOf(value);
    }
            }
