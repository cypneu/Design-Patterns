package builder;

import builder.JSONTypes.JSONFloat;
import builder.JSONTypes.JSONInteger;
import builder.JSONTypes.JSONObject;
import builder.JSONTypes.JSONString;

import java.util.HashMap;

public interface Builder {

    void setObject(JSONObject jsonObject);

    JSONObject buildJSONObject(HashMap<JSONString, Object> hashMap);

    void setString(JSONString jsonString);

    JSONString buildJSONString(String json);

    void setInteger(JSONInteger jsonInteger);

    JSONInteger buildJSONInteger(int val);

    void setFloat(JSONFloat jsonFloat);

    JSONFloat buildJSONFloat(float val);

}
