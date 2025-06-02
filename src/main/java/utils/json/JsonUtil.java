package utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtil {
    private final ObjectMapper jsonMapper;
    private final TypeReference<Map<String, Object>> mapTypeRef;

    public JsonUtil() {
        this.jsonMapper = new ObjectMapper();
        this.mapTypeRef = new TypeReference<>() {
        };
    }

    public Map<String, Object> toMap(Object keyValuePojo) {
        return this.jsonMapper.convertValue(keyValuePojo, mapTypeRef);
    }

    public Map<String, Object> toMap(String jsonString) throws JsonProcessingException {
        return this.jsonMapper.readValue(jsonString, mapTypeRef);
    }

    public <T> T parse(String jsonString, Class<T> jsonModel) throws JsonProcessingException {
        return this.jsonMapper.readValue(jsonString, jsonModel);
    }

    public ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

    public TypeReference<Map<String, Object>> getMapTypeRef() {
        return mapTypeRef;
    }
}
