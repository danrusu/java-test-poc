package json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class JsonTemplate {
    public static final String TEMPLATE_PATTERN = "$-{%s}";
    public static final String TEMPLATE_START_PATTERN = "$-{";

    public static String getJson(Path templatePath, Path valuesReplacements) throws IOException {
        final String templateString = Files.readString(templatePath);
        final String valuesJsonString = Files.readString(valuesReplacements);

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {
        };
        HashMap<String, Object> replacementMap = mapper.readValue(valuesJsonString, typeRef);

        String finalJsonString = templateString;
        for (Map.Entry<String, Object> entry : replacementMap.entrySet()) {
            String target = String.format("$-{%s}", entry.getKey());
            String replacement = String.format("%s", entry.getValue());
            finalJsonString = finalJsonString.replace(target, replacement);
        }
        if (finalJsonString.contains("$-{")) {
            throw new RuntimeException("not all templates were replaced");
        }

        return finalJsonString;
    }

    public static String getJson(Path templatePath, Map<String, Object> valuesReplacements) throws IOException {
        String finalJsonString = Files.readString(templatePath);
        for (Map.Entry<String, Object> entry : valuesReplacements.entrySet()) {
            String target = String.format(TEMPLATE_PATTERN, entry.getKey());
            String replacement = String.format("%s", entry.getValue());
            finalJsonString = finalJsonString.replace(target, replacement);
        }

        return finalJsonString;
    }

    public static void assertTemplateIsComplete(String jsonStringFromnTemplate) {
        if (jsonStringFromnTemplate.contains(TEMPLATE_START_PATTERN)) {
            throw new JsonValueFromTemplateError();
        }
    }
}
