package ch.shanehofstetter.pvdimension.location;

import ch.shanehofstetter.pvdimension.net.HttpGetRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * Get the Coordinates for given Address, uses Google Maps Api
 * Therefore an internet connection is required
 */
public class GeoCoordinateRequest {

    static final Logger logger = LogManager.getLogger();
    private static final String WEBSERVICE_ADDRESS = "https://maps.googleapis.com/maps/api/geocode/xml?address=";

    /**
     * Get the Coordinates for given Address
     *
     * @param address the address to get the Coordinates for, the more precise the better
     * @return Coordinates object containing latitude and longitude
     * @throws Exception if no internet-connection or server unreachable or address insufficient
     */
    public static Coordinates getCoordinatesForAddress(Address address) throws Exception {
        String completeUrl = WEBSERVICE_ADDRESS + "\"" + address.toString() + "\"";
        try {
            String xmlResult = HttpGetRequest.sendGETRequest(completeUrl);
            return getCoordinatesFromXMLResult(xmlResult);
        } catch (Exception exception) {
            logger.error("Error while retrieving coordinates: " + exception.getMessage(), exception);
            throw exception;
        }
    }

    /**
     * @param xml XML String containing Coordinates
     * @return Coordinates object containing the found latitude and longitude
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws NumberFormatException
     */
    private static Coordinates getCoordinatesFromXMLResult(String xml) throws ParserConfigurationException, IOException, SAXException, NumberFormatException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        Document document = documentBuilder.parse(is);
        try {
            double latitude = Double.parseDouble(document.getElementsByTagName("lat").item(0).getTextContent());
            double longitude = Double.parseDouble(document.getElementsByTagName("lng").item(0).getTextContent());
            return new Coordinates(latitude, longitude);
        } catch (Exception ex) {
            logger.error("Error during parsing of coordinates: " + ex.getMessage(), ex);
            throw ex;
        }
    }
}
