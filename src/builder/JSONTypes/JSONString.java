package builder.JSONTypes;

public class JSONString {
    private final String val;
    public JSONString(String string) {
        this.val = string;
    }

    @Override
    public String toString() {
        return "\"" + this.val + "\"";
    }
}
