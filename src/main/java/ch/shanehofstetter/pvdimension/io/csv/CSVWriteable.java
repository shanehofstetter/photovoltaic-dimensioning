package ch.shanehofstetter.pvdimension.io.csv;

import java.util.ArrayList;

public interface CSVWriteable {
    ArrayList<String> getCSVHeaders();

    ArrayList<ArrayList<String>> getCSVData();
}
