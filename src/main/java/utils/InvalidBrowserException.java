package utils;

public class InvalidBrowserException extends RuntimeException {
    public InvalidBrowserException(String browser) {
        super(browser);
    }
}
