package utils.base;

public class StaticClass {
    protected StaticClass() {
        throw new StaticClassException();
    }

    public static class StaticClassException extends RuntimeException {
    }
}
