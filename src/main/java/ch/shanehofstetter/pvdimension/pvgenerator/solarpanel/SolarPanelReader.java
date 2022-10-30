package ch.shanehofstetter.pvdimension.pvgenerator.solarpanel;

import ch.shanehofstetter.pvdimension.io.FileReader;

import java.util.ArrayList;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.pvgenerator.solarpanel
 */
public class SolarPanelReader {

    /**
     * read the modules from file
     *
     * @return list containing solar panels
     * @throws Exception
     */
    public ArrayList<SolarPanel> readModules() throws Exception {
        String fileContent = FileReader.readStreamIntoString(getClass().getResourceAsStream("/ch/shanehofstetter/pvdimension/pvgenerator/solarpanel/database/modules.txt"));
        return parseData(fileContent);
    }

    /**
     * parse the given data into solarpanels list
     * @param fileContent complete file content
     * @return list containing all solar panels
     */
    private ArrayList<SolarPanel> parseData(String fileContent) {
        String[] lines = fileContent.split(System.lineSeparator());
        if (lines.length < 3) {
            return null;
        }
        ArrayList<SolarPanel> solarPanels = new ArrayList<>();

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] values = line.split("\t");

            SolarPanel solarPanel = new SolarPanel();
            solarPanel.setManufacturer(values[0]);
            solarPanel.setTypeName(values[1]);
            solarPanel.setPower(Double.parseDouble(values[2]));
            solarPanel.setEfficiency(Double.parseDouble(values[3]) / 100.0);
            solarPanel.setSizeByPowerAndEfficiency();
            solarPanels.add(solarPanel);
        }
        return solarPanels;
    }
}
