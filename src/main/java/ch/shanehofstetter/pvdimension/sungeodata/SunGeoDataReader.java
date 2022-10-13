package ch.shanehofstetter.pvdimension.sungeodata;

import ch.shanehofstetter.pvdimension.io.FileReader;
import org.slf4j.LoggerFactory;


import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sun Geographic Data Reader reads the locally stored files containing the location-specific data about the suns positions
 * during different months
 * <p>
 * Data is stored in .txt files with tab separators, SunGeoDataReader parses one file to an ArrayList of SunGeoDataElements
 * <p>
 * IMPORTANT: At the moment, only the location "baden" is supported and file path is hardcoded
 * <p>
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.sungeodata
 */
public class SunGeoDataReader {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SunGeoDataReader.class);

    /**
     * Read and parse a file into an ArrayList
     *
     * @param month month
     * @return elements containing the files data
     * @throws Exception
     */
    public ArrayList<SunGeoDataElement> readSunGeoData(Month month) throws Exception {
        String filename = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ".txt";
        logger.debug("reading: "+filename);
//        String fileContent = FileReader.readFileToString(getClass().getResource("/ch/abbts/pvdimension/sungeodata/data/baden/" + filename));
        String fileContent = FileReader.readStreamIntoString(getClass().getClassLoader().getResourceAsStream("ch/abbts/pvdimension/sungeodata/data/baden/" + filename));
        return parseData(fileContent);
    }

    /**
     * parses given data, crops unnecessary values
     *
     * @param fileContent the complete content with header lines and everything
     * @return parsed ArrayList containing data-elements
     */
    private ArrayList<SunGeoDataElement> parseData(String fileContent) {
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile("\\d+:\\d+\\t\\d+.\\d+\\t\\d+.\\d+")
                .matcher(fileContent);
        while (m.find()) {
            allMatches.add(m.group());
        }
        ArrayList<SunGeoDataElement> elements = new ArrayList<>();
        for (String line : allMatches) {
            String[] values = line.split("\t");
            //time - azimut - altitude
            SunGeoDataElement element = new SunGeoDataElement();
            element.setTimeString(values[0]);
            element.setAzimut(Double.parseDouble(values[1]));
            element.setAltitude(Double.parseDouble(values[2]));
            elements.add(element);
        }
        return elements;
    }

}
