package builder.JSONTypes;

public class JSONInteger {
    private final int val;
    public JSONInteger(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return Integer.toString(this.val);
    }
}
