package utils.file;

import utils.base.StaticClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static utils.logger.SimpleLogger.log;

public class FileUtils extends StaticClass {
    public static List<Path> getFiles(Path folderPath) throws IOException {
        try (Stream<Path> filePaths = Files.walk(folderPath)) {
            return filePaths.filter(
                    filePath -> filePath.toFile().isFile()
            ).toList();
        }
    }

    public static List<Path> getDownloadedFiles() throws IOException {
        return getFiles(getDownloadsPath());
    }

    public static void cleanDownloads() throws IOException {
        getFiles(getDownloadsPath()).forEach(filePath -> {
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                log("Cannot delete file: " + filePath);
            }
        });
    }

    public static Path getDownloadsPath() {
        return Path.of(System.getProperty("user.dir"), "downloads");
    }
}
