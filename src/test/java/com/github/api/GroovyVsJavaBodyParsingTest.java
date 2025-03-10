package com.github.api;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.api.GithubApiEndpoints.GITHUB_API_BASE_URL;
import static com.github.api.GithubApiEndpoints.GITHUB_SEARCH_REPOSITORIES_ENDPOINT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GroovyVsJavaBodyParsingTest {

    @Test
    @GithubApiTest
    void allResultsMustBeJavaRepos_GroovyParsing() {
        given()
                .baseUri(GITHUB_API_BASE_URL)
                .queryParam("q", "Java")
                // https://api.github.com/search/repositories?q=Java
                .get(GITHUB_SEARCH_REPOSITORIES_ENDPOINT)

                .then()
                // harder to debug
                .body("items.findAll{ it.language != 'Java' }.size()", equalTo(0));

    }

    // preferred
    @Test
    @GithubApiTest
    void allResultsMustBeJavaRepos_JavaParsing() {

        List<String> languages = given()
                .baseUri(GITHUB_API_BASE_URL)
                .queryParam("q", "Java")
                .log().all()
                .get(GITHUB_SEARCH_REPOSITORIES_ENDPOINT)

                .jsonPath()
                .getList("items.language");

        List<String> javaLanguages = languages.stream()
                .filter(lang -> !"Java".equalsIgnoreCase(lang))
                .toList();

        assertEquals(0, javaLanguages.size());
    }
}
