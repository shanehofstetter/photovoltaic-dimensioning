package ch.shanehofstetter.pvdimension.simulation;

/**
 * Throw this exception if some needed data is missing or null
 *
 *
 */
public class MissingDataException extends Exception {

    public MissingDataException(String message) {
        super(message);
    }
}
