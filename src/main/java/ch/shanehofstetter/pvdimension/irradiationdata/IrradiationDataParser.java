package ch.shanehofstetter.pvdimension.irradiationdata;

import java.util.ArrayList;

/**
 * Reads the given CSV Data from Webservice and parses it to an IrradiationDataElement-List
 *
 */
public class IrradiationDataParser {

    /**
     * Parse the retrieved CSV-Data from Web-Service
     * For each Row in the Data-Table, an IrradiationDataElement gets created
     * and added to the ArrayList
     *
     * @param csv CSV-String to parse
     * @return the resulting IrradiationDataElement-List
     */
    public static ArrayList<IrradiationDataElement> parseCSVData(String csv) {
        String[] lines = csv.split("\n");
        ArrayList<IrradiationDataElement> irradiationDataElements = new ArrayList<>();
        boolean titleLine = true;
        for (String line : lines) {
            String[] values = line.split("\t\t");
            if (values.length == 4) {
                if (titleLine) {
                    titleLine = false;
                } else {
                    IrradiationDataElement element = new IrradiationDataElement();
                    element.setTimeString(values[0]);
                    element.setGlobalIrradiance(Double.parseDouble(values[1]));
                    element.setDiffuseIrradiance(Double.parseDouble(values[2]));
                    element.setClearSkyIrradiance(Double.parseDouble(values[3]));
                    irradiationDataElements.add(element);
//                    for (String value : values){
//                        element.setTime(value);
//                        //1. time
//                        //2. G
//                        //3. Gd
//                        //4. Gc
//                        logger.debug(value);
//                    }
                }
            }
        }
        return irradiationDataElements;
    }
}
