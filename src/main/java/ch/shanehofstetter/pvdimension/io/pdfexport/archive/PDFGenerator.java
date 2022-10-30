package ch.shanehofstetter.pvdimension.io.pdfexport.archive;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.embed.swing.SwingFXUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;


/**
 * This class Genarates a PDF File a Object of Type PDFDatacontainer is required
 *
 * @author Simon Müller : smueller@xiag.ch
 */
public class PDFGenerator {

    static final Logger logger = LogManager.getLogger();
    private static final int ALIGNEMENT_LEFT = 0;
    private static final int ALIGNEMENT_CETNER = 1;
    private static final int ALIGNEMENT_RIGHT = 2;

    public PDFGenerator(PDFDataContainer dataContainer) {
        Paragraph teaser;
        Paragraph title;
        try {
            Long currentTime = new Date().getTime();
            logger.debug(currentTime);
            String userHomeFolder = System.getProperty("user.home");
            OutputStream file = new FileOutputStream(new File(userHomeFolder, "Desktop/PVDimensionData_" + currentTime + ".pdf"));
            Document document = new Document(PageSize.A4, 20, 20, 50, 25);
            PdfWriter.getInstance(document, file);
            document.open();
            document.setMargins(80, 80, 0, 0);

            //Image headerImage = Image.getInstance("PVD.png");
            //headerImage.setAlignment(ALIGNEMENT_CETNER);
            //headerImage.scaleToFit(400, 400);
            String titlePageTitle = "PVDimenson PDF Export vom: \n" + new Date().toString();
            Paragraph titlePageHeader = new Paragraph(titlePageTitle);
            titlePageHeader.setFont(new Font(Font.FontFamily.SYMBOL, 15, 2));
            titlePageHeader.setAlignment(ALIGNEMENT_CETNER);
            titlePageHeader.setSpacingBefore(20);
            titlePageHeader.setSpacingAfter(80);
            document.add(titlePageHeader);
            //  document.add(headerImage);
            for (int i = 0; i < dataContainer.size(); i++) {
                document.newPage();
                title = new Paragraph(dataContainer.getTitles().get(i));
                title.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 18, 5, BaseColor.BLUE));
                title.setAlignment(ALIGNEMENT_CETNER);
                document.add(title);
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                ImageIO.write(SwingFXUtils.fromFXImage(dataContainer.getWritableImages().get(i), null), "png", byteOutput);
                com.itextpdf.text.Image graph;
                graph = com.itextpdf.text.Image.getInstance(byteOutput.toByteArray());
                graph.scaleToFit(400, 400);
                graph.setAlignment(ALIGNEMENT_CETNER);

                document.add(graph);
                teaser = new Paragraph(dataContainer.getTeasers().get(i));
                document.add(teaser);

            }

            document.addCreationDate();
            document.addTitle("PVDimension Export");
            document.addAuthor("Shane Hofstetter, Simon Müller");
            document.addCreator("PVDimension PDFGenerator");
            //document.addHeader("TEST+", "SDFSA");
            document.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

}
