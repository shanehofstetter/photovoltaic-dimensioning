module ch.shanehofstetter.pvdimension {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.slf4j;
    requires java.prefs;
    requires javafx.web;
    requires itext.xtra;
    requires javafx.swing;
    requires itextpdf;

    opens ch.shanehofstetter.pvdimension to javafx.fxml;
    exports ch.shanehofstetter.pvdimension;
}