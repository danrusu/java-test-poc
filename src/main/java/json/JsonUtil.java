package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.base.StaticClass;

import java.util.Map;

public class JsonUtil extends StaticClass {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static final TypeReference<Map<String, Object>> mapTypeRef = new TypeReference<>() {
    };

    public static Map<String, Object> toMap(Object keyValuePojo) {
        return objectMapper.convertValue(keyValuePojo, mapTypeRef);
    }

    public static Map<String, Object> toMap(String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, mapTypeRef);
    }

    public static <T> T parse(String jsonString, Class<T> jsonModel) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, jsonModel);
    }
}
