package builder.JSONTypes;

public class JSONFloat {
    private final float val;
    public JSONFloat(float val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return Float.toString(val);
    }
}
