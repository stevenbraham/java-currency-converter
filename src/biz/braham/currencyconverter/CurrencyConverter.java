package biz.braham.currencyconverter;

import biz.braham.currencyconverter.engines.ConverterEngine;
import biz.braham.currencyconverter.engines.ExchangeRatesApi;
import biz.braham.currencyconverter.engines.LocalDataConverterEngine;
import biz.braham.currencyconverter.exceptions.UnsupportedCurrencyException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {

    public static void main(String[] args) {
        if (args.length < 3) {
            //not enough arguments provided
            System.out.println("Usage: [from] [to] [amount]");
            System.exit(1);
        }

        //check which converter engine to use
        ConverterEngine converter = new ExchangeRatesApi();
        if (args.length >= 4) {
            switch (args[3]) {
                case "api":
                    converter = new ExchangeRatesApi();
                    break;
                case "local":
                    converter = new LocalDataConverterEngine();
                    break;
                default:
                    System.out.println(args[3] + " is not a supported converter engine. Supported engines are local, api");
                    System.exit(2);
                    break;
            }
        }

        BigDecimal amount = new BigDecimal(args[2]);

        try {
            BigDecimal convertedValue = converter.convert(args[0].toUpperCase(), args[1].toUpperCase(), amount);
            System.out.println(convertedValue.setScale(2, RoundingMode.HALF_UP));
        } catch (UnsupportedCurrencyException uc) {
            System.out.println("Invalid currency code");
            System.exit(1);
        }
    }
}
