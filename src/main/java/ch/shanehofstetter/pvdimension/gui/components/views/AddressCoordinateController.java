package ch.shanehofstetter.pvdimension.gui.components.views;

import ch.shanehofstetter.pvdimension.gui.components.controllers.AddressController;
import ch.shanehofstetter.pvdimension.gui.components.controllers.CoordinateController;
import ch.shanehofstetter.pvdimension.location.Address;
import ch.shanehofstetter.pvdimension.location.Coordinates;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components
 */
public class AddressCoordinateController extends HBox {

    private AddressController addressController;
    private CoordinateController coordinateController;

    public AddressCoordinateController() {
        this(null, null);
    }

    public AddressCoordinateController(Address address, Coordinates coordinates) {
        addressController = new AddressController(address);
        getChildren().add(addressController);
        coordinateController = new CoordinateController(coordinates);
        getChildren().add(coordinateController);
        HBox.setHgrow(addressController, Priority.ALWAYS);
        HBox.setHgrow(coordinateController, Priority.ALWAYS);
        setSpacing(5);
    }

    public AddressController getAddressController() {
        return addressController;
    }

    public void setAddressController(AddressController addressController) {
        this.addressController = addressController;
    }

    public CoordinateController getCoordinateController() {
        return coordinateController;
    }

    public void setCoordinateController(CoordinateController coordinateController) {
        this.coordinateController = coordinateController;
    }

    public void reset() {
        addressController.reset();
        coordinateController.reset();
    }
}
