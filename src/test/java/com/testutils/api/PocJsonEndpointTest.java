package com.testutils.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;

class PocJsonEndpointTest {

    public static final String TEST_UTILS_BASE_URL = "http://testutils.com";

    private String sessionId;

    @BeforeEach
    public void setup() {

        RestAssured.baseURI = TEST_UTILS_BASE_URL;
    }

    @Test
    @Tag("api-test")
    void testHomePageRedirectsToLoginIfNotAuthenticated() {
        when()
                .get("/poc/json/messages")

                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .contentType(JSON)
                .body("", equalTo("a"));

    }

}


