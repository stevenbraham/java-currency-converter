package biz.braham.currencyconverter.engines;

import biz.braham.currencyconverter.exceptions.UnsupportedCurrencyException;

import java.math.BigDecimal;

/**
 * Very basic currency engine that uses a hardcoded internal value list
 */
public class LocalDataConverterEngine implements ConverterEngine {

    @Override
    public BigDecimal convert(String from, String to, BigDecimal amount) throws UnsupportedCurrencyException {
        //convert first to euro
        amount = amount.multiply(new BigDecimal(getCurrencyValue(from)));
        BigDecimal currencyValue = new BigDecimal(1 / getCurrencyValue(to));

        return currencyValue.multiply(amount);
    }

    /**
     * Get the value of a currency in EUR
     *
     * @param currencyCode ISO currency code
     * @return value of 1 unit in EURO
     * @throws UnsupportedCurrencyException
     */
    private double getCurrencyValue(String currencyCode) throws UnsupportedCurrencyException {
        switch (currencyCode) {
            case "EUR":
                return 1;
            case "USD":
                return 0.8;
            case "ZAR":
                return 0.0285;
            case "GHP":
                return 1.5;
            default:
                throw new UnsupportedCurrencyException();
        }
    }
}
