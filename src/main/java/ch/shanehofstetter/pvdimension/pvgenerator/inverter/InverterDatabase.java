package ch.shanehofstetter.pvdimension.pvgenerator.inverter;

import java.util.ArrayList;

public class InverterDatabase {

    private static ArrayList<Inverter> inverters;

    public static void load() {
        InverterReader reader = new InverterReader();
        try {
            inverters = reader.readInverters();
        } catch (Exception e) {
            inverters = new ArrayList<>();
        }
    }

    public static ArrayList<Inverter> getInverters() {
        return inverters;
    }
}
