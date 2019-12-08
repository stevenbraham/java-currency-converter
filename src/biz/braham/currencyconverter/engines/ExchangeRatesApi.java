package biz.braham.currencyconverter.engines;

import biz.braham.currencyconverter.exceptions.APIException;
import biz.braham.currencyconverter.exceptions.UnsupportedCurrencyException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ExchangeRatesApi implements ConverterEngine {
    @Override
    public BigDecimal convert(String from, String to, BigDecimal amount) throws UnsupportedCurrencyException {
        try {
            //get base unit
            JSONObject apiResponse = doAPICall(from, to);
            if (apiResponse.has("rates")) {
                if (apiResponse.getJSONObject("rates").has(to)) {
                    BigDecimal baseValue = apiResponse.getJSONObject("rates").getBigDecimal(to);
                    return baseValue.multiply(amount);
                } else {
                    throw new UnsupportedCurrencyException();
                }
            } else {
                throw new UnsupportedCurrencyException();
            }
        } catch (Exception e) {
            throw new UnsupportedCurrencyException();
        }
    }

    private JSONObject doAPICall(String from, String to) throws APIException {
        JSONObject jsonResponse;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(generateApiURl(from, to))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonResponse = new JSONObject(response.body());
        } catch (Exception e) {
            throw new APIException();
        }
        return jsonResponse;
    }

    /**
     * Generates the api url needed to get the currency information
     *
     * @param from
     * @param to
     * @return
     */
    private URI generateApiURl(String from, String to) throws MalformedURLException, URISyntaxException, UnsupportedEncodingException {
        return new URI("https://api.exchangeratesapi.io/latest?symbols="
                .concat(URLEncoder.encode(to, StandardCharsets.UTF_8.name()))
                .concat("&base=")
                .concat(URLEncoder.encode(from, StandardCharsets.UTF_8.name()))
        );
    }
}
