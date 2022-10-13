package ch.shanehofstetter.pvdimension.gui.components.widgets;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.widgets
 */
public class ValueLabelPair extends HBox {
    protected Label titleLabel;
    protected Label valueLabel;

    public ValueLabelPair(String title, String value, boolean disabled) {
        titleLabel = new Label(title);
        valueLabel = new Label(value);
        setSpacing(5);
        getChildren().addAll(titleLabel, valueLabel);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setValue(String value) {
        valueLabel.setText(value);
    }

    public void setTitleLabelWidth(int width) {
        titleLabel.setMinWidth(width);
        titleLabel.setMaxWidth(width);
    }

    public void setTooltipText(String text) {
        titleLabel.setTooltip(new Tooltip(text));
        valueLabel.setTooltip(new Tooltip(text));
    }
}
