package ch.shanehofstetter.pvdimension.io.pdfexport;

import ch.shanehofstetter.pvdimension.ApplicationInfo;
import ch.shanehofstetter.pvdimension.economy.Economy;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.barchart.PVAmortisationBarChart;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.barchart.PVTotalBarChart;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.barchart.PVTotalEconomicsBarChart;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart.PVWeekOverviewLineChart;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart.PVYearOverviewLineChart;
import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVWeek;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.chart.Chart;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * PVReportCreator generates a PDF Report covering all important information
 * about the simulated PV-Generator
 * <p>
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.io.pdfexport
 */
public class PVReportCreator {
    private File outputFile;
    private PdfWriter pdfWriter;
    private Document document;

    private Font catFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);
    private Font subFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);

    private PVGenerator pvGenerator;
    private PVWeek pvWeek;

    /**
     * @param outputFile file to save the pdf to
     */
    public PVReportCreator(File outputFile) {
        this.outputFile = outputFile;
        int leftMargin = 40;
        int rightMargin = 20;
        int topMargin = 40;
        int bottomMargin = 20;
        this.document = new Document(PageSize.A4, leftMargin, rightMargin, topMargin, bottomMargin);
    }

    /**
     * @param paragraph paragraph
     * @param number    number of empty lines
     */
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    /**
     * Make the PDF with the given information from PVGenerator and PVWeek
     *
     * @param pvGenerator pvGenerator to generate the report from
     * @throws Exception if a needed value is null
     */
    public void makePDF(PVGenerator pvGenerator) throws Exception {
        this.pvWeek = pvGenerator.getSimulationData().getPvWeek();
        this.pvGenerator = pvGenerator;
        pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        document.open();
        addMetaData(document);
        addTitlePage();
        addContent(document);
        document.close();
    }

    /**
     * add some meta data to the pdf
     *
     * @param document document
     */
    private void addMetaData(Document document) {
        document.addTitle(ApplicationInfo.APPLICATION_TITLE_SHORT + " Projektbericht");
        document.addSubject(ApplicationInfo.APPLICATION_ABOUT_TEXT);
        document.addAuthor(ApplicationInfo.APPLICATION_TITLE_SHORT);
        document.addCreator(ApplicationInfo.APPLICATION_TITLE_SHORT);
    }

    /**
     * add the title page, import it from title_page.pdf
     * does not generate the page
     *
     * @throws DocumentException
     * @throws IOException
     */
    private void addTitlePage()
            throws DocumentException, IOException {
        // Load title_page.pdf and add it to document
//        URL titlePagePath = getClass().getResource("res/title_page.pdf");
//        System.out.println(titlePagePath.getPath());
        PdfReader reader = new PdfReader(getClass().getResourceAsStream("res/title_page.pdf"));

        PdfImportedPage page = pdfWriter.getImportedPage(reader, 1);
        PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
        pdfContentByte.addTemplate(page, 0, 0);
    }

    /**
     * add the actual pdf content
     *
     * @param document document
     * @throws DocumentException
     * @throws IOException
     */
    private void addContent(Document document) throws DocumentException, IOException {
        createParameterChapter(document, 1);
        createCostChapter(document, 2);
        createSimulationResultsChapter(document, 3);
    }

    /**
     * adds the PV Parameter chapter to given document
     *
     * @param document      document
     * @param chapterNumber chapter number
     * @throws DocumentException
     */
    private void createParameterChapter(Document document, int chapterNumber) throws DocumentException {
        Anchor title = new Anchor("Photovoltaik-Anlagenparameter", catFont);
        title.setName("Photovoltaik-Anlagenparameter");

        Chapter parametersChapter = new Chapter(new Paragraph(title), chapterNumber);

        // Panel
        Paragraph panelParagraph = new Paragraph("PV-Panel", subFont);
        Section panelSection = parametersChapter.addSection(panelParagraph);

        createPanelTable(panelSection);

        // Battery
        Paragraph batteryParagraph = new Paragraph("Batterie", subFont);
        Section batterySection = parametersChapter.addSection(batteryParagraph);

        createBatteryTable(batterySection);

        // add Chapter to document
        document.add(parametersChapter);
    }

    /**
     * adds the costs chapter to the given document
     *
     * @param document      document
     * @param chapterNumber chapter number
     * @throws DocumentException
     */
    private void createCostChapter(Document document, int chapterNumber) throws DocumentException {
        Anchor costsTitle = new Anchor("Kosten", catFont);
        costsTitle.setName("Kosten");

        Chapter costsChapter = new Chapter(new Paragraph(costsTitle), chapterNumber);

        // Power Costs
        Paragraph powerPricesParagraph = new Paragraph("Strompreise", subFont);
        Section powerPricesSection = costsChapter.addSection(powerPricesParagraph);

        createPowerPricesTable(powerPricesSection);

        // Project Costs
        Paragraph projectCostParagraph = new Paragraph("Projektkosten", subFont);
        Section projectCostSection = costsChapter.addSection(projectCostParagraph);

        createProjectCostTable(projectCostSection);

        // add Chapter to document
        document.add(costsChapter);
    }

    /**
     * adds the simulation results chapter to the document
     * with some charts
     *
     * @param document      document
     * @param chapterNumber chapter number
     * @throws DocumentException
     * @throws IOException
     */
    private void createSimulationResultsChapter(Document document, int chapterNumber) throws DocumentException, IOException {
        Anchor simulationResultsTitle = new Anchor("Simulationsergebnisse", catFont);
        simulationResultsTitle.setName("Simulationsergebnisse");
        Chapter simulationResultsChapter = new Chapter(new Paragraph(simulationResultsTitle), chapterNumber);

        // Week Line-Chart
        String month = pvGenerator.getSimulationParameters().getSimulatingMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN);
        Paragraph weekOverviewParagraph = new Paragraph("Wochenverlauf im " + month, subFont);
        Section weekOverviewSection = simulationResultsChapter.addSection(weekOverviewParagraph);
        PVWeekOverviewLineChart overviewLineChart = new PVWeekOverviewLineChart("Wochenverlauf");
        overviewLineChart.setAnimated(false);
        overviewLineChart.setPvWeek(pvWeek);
        addChart(weekOverviewSection, overviewLineChart);

        // Week Overview Bar Chart
        Paragraph totalOverviewParagraph = new Paragraph("Wochenübersicht im " + month, subFont);
        Section totalOverviewSection = simulationResultsChapter.addSection(totalOverviewParagraph);
        PVTotalBarChart pvTotalBarChart = new PVTotalBarChart();
        pvTotalBarChart.getBarChart().setAnimated(false);
        pvTotalBarChart.setPVSimulationResult(pvWeek);
        addChart(totalOverviewSection, pvTotalBarChart.getBarChart());

        // add some extra space so the next paragraph gets on new page
        // not the best approach but it works..
        Paragraph spaceParagraph = new Paragraph("");
        addEmptyLine(spaceParagraph, 9);
        totalOverviewSection.add(spaceParagraph);

        // Power Costs Overview
        Paragraph powerCostsParagraph = new Paragraph("Übersicht Stromkosten für eine Woche im " + month, subFont);
        Section powerCostsSection = simulationResultsChapter.addSection(powerCostsParagraph);
        powerCostsParagraph.setKeepTogether(true);
        PVTotalEconomicsBarChart pvTotalEconomicsBarChart = new PVTotalEconomicsBarChart();
        pvTotalEconomicsBarChart.getBarChart().setAnimated(false);
        pvTotalEconomicsBarChart.setPVData(pvWeek);
        addChart(powerCostsSection, pvTotalEconomicsBarChart.getBarChart());

        if (pvGenerator.getSimulationData().getPvMonths() != null && pvGenerator.getSimulationData().getYearTotal() != null) {

            // add pseudo page-break
            Paragraph yearSpaceParagraph = new Paragraph("");
            addEmptyLine(yearSpaceParagraph, 26);
            powerCostsSection.add(yearSpaceParagraph);

            // Monthly Line-Chart
            Paragraph yearTrendParagraph = new Paragraph("Jahresverlauf", subFont);
            Section yearTrendSection = simulationResultsChapter.addSection(yearTrendParagraph);
            PVYearOverviewLineChart yearTrendChart = new PVYearOverviewLineChart("Jahresverlauf");
            yearTrendChart.setAnimated(false);
            yearTrendChart.setPVMonths(pvGenerator.getSimulationData().getPvMonths());
            addChart(yearTrendSection, yearTrendChart);

            // Year Overview Bar Chart
            Paragraph yearOverviewParagraph = new Paragraph("Jahresübersicht", subFont);
            Section yearOverviewSection = simulationResultsChapter.addSection(yearOverviewParagraph);
            PVTotalBarChart yearTotalBarChart = new PVTotalBarChart();
            yearTotalBarChart.getBarChart().setAnimated(false);
            yearTotalBarChart.setPVSimulationResult(pvGenerator.getSimulationData().getYearTotal());
            addChart(yearOverviewSection, yearTotalBarChart.getBarChart());

            // add pseudo page-break
            Paragraph spaceParagraph3 = new Paragraph("");
            addEmptyLine(spaceParagraph3, 10);
            yearOverviewSection.add(spaceParagraph3);

            // Power Costs Overview
            Paragraph yearlyPowerCostsParagraph = new Paragraph("Jahresübersicht der Stromkosten", subFont);
            Section yearlyPowerCostSection = simulationResultsChapter.addSection(yearlyPowerCostsParagraph);
            yearlyPowerCostsParagraph.setKeepTogether(true);

            PVTotalEconomicsBarChart yearlyEconomicsChart = new PVTotalEconomicsBarChart();
            yearlyEconomicsChart.getBarChart().setAnimated(false);
            yearlyEconomicsChart.setPVData(pvGenerator.getSimulationData().getYearTotal());
            addChart(yearlyPowerCostSection, yearlyEconomicsChart.getBarChart());

            Paragraph amortizationParagraph = new Paragraph("Amortisation", subFont);
            Section amortizationSection = simulationResultsChapter.addSection(amortizationParagraph);
            amortizationParagraph.setKeepTogether(true);
            PVAmortisationBarChart pvAmortisationBarChart = new PVAmortisationBarChart();
            pvAmortisationBarChart.getBarChart().setAnimated(false);
            pvAmortisationBarChart.setPVData(pvGenerator.getSimulationData().getPvAmortizationTime());
            addChart(amortizationSection, pvAmortisationBarChart.getBarChart());

        }

        // add Chapter to document
        document.add(simulationResultsChapter);
    }

    /**
     * add a chart to a section
     * scales the image to page width with keeping the proportions
     *
     * @param section section
     * @param chart   chart
     * @throws IOException
     * @throws BadElementException
     */
    private void addChart(Section section, Chart chart) throws IOException, BadElementException {
        ChartSnapshot snapshot = new ChartSnapshot(chart);
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        ImageIO.write(SwingFXUtils.fromFXImage(snapshot.getImage(), null), "png", byteOutput);
        Image graph = Image.getInstance(byteOutput.toByteArray());
        float scaleFactor = ((document.getPageSize().getWidth() - document.leftMargin()
                - document.rightMargin()) / graph.getWidth()) * 100;
        graph.scalePercent(scaleFactor);
        section.add(graph);
    }

    /**
     * adds the project costs table to the given section
     *
     * @param projectCostSection section
     */
    private void createProjectCostTable(Section projectCostSection) {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(90);
        table.setHeaderRows(0);
        table.setSpacingAfter(20);
        table.setSpacingBefore(20);
        table.addCell("Photovolatikpanel Stk.");
        table.addCell(pvGenerator.getSolarPanelField().getSolarPanel().getCost().doubleValue() + " " + Economy.getCurrencyCode());
        table.addCell("Batterie");
        table.addCell(pvGenerator.getBattery().getCost().doubleValue() + " " + Economy.getCurrencyCode());
        table.addCell("Wechselrichter");
        table.addCell(pvGenerator.getInverter().getCost().doubleValue() + " " + Economy.getCurrencyCode());
        table.addCell("Planung und Installation");
        table.addCell(pvGenerator.getPlanningAndInstallationCosts().doubleValue() + " " + Economy.getCurrencyCode());

        projectCostSection.add(table);
    }

    /**
     * adds the power prices table to the given section
     *
     * @param section section
     */
    private void createPowerPricesTable(Section section) {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(90);
        table.setSpacingAfter(20);
        table.setSpacingBefore(20);
        table.setHeaderRows(0);
        table.addCell("Einkaufspreis / kWh");
        table.addCell(pvGenerator.getPowerPrice().getPurchaseKwhPrice() + " " + Economy.getCurrencyCode() + "/kWh");
        table.addCell("Verkaufspreis / kWh");
        table.addCell(pvGenerator.getPowerPrice().getSellingKwhPrice() + " " + Economy.getCurrencyCode() + "/kWh");

        section.add(table);
    }

    /**
     * adds the panel table to the given section
     *
     * @param section section
     */
    private void createPanelTable(Section section) {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(90);
        table.setSpacingAfter(20);
        table.setSpacingBefore(20);
        table.setHeaderRows(0);
        table.addCell("Anzahl");
        table.addCell(pvGenerator.getSolarPanelField().getAmount() + " Stk.");
        table.addCell("Leistung");
        table.addCell(pvGenerator.getSolarPanelField().getSolarPanel().getPower() + " W");
        table.addCell("Grösse");
        table.addCell(pvGenerator.getSolarPanelField().getSolarPanel().getSize() + " m²");
        table.addCell("Wirkungsgrad");
        table.addCell(pvGenerator.getSolarPanelField().getSolarPanel().getEfficiencyPercentage() + "%");
        table.addCell("Aufstellwinkel");
        table.addCell(pvGenerator.getSolarPanelField().getVerticalAngle() + "°");
        table.addCell("Azimut");
        table.addCell(pvGenerator.getSolarPanelField().getAzimut() + "°");

        section.add(table);
    }

    /**
     * adds the battery table to the given section
     *
     * @param section section
     */
    private void createBatteryTable(Section section) {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(90);
        table.setHeaderRows(0);
        table.setSpacingAfter(20);
        table.setSpacingBefore(20);
        table.addCell("Kapazität");
        table.addCell(pvGenerator.getBattery().getCapacitykWh() + " kWh");

        section.add(table);
    }

}