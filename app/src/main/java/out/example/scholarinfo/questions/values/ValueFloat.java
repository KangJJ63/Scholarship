package out.example.scholarinfo.questions.values;

public class ValueFloat implements IValue {
    private float value;

    public ValueFloat(float value){
        this.value=value;
    }

    public float getValue() {
        return value;
    }
    @Override
    public boolean isContains(IValue obj) {
        if(obj instanceof ValueFloat) {
            return value <= ((ValueFloat)obj).value;
        }
        return false;
    }

    @Override
    public String toString() {
        return value+"이상";
    }
}
