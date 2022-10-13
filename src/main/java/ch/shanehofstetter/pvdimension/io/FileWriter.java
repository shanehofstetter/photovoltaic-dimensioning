package ch.shanehofstetter.pvdimension.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Shane Hofstetter - shane.hofstetter@gmail.com
 * ch.shanehofstetter.pvdimension.io
 */
public class FileWriter {

    public void writeStringToFile(String content, Path resourcePath) throws WriteException {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(resourcePath)) {
            bufferedWriter.write(content);
        } catch (Exception e) {
            throw new WriteException(e.getMessage());
        }
    }

    public void writeStringToPath(String content, URL filePath) {
        try (PrintWriter writer = new PrintWriter(new File(filePath.getPath()))) {
            writer.write(content);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public class WriteException extends Exception {
        public WriteException(String message) {
            super(message);
        }
    }
}
