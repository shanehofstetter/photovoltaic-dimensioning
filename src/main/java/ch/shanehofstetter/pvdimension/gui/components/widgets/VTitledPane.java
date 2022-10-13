package ch.shanehofstetter.pvdimension.gui.components.widgets;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.widgets
 */
public class VTitledPane extends TitledPane {

    private VBox layoutBox;

    public VTitledPane(String title) {
        this(title, 5);
    }

    public VTitledPane(String title, int spacing) {
        this(title, spacing, new Insets(5, 5, 5, 5));
    }

    public VTitledPane(String title, int spacing, Insets padding) {
        layoutBox = new VBox(spacing);
        layoutBox.setPadding(padding);
        setText(title);
        setContent(layoutBox);
        layoutBox.setFillWidth(true);
        setCollapsible(false);
    }

    public void add(Node node) {
        layoutBox.getChildren().add(node);
        VBox.setVgrow(node, Priority.ALWAYS);
    }
}
