package com.testutils.todo;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.ResponseBodyExtractionOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static io.restassured.http.ContentType.HTML;
import static io.restassured.http.ContentType.TEXT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

// Tests for https://github.com/danrusu/node-js-todo-app
class TodoAuthTest {

    public static final String TODO_BASE_URL = "http://localhost:1112/";

    private String sessionId;

    @BeforeEach
    public void setup() {

        RestAssured.baseURI = TODO_BASE_URL;

        RestAssured.config = RestAssuredConfig.config()
                .redirect(redirectConfig()
                        .followRedirects(false));

        String setCookieHeader = given()
                .body("{\"username\":\"tester\",\"password\":\"123\"}")
                .when()
                .post("/login")

                .then()
                .header("Set-Cookie", containsStringIgnoringCase("session-id="))

                .extract()
                .header("Set-Cookie");


        this.sessionId = setCookieHeader.replace("session-id=", "");

        assertThat(this.sessionId, is(notNullValue()));
    }

    @Test
    @Tag("todo-local-api-test")
    @Tag("todo-local-smoke-test")
    void testHomePageRedirectsToLoginIfNotAuthenticated() {
        when()
                .get()

                .then()
                .log().all()
                .statusCode(302)
                .contentType(TEXT)
                .header("Location", equalTo("/login"));
    }

    @Test
    @Tag("todo-local-api-test")
    @Tag("todo-local-smoke-test")
    void testHomePageIsFetchedIfAuthenticated() {
        ResponseBodyExtractionOptions resposeBody = given()
                .cookie("session-id", this.sessionId)
                .when()
                .get()

                .then()
                .log().all()
                .statusCode(200)
                .contentType(HTML)
                .extract()
                .body();

//        resposeBody.xmlPath()


    }
}


