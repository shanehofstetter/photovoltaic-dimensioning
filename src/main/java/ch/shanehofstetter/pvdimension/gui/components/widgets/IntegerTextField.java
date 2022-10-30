package ch.shanehofstetter.pvdimension.gui.components.widgets;

import javafx.scene.control.TextField;


public class IntegerTextField extends TextField {
    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
            checkIfEmpty();
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
            checkIfEmpty();
        }
    }

    private void checkIfEmpty() {
        if (getText().equals("")) {
            setText("0");
        }
    }

    private boolean validate(String text) {
        return ("".equals(text) || text.matches("[0-9]"));
    }

    public int getValue() {
        return Integer.parseInt(getText());
    }
}
