package utils.logger;

import utils.base.StaticClass;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class SimpleLogger extends StaticClass {
    public static final Logger LOGGER = new SLogger().getLogger();
    public static final Level DEFAULT_LOG_LEVEL = INFO;

    public static void log(String text) {
        log(DEFAULT_LOG_LEVEL, text);
    }

    public static void log(Level level, String text) {
        LOGGER.log(new LogRecord(level, text));
    }
}
