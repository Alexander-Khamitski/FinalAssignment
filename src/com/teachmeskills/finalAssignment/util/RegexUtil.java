package com.teachmeskills.finalAssignment.util;

import static com.teachmeskills.finalAssignment.consts.files.FileConstants.*;
import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.INVALID_FILE_TYPE_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.regex.RegexConstants.*;

/**
 * The Regex class provides utility methods for retrieving regular expressions based on file types.
 */
public class RegexUtil {

    /**
     * Retrieves the regular expression for extracting the total amount from a file based on its type.
     *
     * @param fileType The type of the file.
     * @return The regular expression for extracting the total amount.
     * @throws IllegalStateException If the file type is invalid.
     */
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

    /**
     * Retrieves the regular expression for matching a line containing the total amount in a file based on its type.
     *
     * @param fileType The type of the file.
     * @return The regular expression for matching the line containing the total amount.
     * @throws IllegalStateException If the file type is invalid.
     */
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
