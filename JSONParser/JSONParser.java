import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;

public class JSONParser {
    public static Map<String, Object> parse(String json) {
        ParseResult<Map<String, Object>> result = parseObject(json, 0);
        return result.value;
    }

    private static ParseResult<Map<String, Object>> parseObject(String json, int start) {
        Map<String, Object> map = new HashMap<>();
        int index = start + 1; // skip the initial '{'
        while (json.charAt(index) != '}') {
            ParseResult<String> keyResult = parseString(json, index);
            //index = keyResult.index + 1; // skip the ':' after the key
            index = keyResult.index;

            while (json.charAt(index) == ' ' || json.charAt(index) == ':') {
                index++;
            }

            ParseResult<Object> valueResult = parseValue(json, index);

            // Check for null (error condition)
            if (valueResult == null) {
                throw new IllegalArgumentException("Failed to parse value at index " + index);
            }
            map.put(keyResult.value, valueResult.value);
            index = valueResult.index;

            // Skip over whitespace and comma before parsing the next key-value pair
            while (json.charAt(index) == ' ' || json.charAt(index) == ',') {
                index++;
            }
            // if (json.charAt(index) == ',') {
            //     index++; // skip the ','
            // }

        }
        return new ParseResult<>(map, index + 1);
    }

    private static ParseResult<List<Object>> parseArray(String json, int start) {
        List<Object> list = new ArrayList<>();
        int index = start + 1; // skip the initial '['
        while (json.charAt(index) != ']') {
            ParseResult<Object> valueResult = parseValue(json, index);
            list.add(valueResult.value);
            index = valueResult.index;
            if (json.charAt(index) == ',') {
                index++; // skip the ','
            }
        }
        return new ParseResult<>(list, index + 1);
    }

    private static ParseResult<String> parseString(String json, int start) {
        StringBuilder sb = new StringBuilder();
        int index = start + 1; // skip the initial '"'
        while (json.charAt(index) != '"') {
            sb.append(json.charAt(index));
            index++;
        }
        return new ParseResult<>(sb.toString(), index + 1);
    }

    private static ParseResult<Integer> parseInteger(String json, int start) {
        StringBuilder sb = new StringBuilder();
        int index = start;
        while (Character.isDigit(json.charAt(index))) {
            sb.append(json.charAt(index));
            index++;
        }
        return new ParseResult<>(Integer.parseInt(sb.toString()), index);
    }

    private static ParseResult<Object> parseValue(String json, int start) {
        char c = json.charAt(start);
        if (c == '{') {
            ParseResult<Map<String, Object>> result = parseObject(json, start);
            if (result == null) {
                return null;
            }
            return new ParseResult<>(result.value, result.index);
        } else if (c == '[') {
            ParseResult<List<Object>> result = parseArray(json, start);
            if (result == null) {
                return null;
            }
            return new ParseResult<>(result.value, result.index);
        } else if (c == '"') {
            ParseResult<String> result = parseString(json, start);
            if (result == null) {
                return null;
            }
            return new ParseResult<>(result.value, result.index);
        } else if (Character.isDigit(c)) {
            ParseResult<Integer> result = parseInteger(json, start);
            if (result == null) {
                return null;
            }
            return new ParseResult<>(result.value, result.index);
        } else if (json.substring(start, start + 4).equals("true")) {
            return new ParseResult<>(true, start + 4);
        } else if (json.substring(start, start + 5).equals("false")) {
            return new ParseResult<>(false, start + 5);
        }
        return null; // invalid input
    }

    private static class ParseResult<T> {
        final T value;
        final int index;

        ParseResult(T value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    public static void main(String[] args) {
        String json = "{\"debug\" : \"on\", \"window\" : {\"title\" : \"sample\", \"size\": 500}}";
        Map<String, Object> output = JSONParser.parse(json);
        assert output.get("debug").equals("on");
        assert ((Map<String, Object>) output.get("window")).get("title").equals("sample");
        assert ((Map<String, Object>) output.get("window")).get("size").equals(500);
        System.out.println("All assertions passed.");
    }
}
