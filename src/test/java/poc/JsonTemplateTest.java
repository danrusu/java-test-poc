package poc;

import org.junit.jupiter.api.Test;
import utils.json.JsonTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonTemplateTest {
    public static Path jsonTemplateTestResourcePath = Paths.get(
            "src",
            "test",
            "resources",
            "json-template");

    @Test
    void testJsonTemplateValuesReplacement() throws IOException {
        Path templatePath = getTestJsonTemplateResourcePath("car.json.template.txt");
        Path valuesPath = getTestJsonTemplateResourcePath("car.values.json");
        Path expectedJsonPath = getTestJsonTemplateResourcePath("car.values.expected.json");
        String expectedJsonString = Files.readString(expectedJsonPath);

        String finalJsonString = JsonTemplate.getJson(templatePath, valuesPath);

        assertEquals(expectedJsonString, finalJsonString);
    }

    @Test
    void testJsonTemplateValuesReplacementFromMap() throws IOException {
        Path templatePath = getTestJsonTemplateResourcePath("car.json.template.txt");


        Map<String, Object> replacementMap = Map.of(
                "model", "911",
                "engine.hp", 300,
                "engine.isDiesel", true,
                "engine.manufacturer.location", "Germany"
        );
        String finalJsonString = JsonTemplate.getJson(templatePath, replacementMap);

        Path expectedJsonPath = getTestJsonTemplateResourcePath("car.values.expected.json");
        String expectedJsonString = Files.readString(expectedJsonPath);
        assertEquals(expectedJsonString, finalJsonString);
    }

    private Path getTestJsonTemplateResourcePath(String resourceFileName) {
        return jsonTemplateTestResourcePath.resolve(resourceFileName);
    }
}
