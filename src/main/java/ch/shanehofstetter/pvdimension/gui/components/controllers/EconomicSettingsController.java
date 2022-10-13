package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.economy.Economy;
import ch.shanehofstetter.pvdimension.gui.components.views.EconomicSettingsView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.controllers
 */
public class EconomicSettingsController extends EconomicSettingsView {

    private List<Currency> currencies = new ArrayList<>();
    private List<EconomicsController.EconomicsChangeListener> listeners = new ArrayList<>();

    public EconomicSettingsController() {
        ObservableList<String> currencyItems = FXCollections.observableArrayList();

        for (Currency currency : Economy.getAvailableCurrencies()) {
            currencies.add(currency);
            currencyItems.add(currency.getCurrencyCode() + " - " + currency.getDisplayName());
        }
        //noinspection unchecked
        currencyComboBox.setItems(currencyItems);

        selectCurrencyCode(Economy.getCurrencyCode());

        currencyComboBox.setOnAction(ev -> currencyChanged());

        Economy.addListener(c -> selectCurrencyCode(c.getCurrencyCode()));

    }

    public void addListener(EconomicsController.EconomicsChangeListener listener) {
        this.listeners.add(listener);
    }

    private void selectCurrencyCode(String code) {
        for (int i = 0; i < currencies.size(); i++) {
            if (Objects.equals(currencies.get(i).getCurrencyCode(), Economy.getCurrency().getCurrencyCode())) {
                currencyComboBox.getSelectionModel().select(i);
                break;
            }
        }
    }

    private void currencyChanged() {
        System.out.println("Currency changed");
        Currency newCurrency = currencies.get(currencyComboBox.getSelectionModel().getSelectedIndex());
        Economy.setCurrency(newCurrency);
        settingsChanged();
    }

    private void settingsChanged() {
        listeners.forEach(EconomicsController.EconomicsChangeListener::changed);
    }

    public interface EconomicSettingsChangeListener {
        void changed();
    }
}
