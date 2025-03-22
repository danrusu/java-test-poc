package utils.base;

public class StaticClass {
    protected StaticClass() {
        throw new StaticClassException();
    }

    private static class StaticClassException extends RuntimeException {
    }
}
