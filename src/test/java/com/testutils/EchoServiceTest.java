package com.testutils;

import io.restassured.http.Cookie;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

class EchoServiceTest {

    public static final String TEST_UTILS_BASE_URL = "http://testutils.com";
//    public static final String TEST_UTILS_BASE_URL = "http://localhost:1111";

    @Test
    void testApi() {
        Cookie myCookie = new Cookie.Builder("session_id", "1234")
                .setSecured(true)
                .build();

        given().baseUri(TEST_UTILS_BASE_URL)
                .pathParams("echoEndpoint", "echo"
                )
                .queryParam("test", true)
                .queryParam("scope", "demo")

                .contentType(JSON)
                .header("My-Custom-Header", "Custom encoding", "UTF8")
                .header("User-agent", "Java RestAssured Client")
                .cookie(myCookie)

                .body("{ \"test\": true }")

                .when()
                .log().all()
                .post("/api/{echoEndpoint}")

                .then()
                .log().all()
                .statusCode(200);
    }
}

