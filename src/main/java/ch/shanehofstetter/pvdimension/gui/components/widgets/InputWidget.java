
package ch.shanehofstetter.pvdimension.gui.components.widgets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class InputWidget extends HBox {

    static final Logger logger = LogManager.getLogger();
    protected String labelText;
    protected boolean useSlider;
    protected Slider slider;
    protected TextField textField;
    protected Label unitLabel;
    protected String unitText = "";
    protected Label textLabel;
    protected double sliderMin;
    protected double sliderMax;
    protected double value = 0.0;
    private double precision = 1000.0;
    private double labelMinWidth = 120;
    private INPUT_TYPE valueType;
    private List<InputWidgetListener> listeners = new ArrayList<>();

    public InputWidget(String labelText) {
        this(labelText, false, INPUT_TYPE.DOUBLE, 0.0, 0.0, 0);
    }

    public InputWidget(String labelText, boolean useSlider) {
        this(labelText, useSlider, INPUT_TYPE.DOUBLE, 0, 100, 0);
    }

    public InputWidget(String labelText, boolean useSlider, INPUT_TYPE inputType) {
        this(labelText, useSlider, inputType, 0, 100, 0);
    }

    public InputWidget(String labelText, boolean useSlider, INPUT_TYPE inputType, double sliderMin, double sliderMax) {
        this(labelText, useSlider, inputType, sliderMin, sliderMax, 0);
    }

    public InputWidget(String labelText, boolean useSlider, INPUT_TYPE inputType, double sliderMin, double sliderMax, double initialValue) {
        this.labelText = labelText;
        this.useSlider = useSlider;
        this.valueType = inputType;

        this.sliderMin = sliderMin;
        this.sliderMax = sliderMax;
        setSpacing(8);
        setPadding(new Insets(0, 10, 0, 10));
        setAlignment(Pos.CENTER_LEFT);

        textLabel = new Label(this.labelText);
        textLabel.setMinWidth(labelMinWidth);
        if (inputType == INPUT_TYPE.DOUBLE) {
            textField = new DoubleTextField();
        } else {
            textField = new IntegerTextField();
        }
        textField.setMinWidth(75);
        textField.setMaxWidth(75);
        textField.setEditable(true);
        textField.setOnAction(ev -> textChanged());

        unitLabel = new Label(unitText);
        if (useSlider) {
            slider = new Slider();
            slider.setMin(sliderMin);
            slider.setMax(sliderMax);

            //Not very good because it gets fired too often..
//            slider.valueProperty().addListener((observable, oldValue, newValue) -> {
//                if (!slider.isValueChanging()){
//                    logger.debug("slider changed: "+slider.getValue());
//                    sliderValueChanged();
//                }
//            });

            slider.setOnMouseDragReleased(ev -> sliderValueChanged());
//            slider.setOnMouseExited(ev -> sliderValueChanged());
            slider.setOnMouseReleased(ev -> sliderValueChanged());
            slider.setOnDragExited(ev -> sliderValueChanged());
            slider.setOnDragDone(ev -> sliderValueChanged());
            slider.setOnKeyReleased(ev -> sliderValueChanged());

            setHgrow(slider, Priority.ALWAYS);
            this.getChildren().addAll(textLabel, textField, unitLabel, slider);
        } else {
            this.getChildren().addAll(textLabel, textField, unitLabel);
        }
        setValue(initialValue);
    }

    public void setUnitText(String unitText) {
        this.unitText = unitText;
        unitLabel.setText(unitText);
    }

    public void setSliderMax(double sliderMax) {
        if (slider != null) {
            slider.setMax(sliderMax);
        }
        this.sliderMax = sliderMax;
        checkTextFieldMax();
    }

    public void setSliderMin(double sliderMin) {
        if (slider != null) {
            slider.setMin(sliderMin);
        }
        this.sliderMin = sliderMin;
    }

    public Slider getSlider() {
        return slider;
    }

    public TextField getTextField() {
        return textField;
    }

    public double getLabelMinWidth() {
        return labelMinWidth;
    }

    public void setLabelMinWidth(double labelMinWidth) {
        this.labelMinWidth = labelMinWidth;
        textLabel.setMinWidth(labelMinWidth);
    }

    private void sliderValueChanged() {
        synchronizeValues(Math.round(slider.getValue() * precision) / precision);
        valueChanged(value);
    }

    private void textChanged() {
        double val = Double.parseDouble(textField.getText());
        if (val < sliderMin) {
            setValue(sliderMin);
        } else if (val > sliderMax) {
            setValue(sliderMax);
        } else {
            synchronizeValues(val);
            valueChanged(value);
        }
    }

    private void checkTextFieldMax() {
        double val = Double.parseDouble(textField.getText());
        if (val > sliderMax) {
            setValue(sliderMax);
        }
    }

    private void synchronizeValues(double newVal) {
        setValue(newVal);
    }

    public void setValue(double value) {
        this.value = value;
        if (slider != null) {
            slider.setValue(value);
        }
        if (valueType == INPUT_TYPE.DOUBLE) {
            textField.setText(value + "");
        } else {
            textField.setText((int) value + "");
        }
//        textField.setText(value+"");
    }

    /**
     * precision:
     * Default 1000.0 means 3 digits after comma (3.423)
     * 100.0 means 2digits after comma (3.42)
     * etc.
     */
    public double getPrecision() {
        return precision;
    }

    /**
     * precision:
     * Default 1000.0 means 3 digits after comma (3.423)
     * 100.0 means 2digits after comma (3.42)
     * etc.
     */
    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public void addListener(InputWidgetListener toAdd) {
        listeners.add(toAdd);
    }

    public void valueChanged(double value) {
        for (InputWidgetListener listener : listeners)
            listener.valueChanged(value);
    }

    public void setTooltipText(String tooltipText) {
        textLabel.setTooltip(new Tooltip(tooltipText));
        textField.setTooltip(new Tooltip(tooltipText));
        if (useSlider)
            slider.setTooltip(new Tooltip(tooltipText));
    }

    public enum INPUT_TYPE {
        INTEGER,
        DOUBLE
    }

    public interface InputWidgetListener {
        void valueChanged(double value);

    }
}

