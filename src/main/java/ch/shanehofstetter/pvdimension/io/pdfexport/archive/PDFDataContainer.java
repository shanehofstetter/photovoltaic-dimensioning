
package ch.shanehofstetter.pvdimension.io.pdfexport.archive;

import javafx.scene.image.WritableImage;

import java.util.ArrayList;

/**
 * @author Simon Müller : smueller@xiag.ch
 */
public class PDFDataContainer {

    private ArrayList<WritableImage> images;
    private ArrayList<String> titles;
    private ArrayList<String> teasers;

    public PDFDataContainer() {
        images = new ArrayList<>();
        titles = new ArrayList<>();
        teasers = new ArrayList<>();
    }

    /**
     * @return the images
     */
    public ArrayList<WritableImage> getWritableImages() {
        return images;
    }

    /**
     * @return the titles
     */
    public ArrayList<String> getTitles() {
        return titles;
    }

    /**
     * @return the teasers
     */
    public ArrayList<String> getTeasers() {
        return teasers;
    }

    public void addData(WritableImage image) {
        this.addData(image, "");
    }

    public void addData(WritableImage image, String title) {
        this.addData(image, "", "");
    }

    public void addData(WritableImage image, String title, String teaser) {
        this.images.add(image);
        this.titles.add(title);
        this.teasers.add(teaser);
    }

    public int size() {
        return images.size();
    }

}
