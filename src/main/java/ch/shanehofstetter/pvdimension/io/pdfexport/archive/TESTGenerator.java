
package ch.shanehofstetter.pvdimension.io.pdfexport.archive;

import ch.shanehofstetter.pvdimension.gui.components.pvcharts.PVInputDataType;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart.PVDayInputLineChart;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart.PVWeeklyInputChart;
import ch.shanehofstetter.pvdimension.io.pdfexport.ChartSnapshot;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class TESTGenerator extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        /* STEPS TO BE DONE FOR PDF OUTPUT */
        PVWeeklyInputChart chart = new PVWeeklyInputChart(PVInputDataType.SUN_POWER);//CHART EXAMPLE
        PVDayInputLineChart chartsa = new PVDayInputLineChart("Chart Titel", true, "XAchse", "YAchse", 0, 1000);//CHART EXAMPLE
        PDFDataContainer dataContainer = new PDFDataContainer();//CREATE A CONTAINER FOR THE PDF DATA
        dataContainer.addData(new ChartSnapshot(chart.getPvDayInputChart()).getImage(), "ON TOP OF Image", "Teaser among the Image");//ADD DATA TO CONTAINER(TAKE CHARTSNAPSHOT)
        dataContainer.addData(new ChartSnapshot(chartsa).getImage(), "Top Of Image Two", "Teaser Chart Two");//ADD DATA TO CONTAINER (TAKE CHARTSNAPSHOT)
        new PDFGenerator(dataContainer); // CREATES THE FILE
        /* STEPS TO BE DONE FOR PDF OUTPUT END */


    }

}
