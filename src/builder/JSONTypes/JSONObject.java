package builder.JSONTypes;

import java.util.HashMap;

public class JSONObject {
    private HashMap<JSONString, Object> object;

    public JSONObject(HashMap<JSONString, Object> object) {
        this.object = object;
    }

    public HashMap<JSONString, Object> getObject() {
        return this.object;
    }

    public void put(JSONString key, Object value) {
        object.put(key, value);
    }

    @Override
    public String toString() {
        return this.object.toString();
    }
}
