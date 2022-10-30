package ch.shanehofstetter.pvdimension.gui.components.widgets;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;



public final class UserInputWidget extends HBox {

    private static final double MINFADERVALUE = 0;
    private static final double MAXFADERVALUE = 100;
    private String widgetLabel;
    private double minFaderValue;
    private double maxFaderValue;
    private double initValue;
    private boolean isFader;
    private Label textLabel;
    private Slider userFader;
    private TextField inputField;

    public UserInputWidget(String widgetLabel) {
        this(widgetLabel, false);
    }

    public UserInputWidget(String widgetLabel, boolean isFader) {
        this(widgetLabel, isFader, MINFADERVALUE, MAXFADERVALUE, 0);
    }

    public UserInputWidget(String widgetLabel, boolean isFader, double minValue, double maxValue, double initValue) {
        this.widgetLabel = widgetLabel;
        this.isFader = isFader;
        this.minFaderValue = minValue;
        this.maxFaderValue = maxValue;
        this.initValue = initValue;
        this.createInputWidget();
    }

    public void createInputWidget() {
        this.textLabel = new Label();
        this.inputField = new TextField(this.widgetLabel);
        this.getChildren().addAll(textLabel, inputField);
        if (isFader) {
            this.userFader = new Slider(this.minFaderValue, this.maxFaderValue, this.initValue);
            this.userFader.setShowTickMarks(true);
            this.userFader.setMajorTickUnit(50);
            this.userFader.getOnMouseReleased();
            this.getChildren().add(userFader);
            this.setSpacing(10.0);
        }

    }

    /**
     * @return the textLabel
     */
    public Label getTextLabel() {
        return textLabel;
    }

    /**
     * @return the userFader
     */
    public Slider getUserFader() {
        return userFader;
    }

    /**
     * @return the inputField
     */
    public TextField getInputField() {
        return inputField;
    }

}
