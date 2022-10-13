package ch.shanehofstetter.pvdimension.gui.components.views;

import ch.shanehofstetter.pvdimension.gui.components.pvcharts.barchart.PVAmortisationBarChart;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.barchart.PVTotalBarChart;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.barchart.PVTotalEconomicsBarChart;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart.PVWeekOverviewLineChart;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart.PVWeeklyResultChart;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart.PVYearOverviewLineChart;
import ch.shanehofstetter.pvdimension.gui.components.widgets.VTitledPane;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.views
 */
public class SimulationResultView extends VTitledPane {
    private Insets defaultInsets = new Insets(5, 5, 5, 5);

    private PVWeeklyResultChart pvWeeklyResultChart;
    private PVWeekOverviewLineChart pvWeekOverviewChart;
    private PVTotalBarChart pvWeekTotalBarChart;
    private PVTotalEconomicsBarChart pvWeekTotalEconomicsBarChart;
    private PVTotalBarChart pvYearTotalBarChart;
    private PVTotalEconomicsBarChart pvYearTotalEconomicsBarChart;
    private PVYearOverviewLineChart pvYearOverviewLineChart;
    private PVAmortisationBarChart pvAmortisationBarChart;
    private Tab yearOverviewTab;
    private Tab weekOverviewTab;
    private Tab trendChartsTab;
    private Tab economyOverviewTab;
    private TabPane resultsTabPane;

    public SimulationResultView(String title, int spacing, Insets padding) {
        super(title, spacing, padding);
        resultsTabPane = new TabPane();
        resultsTabPane.setPadding(new Insets(0, 0, 0, 0));
        resultsTabPane.setMaxHeight(10000);

        trendChartsTab = new Tab("Verlauf");
        trendChartsTab.setClosable(false);

        pvWeeklyResultChart = new PVWeeklyResultChart();
        pvWeeklyResultChart.setPadding(defaultInsets);
        pvWeekOverviewChart = new PVWeekOverviewLineChart("Wochen-Verlauf");
        pvWeekOverviewChart.setPadding(defaultInsets);

        VBox trendChartsBox = new VBox(5);
        trendChartsBox.getChildren().addAll(pvWeeklyResultChart, pvWeekOverviewChart);
        trendChartsTab.setContent(trendChartsBox);
        resultsTabPane.getTabs().add(trendChartsTab);

        pvWeekTotalBarChart = new PVTotalBarChart();
        pvWeekTotalBarChart.setMaxHeight(300);
        pvWeekTotalEconomicsBarChart = new PVTotalEconomicsBarChart();
        pvWeekTotalEconomicsBarChart.setMaxHeight(300);
        VBox weekOverviewBarChartsBox = new VBox(5);
        weekOverviewBarChartsBox.getChildren().addAll(pvWeekTotalBarChart, pvWeekTotalEconomicsBarChart);

        weekOverviewTab = new Tab("Wochen-Übersicht");
        weekOverviewTab.setClosable(false);
        weekOverviewTab.setContent(weekOverviewBarChartsBox);
        resultsTabPane.getTabs().add(weekOverviewTab);

        pvYearOverviewLineChart = new PVYearOverviewLineChart("Jahres-Verlauf");
        pvYearOverviewLineChart.setMaxHeight(300);
        pvYearTotalBarChart = new PVTotalBarChart();
        pvYearTotalBarChart.setMaxHeight(300);
        pvYearTotalEconomicsBarChart = new PVTotalEconomicsBarChart();
        pvYearTotalEconomicsBarChart.setMaxHeight(300);
        VBox yearOverviewBarChartsBox = new VBox(5);
        yearOverviewBarChartsBox.getChildren().addAll(pvYearOverviewLineChart, pvYearTotalBarChart, pvYearTotalEconomicsBarChart);

        yearOverviewTab = new Tab("Jahres-Übersicht");
        yearOverviewTab.setClosable(false);
        yearOverviewTab.setContent(yearOverviewBarChartsBox);
        resultsTabPane.getTabs().add(yearOverviewTab);

        pvAmortisationBarChart = new PVAmortisationBarChart();
        pvAmortisationBarChart.setMaxHeight(300);

        economyOverviewTab = new Tab("Wirtschaftlichkeit");
        economyOverviewTab.setClosable(false);
        economyOverviewTab.setContent(new VBox(pvAmortisationBarChart));
        resultsTabPane.getTabs().add(economyOverviewTab);

        add(resultsTabPane);
    }

    public void showYearOverviewTab(boolean show) {
        if (show) {
            if (!resultsTabPane.getTabs().contains(yearOverviewTab))
                resultsTabPane.getTabs().add(yearOverviewTab);
        } else {
            if (resultsTabPane.getTabs().contains(yearOverviewTab))
                resultsTabPane.getTabs().remove(yearOverviewTab);
        }
    }

    public PVWeeklyResultChart getPvWeeklyResultChart() {
        return pvWeeklyResultChart;
    }

    public PVWeekOverviewLineChart getPvWeekOverviewChart() {
        return pvWeekOverviewChart;
    }

    public PVTotalBarChart getPvWeekTotalBarChart() {
        return pvWeekTotalBarChart;
    }

    public PVTotalEconomicsBarChart getPvWeekTotalEconomicsBarChart() {
        return pvWeekTotalEconomicsBarChart;
    }

    public PVTotalBarChart getPvYearTotalBarChart() {
        return pvYearTotalBarChart;
    }

    public PVTotalEconomicsBarChart getPvYearTotalEconomicsBarChart() {
        return pvYearTotalEconomicsBarChart;
    }

    public PVYearOverviewLineChart getPvYearOverviewLineChart() {
        return pvYearOverviewLineChart;
    }

    public PVAmortisationBarChart getPvAmortisationBarChart() {
        return pvAmortisationBarChart;
    }
}
