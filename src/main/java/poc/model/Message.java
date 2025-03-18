package poc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private final LocalDate date;
    private final String from;
    private final int priority;
    private final String text;

    public Message(
            @JsonProperty("date")
            LocalDate date,
            @JsonProperty("from")
            String from,
            @JsonProperty("priority")
            int priority,
            @JsonProperty("text")
            String text) {
        this.date = date;
        this.from = from;
        this.priority = priority;
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public int getPriority() {
        return priority;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "date=" + date +
                ", from='" + from + '\'' +
                ", priority=" + priority +
                ", text='" + text + '\'' +
                '}';
    }
}
