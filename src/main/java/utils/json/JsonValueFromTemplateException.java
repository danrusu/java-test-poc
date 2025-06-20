package utils.json;

public class JsonValueFromTemplateException extends RuntimeException {
    public JsonValueFromTemplateException() {
        super("not all templates were replaced");
    }
}
