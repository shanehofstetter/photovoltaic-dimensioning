package ch.shanehofstetter.pvdimension.simulation;

/**
 * Throw this exception if some needed data is missing or null
 *
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.simulation
 */
public class MissingDataException extends Exception {

    public MissingDataException(String message) {
        super(message);
    }
}
