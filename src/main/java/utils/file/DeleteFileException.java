package utils.file;

import java.nio.file.Path;

public class DeleteFileException extends RuntimeException {
    public DeleteFileException(Path filepath, Throwable cause) {
        super("Cannot delete file: " + filepath.toString(), cause);
    }
}
