package builder;

import builder.JSONTypes.JSONFloat;
import builder.JSONTypes.JSONInteger;
import builder.JSONTypes.JSONObject;
import builder.JSONTypes.JSONString;

import java.util.HashMap;

public class JSONBuilder implements Builder {
    private Object resJSON;


    public Object getResult() {
        return this.resJSON;
    }

    @Override
    public void setObject(JSONObject jsonObject) {
        this.resJSON = jsonObject;
    }

    @Override
    public JSONObject buildJSONObject(HashMap<JSONString, Object> hashMap) {
        return new JSONObject(hashMap);
    }

    public void setString(JSONString jsonString) {
        this.resJSON = jsonString;
    }

    @Override
    public JSONString buildJSONString(String string) {
        return new JSONString(string);
    }

    @Override
    public void setInteger(JSONInteger jsonInteger) {
        this.resJSON = jsonInteger;
    }

    @Override
    public JSONInteger buildJSONInteger(int val) {
        return new JSONInteger(val);
    }

    @Override
    public void setFloat(JSONFloat jsonFloat) {
        this.resJSON = jsonFloat;
    }

    @Override
    public JSONFloat buildJSONFloat(float val) {
        return new JSONFloat(val);
    }
}
