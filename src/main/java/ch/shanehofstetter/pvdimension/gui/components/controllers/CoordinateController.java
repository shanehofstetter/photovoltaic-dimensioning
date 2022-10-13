package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.gui.components.views.CoordinateView;
import ch.shanehofstetter.pvdimension.location.Coordinates;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components
 */
public class CoordinateController extends CoordinateView {

    private static final Coordinates DEFAULT_COORDINATES = new Coordinates(0, 0);
    private Coordinates coordinates;

    public CoordinateController(Coordinates coordinates) {
        setCoordinates(coordinates);
    }

    public CoordinateController() {
        this(DEFAULT_COORDINATES);
        setValues();
    }

    /**
     * reset the coordinates to default
     */
    public void reset() {
        this.coordinates = DEFAULT_COORDINATES;
        setValues();
    }

    private void setValues() {
        longitudeValue.setText(coordinates.getLongitude() + "\u00B0");
        latitudeValue.setText(coordinates.getLatitude() + "\u00B0");
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            coordinates = DEFAULT_COORDINATES;
        }
        this.coordinates = coordinates;
        setValues();
    }
}
