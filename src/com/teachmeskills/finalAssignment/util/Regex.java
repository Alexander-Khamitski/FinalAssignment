package com.teachmeskills.finalAssignment.util;

import static com.teachmeskills.finalAssignment.consts.files.FileConstants.*;
import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.INVALID_FILE_TYPE_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.regex.RegexConstants.*;

public class Regex {

    public static String getTotalAmountRegexByFileType(String fileType) {
        return switch (fileType) {
            case ORDER -> ORDER_TOTAL_AMOUNT_REGEX;
            case BILL -> BILL_TOTAL_AMOUNT_REGEX;
            case INVOICE -> INVOICE_TOTAL_AMOUNT_REGEX;
            default -> {
                String errorMessage = String.format(INVALID_FILE_TYPE_MESSAGE, fileType);
                Logger.error(errorMessage);
                throw new IllegalStateException(errorMessage);
            }
        };
    }

    public static String getLineTotalAmountRegexByFiletype(String fileType) {
        return switch (fileType) {
            case ORDER -> ORDER_TOTAL_AMOUNT_LINE_REGEX;
            case BILL -> BILL_TOTAL_AMOUNT_LINE_REGEX;
            case INVOICE -> INVOICE_TOTAL_AMOUNT_LINE_REGEX;
            default -> {
                String errorMessage = String.format(INVALID_FILE_TYPE_MESSAGE, fileType);
                Logger.error(errorMessage);
                throw new IllegalStateException(errorMessage);
            }
        };
    }
}
