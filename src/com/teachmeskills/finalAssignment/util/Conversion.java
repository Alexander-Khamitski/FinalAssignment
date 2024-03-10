package com.teachmeskills.finalAssignment.util;

import static com.teachmeskills.finalAssignment.consts.conversion.ConversionConstants.*;
import static com.teachmeskills.finalAssignment.consts.messages.LogMessages.AMOUNT_IN_CURRENCY;

public class Conversion {

    public static double convertAmountIntoUsd(String currencyFrom, double amount) {
        if (currencyFrom.equals(GBR)) {
            return Conversion.convert(amount, GBR, USD);
        } else if (currencyFrom.equals(EUR)) {
            return Conversion.convert(amount, EUR, USD);
        } else {
            return amount;
        }
    }

    private static double convert(double amount, String currencyFrom, String currencyTo) {
        if (currencyFrom.equals(EUR) && currencyTo.equals(USD)) {
            return getConvertedAmount(amount, currencyFrom, currencyTo, EUR_TO_USD);
        } else if (currencyFrom.equals(GBR) && currencyTo.equals(USD)) {
            return getConvertedAmount(amount, currencyFrom, currencyTo, GBR_TO_USD);
        } else {
            System.out.println(String.format(WRONG_DATA_FOR_CONVERSION, amount, currencyFrom, currencyTo));
            return amount;
        }
    }

    private static double getConvertedAmount(double amount, String currencyFrom, String currencyTo, double rate) {
        System.out.println(String. format(CONVERSION_MESSAGE, amount, currencyFrom, currencyTo, rate));
        System.out.println(String.format(AMOUNT_IN_CURRENCY, currencyFrom, amount));
        double converted = amount * rate;
        System.out.println(String.format(AMOUNT_IN_CURRENCY, currencyTo, converted));
        return converted;
    }
}
