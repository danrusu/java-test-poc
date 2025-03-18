package com.testutils.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MessageRecord(
        @JsonProperty("subject")
        String subject,
        @JsonProperty("body")
        String text
) {
}
