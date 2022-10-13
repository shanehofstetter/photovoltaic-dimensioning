package ch.shanehofstetter.pvdimension.gui.components.widgets;

import javafx.scene.control.TextField;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension
 */
public class DoubleTextField extends TextField {

    private double minValue = -Double.MAX_VALUE;
    private double maxValue = Double.MAX_VALUE;

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
            checkIfEmpty();
            checkRange();
        }
    }

    private void checkRange() {
        if (getText().equals("-")) {
            return;
        }
        try {
            double value = Double.parseDouble(getText());
            if (value < getMinValue()) {
                setValue(minValue);
            } else if (value > getMaxValue()) {
                setValue(maxValue);
            }
        } catch (Exception ex) {
            System.out.println("exception, getText: " + getText());
            // user did not finish input
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
            checkIfEmpty();
            checkRange();
        }
    }

    private void checkIfEmpty() {
        if (getText().equals("")) {
            setValue(0);
        }
    }

    private boolean validate(String text) {
        if ("".equals(text)) {
            return true;
        } else if (getText().contains(".") && ".".equals(text)) {
            return false;
        } else if (getText().contains("-") && "-".equals(text)) {
            return false;
        } else if (".".equals(text)) {
            return true;
        } else
            return "-".equals(text) || text.matches("[0-9]");
    }

    /**
     * @return value
     */
    public double getValue() {
        try {
            return Double.parseDouble(getText());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    /**
     * set the value to display
     *
     * @param value value
     */
    public void setValue(double value) {
        setText(Double.toString(value));
    }

    /**
     * @return minimum value
     */
    public double getMinValue() {
        return minValue;
    }

    /**
     * @param minValue minimum value
     */
    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    /**
     * @return maximum value
     */
    public double getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxValue maximum value
     */
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
}
