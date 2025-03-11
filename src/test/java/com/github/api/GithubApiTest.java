package com.github.api;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Tag("api-test")
@Tag("github")
public @interface GithubApiTest {
    // sharing 2 tags
}
