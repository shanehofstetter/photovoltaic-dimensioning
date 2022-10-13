package ch.shanehofstetter.pvdimension.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.io
 */
public class FileReader {

    /**
     * Reads the file at given location into a String and returns it
     *
     * @param url the url of the file to read
     * @return the whole file as String
     * @throws Exception if the file was not found or not properly formatted
     */
    public static String readFileToString(URL url) throws Exception {
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(url.toURI().getPath()))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    /**
     * read a given inputstream completely into a string
     *
     * @param stream stream to read from
     * @return string containing all data from stream
     */
    public static String readStreamIntoString(InputStream stream) {
        java.util.Scanner s = new java.util.Scanner(stream, "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
