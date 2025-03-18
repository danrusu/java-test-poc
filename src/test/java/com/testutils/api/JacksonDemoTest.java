package com.testutils.api;

import com.testutils.api.model.Message;
import com.testutils.api.model.MessageRecord;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JacksonDemoTest {
    @Test
    @Tag("api-test")
    void testMessageEndpoint() {

        Message message = RestAssured
                .get("https://testutils.com/poc/json/message")
                .as(Message.class, ObjectMapperType.JACKSON_2); // default mapper

        assertEquals("afterschool reminder", message.getSubject());
        assertEquals("Don't forget to pick me up at 5 pm!", message.getText());
    }

    @Test
    @Tag("api-test")
    void testMessageEndpoint_usingRecords() {

        MessageRecord messageRecord = RestAssured
                .get("https://testutils.com/poc/json/message")
                .as(MessageRecord.class); // default mapper

        assertEquals("afterschool reminder", messageRecord.subject());
        assertEquals("Don't forget to pick me up at 5 pm!", messageRecord.text());
    }
}
