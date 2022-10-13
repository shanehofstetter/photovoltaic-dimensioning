package ch.shanehofstetter.pvdimension.io.pvdata;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.io.pvdata
 */
public class PVLoader {

    /**
     * load the serialized pvData object in given filepath and deserialize it
     *
     * @param filePath location of file
     * @return deserialized data object
     * @throws Exception if file not there or cannot be deserialized due to an error (data missing or corrupt or old version)
     */
    public PVData load(String filePath) throws Exception {
        PVData pvData;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            pvData = (PVData) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
            throw i;
        }
        if (pvData == null) {
            throw new Exception("Konnte das Projekt nicht korrekt laden.");
        }
        return pvData;
    }
}
