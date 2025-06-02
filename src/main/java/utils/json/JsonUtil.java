package utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.base.StaticClass;

import java.util.Map;

public class JsonUtil extends StaticClass {
    public static ObjectMapper jsonMapper() {
        return new ObjectMapper();
    }

    public static TypeReference<Map<String, Object>> mapTypeRef() {
        return new TypeReference<>() {
        };
    }

    public static Map<String, Object> toMap(Object keyValuePojo) {
        return jsonMapper().convertValue(keyValuePojo, mapTypeRef());
    }

    public static Map<String, Object> toMap(String jsonString) throws JsonProcessingException {
        return jsonMapper().readValue(jsonString, mapTypeRef());
    }

    public static <T> T parse(String jsonString, Class<T> jsonModel) throws JsonProcessingException {
        return jsonMapper().readValue(jsonString, jsonModel);
    }
}
