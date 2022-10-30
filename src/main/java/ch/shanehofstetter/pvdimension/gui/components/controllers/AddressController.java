package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.gui.components.views.AddressView;
import ch.shanehofstetter.pvdimension.location.Address;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AddressController extends AddressView {
    private static final Logger logger = LogManager.getLogger();
    private static final Address DEFAULT_ADDRESS = new Address("", "", "Baden", "", "Schweiz");

    private Address address;
    private ArrayList<AddressControlListener> listeners = new ArrayList<>();

    public AddressController() {
        this(DEFAULT_ADDRESS);
    }

    public AddressController(Address address) {
        super();
        setAddress(address);
        streetField.textProperty().addListener(this::streetChanged);
        streetnumberField.textProperty().addListener(this::streetNumberChanged);
        zipField.textProperty().addListener(this::zipChanged);
        cityField.textProperty().addListener(this::cityChanged);
        countryField.textProperty().addListener(this::countryChanged);
        submitButton.setOnAction(this::submitClicked);
    }

    public void setAddress(Address address) {
        if (address == null) {
            address = DEFAULT_ADDRESS;
        }
        this.address = address;
        setAddressTexts(address);
    }

    private void setAddressTexts(Address address) {
        this.cityField.setText(address.getCity());
        this.zipField.setText(address.getZipCode());
        this.countryField.setText(address.getCountry());
        this.streetField.setText(address.getStreet());
        this.streetnumberField.setText(address.getStreetNumber());
    }

    public void reset() {
        this.address = DEFAULT_ADDRESS;
        this.setAddressTexts(this.address);
    }

    private void submitClicked(ActionEvent actionEvent) {
        logger.debug("submitting address: " + address);
        addressChanged();
    }

    private void streetChanged(Observable observable) {
        logger.debug("street: " + streetField.getText());
        address.setStreet(streetField.getText());
    }

    private void zipChanged(Observable observable) {
        logger.debug("zip: " + zipField.getText());
        address.setZipCode(zipField.getText());
    }

    private void countryChanged(Observable observable) {
        logger.debug("country: " + countryField.getText());
        address.setCountry(countryField.getText());
    }

    private void cityChanged(Observable observable) {
        logger.debug("city: " + cityField.getText());
        address.setCity(cityField.getText());
    }

    private void streetNumberChanged(Observable observable) {
        logger.debug("streetnumber: " + streetnumberField.getText());
        address.setStreetNumber(streetnumberField.getText());
    }

    private void addressChanged() {
        for (AddressControlListener listener : listeners) {
            listener.addressChanged(address);
        }
    }

    public void addListener(AddressControlListener addressControlListener) {
        this.listeners.add(addressControlListener);
    }

    public interface AddressControlListener {
        void addressChanged(Address address);
    }

}
