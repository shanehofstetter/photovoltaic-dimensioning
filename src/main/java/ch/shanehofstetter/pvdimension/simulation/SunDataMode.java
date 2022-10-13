package ch.shanehofstetter.pvdimension.simulation;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 *         ch.shanehofstetter.pvdimension.simulation
 */
public enum SunDataMode {
    /**
     * local mode: user specifies the sun-data himself
     */
    LOCAL {
        @Override
        public String toString() {
            return "LOCAL";
        }
    },
    /**
     * web mode: we need to fetch the sun-data from a webservice
     */
    WEBSERVICE {
        @Override
        public String toString() {
            return "WEBSERVICE";
        }
    }
}
