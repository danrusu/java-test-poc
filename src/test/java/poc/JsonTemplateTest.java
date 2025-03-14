package poc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonTemplateTest {
    public static Path JSON_TEMPLATE_TEST_RESOURCE_PATH = Paths.get(
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

    private Path getTestJsonTemplateResourcePath(String resourceFileName) {
        return JSON_TEMPLATE_TEST_RESOURCE_PATH.resolve(resourceFileName);
    }
}
