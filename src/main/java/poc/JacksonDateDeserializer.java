package poc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import poc.model.Message;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class JacksonDateDeserializer {
    private static final File MESSAGE_JSON = new File("src/main/resources/message.json");

    public static void main(String[] args) throws IOException {

        final Message message1 = readJsonWithJavaTimeModule(MESSAGE_JSON);
        System.out.println(message1);

        final Message message2 = readJsonWithCustomDeserializer(MESSAGE_JSON);
        System.out.println(message2);
    }

    private static Message readJsonWithJavaTimeModule(File jsonFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(WRITE_DATES_AS_TIMESTAMPS);

        return mapper.readValue(jsonFile, Message.class);
    }

    private static Message readJsonWithCustomDeserializer(File jsonFile) throws IOException {
        LocalDateDeserializer dateDeserializer =
                new LocalDateDeserializer(DateTimeFormatter.ISO_DATE_TIME);

        SimpleModule module = new SimpleModule()
                .addDeserializer(LocalDate.class, dateDeserializer);

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(module);

        return mapper.readValue(jsonFile, Message.class);
    }
}

