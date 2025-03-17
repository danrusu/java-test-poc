package com.testutils.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    public String subject;

    public String text;


    public Message(
            @JsonProperty("subject") String subject,
            @JsonProperty("body") String text
    ) {
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
