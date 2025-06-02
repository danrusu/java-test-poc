package utils.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

//= "$-{%s}"
public class JsonTemplate {
    private final String templatePattern;

    public JsonTemplate(String templatePattern) {
        this.templatePattern = templatePattern;
    }

    public String getJson(Path templatePath, Path valuesReplacements) throws IOException {
        final JsonUtil jsonUtil = new JsonUtil();
        final String templateString = Files.readString(templatePath);
        final String valuesJsonString = Files.readString(valuesReplacements);

        Map<String, Object> replacementMap = jsonUtil.getJsonMapper()
                .readValue(valuesJsonString, jsonUtil.getMapTypeRef());

        String finalJsonString = templateString;
        for (Map.Entry<String, Object> entry : replacementMap.entrySet()) {
            String target = String.format(this.templatePattern, entry.getKey());
            String replacement = String.format("%s", entry.getValue());
            finalJsonString = finalJsonString.replace(target, replacement);
        }
        return finalJsonString;
    }

    public String getJson(Path templatePath, Map<String, Object> valuesReplacements) throws IOException {
        String finalJsonString = Files.readString(templatePath);
        for (Map.Entry<String, Object> entry : valuesReplacements.entrySet()) {
            String target = String.format(this.templatePattern, entry.getKey());
            String replacement = String.format("%s", entry.getValue());
            finalJsonString = finalJsonString.replace(target, replacement);
        }

        return finalJsonString;
    }

    public void assertTemplateIsComplete(String jsonStringFromTemplate) {
        final String templateStartPattern = this.templatePattern.split("%s")[0];
        if (jsonStringFromTemplate.contains(templateStartPattern)) {
            throw new JsonValueFromTemplateError();
        }
    }
}
