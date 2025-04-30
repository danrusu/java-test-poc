package utils.file;

import utils.base.StaticClass;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static utils.file.FileUtils.deleteFile;
import static utils.file.FileUtils.getFiles;

public class DownloadUtils extends StaticClass {

    public static List<Path> getDownloadedFiles() throws IOException {
        return getFiles(getDownloadsPath());
    }

    public static void cleanDownloads() throws IOException {
        getFiles(getDownloadsPath()).forEach(FileUtils::deleteFile);
    }

    public static void deleteDownload(Path filePath) throws IOException {
        Path deleteFilePath = getDownloadsPath().resolve(filePath);
        deleteFile(deleteFilePath);
    }

    public static Path getDownloadsPath() {
        return Path.of(System.getProperty("user.dir"), "downloads");
    }
}
