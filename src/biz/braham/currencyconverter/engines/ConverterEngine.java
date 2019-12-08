package biz.braham.currencyconverter.engines;

import biz.braham.currencyconverter.exceptions.UnsupportedCurrencyException;

import java.math.BigDecimal;

/**
 * Standardized interface for creating currency converter objects
 */
public interface ConverterEngine {
    /**
     * Converts the desired amount of money from one currency to another
     *
     * @param from   ISO currency code
     * @param to     ISO currency code
     * @param amount monetary value
     * @return converted amount
     * @throws UnsupportedCurrencyException if one the iso codes can't be resolved by the currency data provider, this exception get's thrown
     */
    public BigDecimal convert(String from, String to, BigDecimal amount) throws UnsupportedCurrencyException;
}
