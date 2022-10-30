package ch.shanehofstetter.pvdimension.io.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVWriter {

    private File outputFile;

    /**
     * CSVWriter
     *
     * @param outputFile file to write the csv data into
     */
    public CSVWriter(File outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * export the CSVWriteable object<br>
     * calls methods in the interface so these need to be implemented<br>
     * cancels export if one of the methods return null<br>
     *
     * @param csvWriteable csvWriteable object to export
     * @throws IOException if file cannot be written
     */
    public void writeCSV(CSVWriteable csvWriteable) throws IOException {
        ArrayList<String> headers = csvWriteable.getCSVHeaders();
        ArrayList<ArrayList<String>> data = csvWriteable.getCSVData();
        if (headers == null || data == null) {
            return;
        }
        FileWriter fileWriter = new FileWriter(outputFile);
        fileWriter.write(String.join(",", headers));
        fileWriter.write("\n");
        for (ArrayList<String> strings : data) {
            fileWriter.write(String.join(",", strings));
            fileWriter.write("\n");
        }
        fileWriter.flush();
        fileWriter.close();
    }
}
