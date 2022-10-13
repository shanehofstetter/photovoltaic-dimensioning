package ch.shanehofstetter.pvdimension.io.pvdata;

import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.io.pvdata
 */
public class PVSaver {

    /**
     * save the pvData to given filepath
     *
     * @param pvData   pvData to save
     * @param filePath filepath to save to
     * @throws IOException if file could not be written
     */
    public void save(PVData pvData, String filePath) throws IOException {

        FileOutputStream fileOut = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(pvData);
        out.close();
        fileOut.close();
        System.out.print("Serialized data is saved in: " + filePath);
    }

    /**
     * save a PVGenerator object to a file, get serialized
     * @param pvGenerator pvGenerator to save
     * @param filePath file to save to
     * @throws IOException if file could not be written
     */
    public void save(PVGenerator pvGenerator, String filePath) throws IOException {
        PVData pvData = new PVData(pvGenerator);
        save(pvData, filePath);
    }

}
