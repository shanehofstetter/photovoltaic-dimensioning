package ch.shanehofstetter.pvdimension.pvgenerator.inverter;

import ch.shanehofstetter.pvdimension.io.FileReader;

import java.util.ArrayList;


public class InverterReader {

    public ArrayList<Inverter> readInverters() throws Exception {
        String fileContent = FileReader.readStreamIntoString(getClass().getResourceAsStream("database/inverters.txt"));
        return parseData(fileContent);
    }

    private ArrayList<Inverter> parseData(String fileContent) {
        String[] lines = fileContent.split(System.lineSeparator());
        if (lines.length < 3) {
            return null;
        }
        ArrayList<Inverter> solarPanels = new ArrayList<>();

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] values = line.split("\t");
            Inverter solarPanel = new Inverter();
            solarPanel.setManufacturer(values[0]);
            solarPanel.setTypeName(values[1]);
            solarPanel.setCircuitType(values[2]);
            solarPanel.setUsageType(values[3]);
            solarPanel.setMaxGeneratorPower(Double.parseDouble(values[4]));
            solarPanel.setMaxACPower(Double.parseDouble(values[5]));
            solarPanels.add(solarPanel);
        }
        return solarPanels;
    }
}
