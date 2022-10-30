package ch.shanehofstetter.pvdimension;


import javafx.scene.image.Image;

/**
 * Application Info stores general information about the application like strings, icons, etc.
 */
public class ApplicationInfo {
    public static final String APPLICATION_TITLE_LONG = "Photovoltaik Ertrags-Simulation und Dimensionierung";
    public static final String APPLICATION_DETAIL = "Semesterarbeit ABBTS - Studiengang Informatik";
    public static final String APPLICATION_TITLE_SHORT = "PV-Simulator";
    public static final String APPLICATION_ABOUT_TEXT = "Build: \n" + BuildInfo.VERSION_NUMBER + "\n" + BuildInfo.BUILD_TIME
            + "\n\nEntwickler:\n"
            + "Shane Hofstetter \t shane.hofstetter@gmail.com\n"
            + "Simon Müller";
    public static final String APPLICATION_ICON_NAME = "pvdimension_icon_128.png";
    public static Image APPLICATION_IMAGE;

}
