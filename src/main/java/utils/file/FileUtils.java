package utils.file;

import utils.base.StaticClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils extends StaticClass {
    public static List<Path> getFiles(Path folderPath) throws IOException {
        try (Stream<Path> filePaths = Files.walk(folderPath)) {
            return filePaths.filter(
                    filePath -> filePath.toFile().isFile()
            ).toList();
        }
    }

    public static void deleteFile(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new DeleteFileException(filePath, e);
        }
    }
}
