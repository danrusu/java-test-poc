package utils.json;

import utils.base.StaticClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static utils.json.JsonUtil.jsonMapper;
import static utils.json.JsonUtil.mapTypeRef;

public class JsonTemplate extends StaticClass {
    public static final String TEMPLATE_PATTERN = "$-{%s}";
    public static final String TEMPLATE_START_PATTERN = "$-{";

    public static String getJson(Path templatePath, Path valuesReplacements) throws IOException {
        final String templateString = Files.readString(templatePath);
        final String valuesJsonString = Files.readString(valuesReplacements);

        Map<String, Object> replacementMap = jsonMapper().readValue(valuesJsonString, mapTypeRef());

        String finalJsonString = templateString;
        for (Map.Entry<String, Object> entry : replacementMap.entrySet()) {
            String target = String.format(TEMPLATE_PATTERN, entry.getKey());
            String replacement = String.format("%s", entry.getValue());
            finalJsonString = finalJsonString.replace(target, replacement);
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

    public static void assertTemplateIsComplete(String jsonStringFromTemplate) {
        if (jsonStringFromTemplate.contains(TEMPLATE_START_PATTERN)) {
            throw new JsonValueFromTemplateError();
        }
    }
}
