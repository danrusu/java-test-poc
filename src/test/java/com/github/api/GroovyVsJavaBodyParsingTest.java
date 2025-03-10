package com.github.api;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.api.GithubApiEndpoints.GITHUB_API_BASE_URL;
import static com.github.api.GithubApiEndpoints.GITHUB_SEARCH_REPOSITORIES_ENDPOINT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GroovyVsJavaBodyParsingTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = GITHUB_API_BASE_URL;
        RestAssured.basePath = GITHUB_SEARCH_REPOSITORIES_ENDPOINT;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.HEADERS);
    }

    @AfterAll
    static void teardown() {
        RestAssured.reset();
    }

    @Test
    @GithubApiTest
    void allResultsMustBeJavaRepos_GroovyParsing() {
        given()
                .queryParam("q", "Java")
                // https://api.github.com/search/repositories?q=Java
                .get()

                .then()
                // harder to debug
                .body("items.findAll{ it.language != 'Java' }.size()", equalTo(0));

    }

    // preferred
    @Test
    @GithubApiTest
    void allResultsMustBeJavaRepos_JavaParsing() {

        List<String> languages = given()
                .queryParam("q", "Java")
                .get()

                .jsonPath()
                .getList("items.language");

        List<String> javaLanguages = languages.stream()
                .filter(lang -> !"Java".equalsIgnoreCase(lang))
                .toList();

        assertEquals(0, javaLanguages.size());
    }
}
