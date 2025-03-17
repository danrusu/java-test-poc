package com.testutils.api;

import com.testutils.api.model.Message;
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
}
