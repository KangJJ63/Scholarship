package out.example.scholarinfo.questions.values;

import androidx.annotation.NonNull;

public class ValueIntRange implements IValue {

    private int start;
    private int finish;

    public ValueIntRange(int start, int finish) {
        this.start = start;
        this.finish = finish;
    }

    public int getStart() {
        return start;
    }

    public int getFinish() {
        return finish;
    }

    @Override
    public boolean isContains(IValue obj) {
        if(obj instanceof ValueIntRange) {
            ValueIntRange value = (ValueIntRange) obj;
            return start <= value.start && value.finish <= finish;
        }
        if(obj instanceof ValueInt) {
            ValueInt value = (ValueInt) obj;
            return start <= value.getValue() && value.getValue() <= finish;
        }

        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return start + " ~ " + finish;
    }
}
