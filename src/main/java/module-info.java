module ch.shanehofstetter.pvdimension {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.prefs;
    requires javafx.web;
    requires itext.xtra;
    requires javafx.swing;
    requires itextpdf;
    requires org.apache.logging.log4j;

    opens ch.shanehofstetter.pvdimension to javafx.fxml;
    opens ch.shanehofstetter.pvdimension.pvgenerator.solarpanel to javafx.base;
    exports ch.shanehofstetter.pvdimension;
}