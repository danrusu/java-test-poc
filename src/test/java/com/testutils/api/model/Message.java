package com.testutils.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    @JsonProperty("subject")
    public String subject;
    @JsonProperty("body")
    public String text;

    public Message() {
    }

    public Message(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getSubject() {
        return subject;
    }
}
