package ch.shanehofstetter.pvdimension.io.csv;

import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class CSVWriterController {
    static final Logger logger = LogManager.getLogger();

    /**
     * writes multiple .csv files into the given directory<br>
     * 1. PVWeek    : Ertrag_Woche.csv<br>
     * 2. PVMonths  : Ertrag_Jahr.csv<br>
     * 3. YearTotal : Jahrestotal.csv<br>
     *
     * @param directory   directory to store the files in
     * @param pvGenerator pvGenerator
     * @throws IOException when directory is not there or write-protected, etc.
     */
    public void saveCSVData(File directory, PVGenerator pvGenerator) throws IOException {
        System.out.println(directory.getPath());
        if (pvGenerator.getSimulationData().getPvWeek() != null) {
            saveCSVWriteable(new File(directory.getPath(), "Ertrag_Woche.csv"), pvGenerator.getSimulationData().getPvWeek());
            logger.info("saved pvweek");
        } else {
            logger.error("cannot save pvweek, its null");
        }
        if (pvGenerator.getSimulationData().getPvMonths() != null) {
            saveCSVWriteable(new File(directory.getPath(), "Ertrag_Jahr.csv"), pvGenerator.getSimulationData().getPvYear());
            logger.info("saved PvMonths");
        } else {
            logger.error("cannot save pvmonths, its null");
        }
        if (pvGenerator.getSimulationData().getPvYear() != null) {
            saveCSVWriteable(new File(directory.getPath(), "Jahrestotal.csv"), pvGenerator.getSimulationData().getPvYear());
            logger.info("saved PvYear");
        } else {
            logger.error("cannot save pvyear, its null");
        }
    }

    /**
     * Writes a CSVWriteable object given file
     *
     * @param file         file to store the csv data to, needs to be complete path to file
     * @param csvWriteable csvWriteable object to export
     * @throws IOException if file can not be written
     */
    private void saveCSVWriteable(File file, CSVWriteable csvWriteable) throws IOException {
        CSVWriter csvWriter = new CSVWriter(file);
        csvWriter.writeCSV(csvWriteable);
    }
}
