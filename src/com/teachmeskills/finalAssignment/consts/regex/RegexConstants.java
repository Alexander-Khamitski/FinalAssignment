package com.teachmeskills.finalAssignment.consts.regex;

public interface RegexConstants {

    String YEAR_REGEX = "(197[0-9]{1}|198[0-9]{1}|199[0-9]{1}|200[0-9]{1}|201[0-9]{1}|202[0-9]{1})";
    String FILE_TYPES_REGEX = "(bill|invoice|order)";
    String ORDER_TOTAL_AMOUNT_LINE_REGEX = "(order(\\s)?total(\\s)?)([0-9]{1,3}?)(\\,?)([0-9]{1,3})(\\.[0-9]{1,2})";
    String ORDER_TOTAL_AMOUNT_REGEX = "([0-9]{1,3}?)(\\,?)([0-9]{1,3})(\\.?)([0-9]{1,2})";
    String BILL_TOTAL_AMOUNT_LINE_REGEX = "(bill(\\s)?total(\\s)?)(.*)([0-9]{1,3}?)(\\,?)([0-9]{1,3})(\\,[0-9]{1,2})";
    String BILL_TOTAL_AMOUNT_REGEX = "([0-9]{1,6})(\\,[0-9]{1,2})";
    String INVOICE_TOTAL_AMOUNT_LINE_REGEX = "(total(\\s)?)(amount(\\s)?)?(.*)([0-9]{1,6})(\\,?)(\\.?)([0-9]{1,2}?)";
    String INVOICE_TOTAL_AMOUNT_REGEX = "([0-9]{1,6})(\\,?)(\\.?)([0-9]{1,2})?";

}
