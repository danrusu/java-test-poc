package json;

public class JsonValueFromTemplateError extends Error {
    public JsonValueFromTemplateError() {
        super("not all templates were replaced");
    }
}
