package com.testutils.api;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static io.restassured.http.ContentType.JSON;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.*;


// Hamcrest Matchers: https://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/Matchers.html
class EchoServiceTest {

    public static final String TEST_UTILS_BASE_URL = "http://testutils.com";
//    public static final String TEST_UTILS_BASE_URL = "http://localhost:1111";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = TEST_UTILS_BASE_URL;
        RestAssured.basePath = "/api";

        //RestAssured.rootPath = "request";

        RestAssured.config =
                RestAssuredConfig.config().redirect(redirectConfig().followRedirects(false));
    }

    @Test
    @Tag("api-test")
    @Tag("smoke-test")
    void testApi() {
        Cookie myCookie = new Cookie.Builder("session_id", "1234")
                .setSecured(true)
                .build();

        given().baseUri(TEST_UTILS_BASE_URL)
                .pathParams("echoEndpoint", "echo")
                .queryParam("test", true)
                .queryParam("scope", "demo")

                .contentType(JSON)
                .header("x-custom-header", "custom encoding", "UTF8")
                .header("User-agent", "Java RestAssured Client")
                .cookie(myCookie)

                .body("{ \"test\": true }")

                .log().all()

                .when()
                .post("/{echoEndpoint}")

                .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(2L), SECONDS)

                .contentType(JSON)
                .header("X-Powered-By", equalToIgnoringCase("express"))
                .header("Etag", not(isEmptyString()))

                .rootPath("request")
                .body("headers.x-custom-header", equalTo("custom encoding, UTF8"))
                .body("body.test", equalTo(true))

                .noRootPath()
                .body("request.cookies.session_id", equalTo("1234"));
    }
}

