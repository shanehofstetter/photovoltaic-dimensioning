package ch.shanehofstetter.pvdimension.io.csv;

import java.util.ArrayList;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.io.csv
 */
public interface CSVWriteable {
    ArrayList<String> getCSVHeaders();

    ArrayList<ArrayList<String>> getCSVData();
}
