package ch.shanehofstetter.pvdimension.economy;


import ch.shanehofstetter.pvdimension.io.FileReader;
import ch.shanehofstetter.pvdimension.io.FileWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.prefs.Preferences;

/**
 * Economy holds the manages the currency configuration<br>
 * Loads, saves the used currency in the application<br>
 * <p>
 * The currency configuration gets permanently stored on the system,<br>
 * it is not stored in project-specific objects<br>
 *
 */
public class Economy {

    public static final String DEFAULT_CURRENCY_CODE = "CHF";
    static final Logger logger = LogManager.getLogger();
    private static Currency currency = Currency.getInstance(DEFAULT_CURRENCY_CODE);
    private static String currencyPreferenceID = "CURRENCY";
    private static List<String> currencyCodes = Arrays.asList(
            "CHF",
            "EUR",
            "USD",
            "GBP",
            "INR",
            "AUD",
            "CAD",
            "SGD",
            "MYR",
            "JPY",
            "CNY"
    );
    private static List<CurrencyChangeListener> listeners = new ArrayList<>();


    /**
     * @return a set of most used currency codes
     */
    public static Set<Currency> getAvailableCurrencies() {
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        Currency[] currenciesArr = currencies.toArray(new Currency[currencies.size()]);
        for (int i = currenciesArr.length - 1; i > 0; i--) {
            Currency currency = currenciesArr[i];
            if (!currencyCodes.contains(currency.getCurrencyCode())) {
                currencies.remove(currency);
            }
        }
        return currencies;
    }

    /**
     * the configured currency
     *
     * @return currency
     */
    public static Currency getCurrency() {
        return currency;
    }

    /**
     * set another currency
     * gets immediately saved to the system
     * triggers event
     *
     * @param newCurrency new currency to use
     */
    public static void setCurrency(Currency newCurrency) {
        if (currency == null) {
            logger.warn("currency set to null, loading default");
            currency = Currency.getInstance(DEFAULT_CURRENCY_CODE);
        } else {
            logger.debug("set currency to: " + newCurrency.getDisplayName());
            currency = newCurrency;
        }
        currencyChanged();
    }

    /**
     * get the currency code
     * ex: CHF, USD
     *
     * @return currency code
     */
    public static String getCurrencyCode() {
        if (currency == null) {
            logger.warn("currency is null, set to default");
            currency = Currency.getInstance(DEFAULT_CURRENCY_CODE);
        }
        return currency.getCurrencyCode();
    }

    /**
     * set the currency with a given currency code
     * examples: CHF, USD ,..
     *
     * @param currencyCode currency code
     */
    public static void setCurrencyByCode(String currencyCode) {
        if (currencyCode == null) {
            logger.warn("currency set to null, loading default");
            currencyCode = DEFAULT_CURRENCY_CODE;
        }
        setCurrency(Currency.getInstance(currencyCode));
    }

    /**
     * saves the currency code to local config file
     * WARNING: does NOT work with jars
     */
    @Deprecated
    private static void saveCurrencyToLocalConfig() {
        FileWriter writer = new FileWriter();
        try {
            writer.writeStringToPath(currency.getCurrencyCode(), Economy.class.getClassLoader().getResource("ch/shanehofstetter/pvdimension/economy/currency.pref"));
        } catch (Exception e) {
            logger.error("failed to save currency code to local file: " + e.getMessage(), e);
        }
    }

    /**
     * loads currency from local config file
     */
    @Deprecated
    private static void loadCurrencyFromLocalConfig() {
        String currencyPref = null;
        try {
            currencyPref = FileReader.readStreamIntoString(
                    Economy.class.getClassLoader().getResourceAsStream("ch/shanehofstetter/pvdimension/economy/currency.pref"));
        } catch (Exception e) {
            logger.error("failed to load currency setting from local file: " + e.getMessage(), e);
        }
        if (currencyPref == null) {
            logger.warn("loading default currency");
            currency = Currency.getInstance(DEFAULT_CURRENCY_CODE);
        } else {
            try {
                currency = Currency.getInstance(currencyPref);
                logger.debug("currency loaded: " + currencyPref);
            } catch (Exception e) {
                logger.error("failed to get currency instance with code: " + e.getMessage() + ", loading default currency");
                currency = Currency.getInstance(DEFAULT_CURRENCY_CODE);
            }
        }
        // a currency gets loaded either way
    }

    /**
     * add a listener which gets notified everytime the currency changes
     *
     * @param listener listener
     */
    public static void addListener(CurrencyChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * notify all listeners
     */
    private static void currencyChanged() {
        for (CurrencyChangeListener listener : listeners) listener.changed(currency);
    }

    /**
     * loads currency from preferences and sets it
     */
    @Deprecated
    private static void loadCurrencyFromPreference() {
        Preferences prefs = Preferences.userNodeForPackage(Economy.class);
        currency = Currency.getInstance(prefs.get(currencyPreferenceID, DEFAULT_CURRENCY_CODE));
    }

    /**
     * saves the currency code to the preferences
     */
    @Deprecated
    private static void saveCurrencyPreference() {
        Preferences prefs = Preferences.userNodeForPackage(Economy.class);
        prefs.put(currencyPreferenceID, currency.getCurrencyCode());
    }

    /**
     * Listener for Currency changes
     */
    public interface CurrencyChangeListener {

        void changed(Currency currency);

    }
}
