package utils.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class SLogger {
    private static final String LOGGER_OUTPUT_FILE = "out/log.txt";
    private final Logger logger;

    protected SLogger() {
        logger = Logger.getLogger(SimpleLogger.class.getSimpleName());

        var simpleFormatter = new SimpleFormatter();

        // no need for consoleHandler, FileHandler logs also to console
//        var consoleHandler = new ConsoleHandler();
//        consoleHandler.setFormatter(simpleFormatter);
//        logger.addHandler(consoleHandler);

        try {
            var fileHandler = new FileHandler(LOGGER_OUTPUT_FILE);
            fileHandler.setFormatter(simpleFormatter);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            throw new LoggerException();
        }
    }

    public Logger getLogger() {
        return logger;
    }
}
