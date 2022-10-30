package ch.shanehofstetter.pvdimension.location;

import java.io.Serializable;


public class Address implements Serializable {
    private String street;
    private String streetNumber;
    private String city;
    private String zipCode;
    private String country;

    public Address(String street, String streetNumber, String city, String zipCode, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Address(String street, String streetNumber, String city, String zipCode) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.zipCode = zipCode;
    }

    public Address(String street, String streetNumber, String city) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
    }

    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    public Address() {

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String toString() {
        StringBuilder strAddress = new StringBuilder();
        if (getStreet() != null) {
            strAddress.append(getStreet()).append("+");
        }
        if (getStreetNumber() != null) {
            strAddress.append(getStreetNumber()).append(",+");
        }
        if (getZipCode() != null) {
            strAddress.append(getZipCode()).append("+");
        }
        if (getCity() != null) {
            strAddress.append(getCity()).append(",+");
        }
        if (getCountry() != null) {
            strAddress.append(getCountry());
        }
        return strAddress.toString();
    }
}
