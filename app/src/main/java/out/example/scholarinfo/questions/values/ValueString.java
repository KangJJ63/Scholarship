package out.example.scholarinfo.questions.values;

import androidx.annotation.NonNull;

public class ValueString implements IValue {

    private String value;

    public ValueString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean isContains(IValue obj) {
        if(obj instanceof ValueString) {
            ValueString value = (ValueString) obj;

            return value.value.contains(this.value);
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return value;
    }
}
