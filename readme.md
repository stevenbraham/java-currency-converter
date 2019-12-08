I made this project for fun to experiment a bit with Java. 

You need at least Java 11 for the `HttpClient` class.

To run the app build the CurrencyConverter package use Maven to build and install all dependencies.

Afterwards run it like this:

`convert [currency1] [currency2] [amount] [engine (optional)]`

You can choose from two engines:

* a local data engine with hardcoded values
* an api engine powered by [ExchangesRates.io](https://exchangeratesapi.io/) as a data API