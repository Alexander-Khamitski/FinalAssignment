package com.teachmeskills.finalAssignment.service;

import com.teachmeskills.finalAssignment.util.Logger;

import static com.teachmeskills.finalAssignment.consts.conversion.ConversionConstants.*;
import static com.teachmeskills.finalAssignment.consts.messages.LogMessages.AMOUNT_IN_CURRENCY;

/**
 * The Conversion class provides methods for currency conversion.
 * It includes methods to convert an amount from one currency to another.
 */
public class ConversionService {

    /**
     * Converts an amount from the specified currency to USD.
     *
     * @param currencyFrom The currency from which to convert.
     * @param amount       The amount to convert.
     * @return The converted amount in USD, or the original amount if no conversion is needed.
     */
    public static double convertAmountIntoUsd(String currencyFrom, double amount) {
        if (currencyFrom.equals(GBR)) {
            return ConversionService.convert(amount, GBR, USD);
        } else if (currencyFrom.equals(EUR)) {
            return ConversionService.convert(amount, EUR, USD);
        } else {
            return amount;
        }
    }

    /**
     * Converts an amount from one currency to another by calling
     * getConvertedAmount(double amount, String currencyFrom, String currencyTo, double rate) method.
     *
     * @param amount       The amount to convert.
     * @param currencyFrom The currency from which to convert.
     * @param currencyTo   The currency to which to convert.
     * @return The converted amount.
     */
    private static double convert(double amount, String currencyFrom, String currencyTo) {
        if (currencyFrom.equals(EUR) && currencyTo.equals(USD)) {
            return getConvertedAmount(amount, currencyFrom, currencyTo, EUR_TO_USD);
        } else if (currencyFrom.equals(GBR) && currencyTo.equals(USD)) {
            return getConvertedAmount(amount, currencyFrom, currencyTo, GBR_TO_USD);
        } else {
            Logger.error(String.format(WRONG_DATA_FOR_CONVERSION, amount, currencyFrom, currencyTo));
            return amount;
        }
    }

    /**
     * Calculates the converted amount based on the exchange rate.
     *
     * @param amount       The amount to convert.
     * @param currencyFrom The currency from which to convert.
     * @param currencyTo   The currency to which to convert.
     * @param rate         The exchange rate between the two currencies.
     * @return The converted amount.
     */
    private static double getConvertedAmount(double amount, String currencyFrom, String currencyTo, double rate) {
        Logger.info(String. format(CONVERSION_MESSAGE, amount, currencyFrom, currencyTo, rate));
        Logger.info(String.format(AMOUNT_IN_CURRENCY, currencyFrom, amount));
        double converted = amount * rate;
        Logger.info(String.format(AMOUNT_IN_CURRENCY, currencyTo, converted));
        return converted;
    }
}
