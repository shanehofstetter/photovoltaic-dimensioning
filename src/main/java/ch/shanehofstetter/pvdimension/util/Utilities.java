package ch.shanehofstetter.pvdimension.util;

/**
 * Utility class with helpful functions which are used by different packages
 * <p>
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension
 */
public class Utilities {

    /**
     * @param value      Value to limit
     * @param lowerLimit Lower Limit, e.g. 0
     * @param upperLimit Upper Limit, e.g. 1
     * @return Limited Value [lower limit ... upper limit]
     */
    public static double limitValue(double value, double lowerLimit, double upperLimit) {
        if (value < lowerLimit)
            value = lowerLimit;
        else if (value > upperLimit)
            value = upperLimit;
        return value;
    }

    /**
     * round a double value to a number of decimal places
     *
     * @param value    the value to round
     * @param decimals number of decimal places
     * @return rounded value
     */
    public static double round(double value, int decimals) {
        double factor = Math.pow(10.0, (double) decimals);
        return Math.round(value * factor) / factor;
    }

    /**
     * Counts how many times the substring appears in the larger String.
     * A null or empty ("") String input returns 0.
     * Source: Apache Commons Lang
     *
     * @param str String to check
     * @param sub substring to count
     * @return number of occurrences
     */
    public static int countMatches(String str, String sub) {
        if (str == null || sub == null) {
            return 0;
        }
        if (str.equals("") || sub.equals("")) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }
}
