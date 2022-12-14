package builder;

import builder.JSONTypes.JSONFloat;
import builder.JSONTypes.JSONInteger;
import builder.JSONTypes.JSONObject;
import builder.JSONTypes.JSONString;
import com.google.gson.Gson;

import java.util.HashMap;

public class JSONParser {
    private int idx;
    private char nextChar;

    public void parseJSON(Builder jsonBuilder, String json) {
        if (!isStringValidJson(json)) {
            System.out.println("\u001B[31m" + "Provided string is not a valid JSON!" + "\u001B[0m");
            return;
        }

        handleParsing(jsonBuilder, json);
        System.out.println("\u001B[32m" + "Successfully parsed provided string to JSON!" + "\u001B[0m");
    }

    private Object parseObject(Builder jsonBuilder, String json) {
        JSONObject jsonObject = jsonBuilder.buildJSONObject(new HashMap<>());

        nextChar = getNextChar(json);
        if (nextChar != '"') {
            skipWhitespaces(json);
            if (nextChar == '}') {
                nextChar = getNextChar(json);
                jsonBuilder.setObject(jsonObject);
                return jsonObject;
            }
        }

        while (true) {
            nextChar = getNextChar(json);
            StringBuilder key = new StringBuilder();
            while (nextChar != '"') {
                key.append(nextChar);
                nextChar = getNextChar(json);
            }
            nextChar = getNextChar(json);


            while (nextChar != ':')
                nextChar = getNextChar(json);

            nextChar = getNextChar(json);
            skipWhitespaces(json);
            Object value = handleParsing(jsonBuilder, json);
            jsonObject.put(new JSONString(key.toString()), value);
            skipWhitespaces(json);


            if (nextChar == '}'){
                nextChar = getNextChar(json);
                break;
            }

            nextChar = getNextChar(json);
            skipWhitespaces(json);
        }

        jsonBuilder.setObject(jsonObject);
        return jsonObject;
    }

    public JSONString parseString(Builder builder, String json) {
        StringBuilder sb = new StringBuilder();
        nextChar = getNextChar(json);
        while (nextChar != '"') {
            sb.append(nextChar);
            nextChar = getNextChar(json);

        }
        nextChar = getNextChar(json);

        JSONString res = builder.buildJSONString(sb.toString());
        builder.setString(res);
        return res;
    }

    public Object parseNumber(Builder builder, String json) {
        StringBuilder sb = new StringBuilder();
        while (nextChar != ',' && !Character.isWhitespace(nextChar)) {
            sb.append(nextChar);
            nextChar = getNextChar(json);
        }

        try {
            JSONInteger jsonInteger = builder.buildJSONInteger(Integer.parseInt(sb.toString()));
            builder.setInteger(jsonInteger);
            return jsonInteger;
        } catch (NumberFormatException e) {
            JSONFloat jsonFloat = builder.buildJSONFloat(Float.parseFloat(sb.toString()));
            builder.setFloat(jsonFloat);
            return jsonFloat;
        }
    }

    private Object handleParsing(Builder jsonBuilder, String json) {
        if (this.idx < json.length()) {
            nextChar = json.charAt(this.idx);
            return switch (nextChar) {
                case '{' -> parseObject(jsonBuilder, json);
                case '"' -> parseString(jsonBuilder, json);
                default -> parseNumber(jsonBuilder, json);
            };
        }
        return null;
    }

    private boolean isStringValidJson(String json) {
        final Gson gson = new Gson();

        try {
            gson.fromJson(json, Object.class);
            return true;
        } catch (com.google.gson.JsonSyntaxException e) {
            return false;
        }
    }

    private char getNextChar(String json) {
        if (this.idx + 1 >= json.length())
            return ' ';

        this.idx++;
        return json.charAt(this.idx);
    }

    private void skipWhitespaces(String json) {
        while (Character.isWhitespace(nextChar))
            nextChar = getNextChar(json);
    }
}
