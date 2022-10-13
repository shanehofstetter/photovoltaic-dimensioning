package ch.shanehofstetter.pvdimension.gui.components.controllers;

/**
 * Author: Shane Hofstetter - shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.controllers
 */
public enum UserDataStatus {
    DIRTY,
    UNCHANGED;

    @Override
    public String toString() {
        switch (this) {
            case DIRTY:
                return "Dirty";
            case UNCHANGED:
                return "Unchanged";
            default:
                throw new IllegalArgumentException();
        }
    }
}
