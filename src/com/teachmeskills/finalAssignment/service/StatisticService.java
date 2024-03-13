package com.teachmeskills.finalAssignment.service;

import com.teachmeskills.finalAssignment.util.Logger;
import com.teachmeskills.finalAssignment.util.RegexUtil;
import com.teachmeskills.finalAssignment.util.Statistics;

import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.INVALID_FILE_TYPE_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.files.FileConstants.*;
import static com.teachmeskills.finalAssignment.consts.statistics.StatisticsMessages.*;

/**
 * The StatisticService class provides methods for generating and appending statistics.
 * It includes methods to get full statistics, append statistics based on file content,
 * and set statistics values accordingly.
 */
public class StatisticService {

    /**
     * Generates full statistics and logs them.
     * Creates a statistics file if it does not exist and appends statistics to it.
     * Also logs statistics information to the console.
     */
    public static void getFullStatistics() {
        FileService.createFileIfNotExist(STATISTICS_DIR, STATISTICS_FILE_NAME);
        FileService.appendFile(STATISTICS_FILE_PATH, String.format(ORDERS_STATISTICS_MESSAGE, Statistics.getOrderTotalSum()));
        FileService.appendFile(STATISTICS_FILE_PATH, String.format(BILLS_STATISTICS_MESSAGE, Statistics.getBillTotalSum()));
        FileService.appendFile(STATISTICS_FILE_PATH, String.format(INVOICES_STATISTICS_MESSAGE, Statistics.getInvoiceTotalSum()));
        FileService.appendFile(STATISTICS_FILE_PATH, String.format(TOTAL_AMOUNT_STATISTICS_MESSAGE, Statistics.getTotalSum()));
        Logger.info(DIVIDING_LINE);
        Logger.info(String.format(ORDERS_STATISTICS_MESSAGE, Statistics.getOrderTotalSum()));
        Logger.info(String.format(BILLS_STATISTICS_MESSAGE, Statistics.getBillTotalSum()));
        Logger.info(String.format(INVOICES_STATISTICS_MESSAGE, Statistics.getInvoiceTotalSum()));
        Logger.info(String.format(TOTAL_AMOUNT_STATISTICS_MESSAGE, Statistics.getTotalSum()));
        Logger.info(String.format(STATISTICS_DIR_MESSAGE, STATISTICS_FILE_PATH));
    }

    /**
     * Sets the statistics value based on the file type and total amount.
     * Updates the relevant statistics values and logs the updated values.
     *
     * @param fileType The type of the file.
     * @param value    The total amount extracted from the line.
     */
    public static void setStatisticsValue(String fileType, Double value) {
        switch (fileType) {
            case ORDER -> {
                Statistics.setOrderTotalSum(Statistics.getOrderTotalSum() + value);
                Logger.info(String.format(ORDERS_STATISTICS_MESSAGE, Statistics.getOrderTotalSum()));
            }
            case BILL -> {
                Statistics.setBillTotalSum(Statistics.getBillTotalSum() + value);
                Logger.info(String.format(BILLS_STATISTICS_MESSAGE, Statistics.getBillTotalSum()));
            }
            case INVOICE -> {
                Statistics.setInvoiceTotalSum(Statistics.getInvoiceTotalSum() + value);
                Logger.info(String.format(INVOICES_STATISTICS_MESSAGE, Statistics.getInvoiceTotalSum()));
            }
            default -> {
                String errorMessage = String.format(INVALID_FILE_TYPE_MESSAGE, fileType);
                Logger.error(errorMessage);
                throw new IllegalStateException(errorMessage);
            }
        }
        Statistics.setTotalSum(Statistics.getTotalSum() + value);
        Logger.info(String.format(TOTAL_AMOUNT_STATISTICS_MESSAGE, Statistics.getTotalSum()));
    }

    /**
     * Extracts the total amount from a line of text based on the file type and line content.
     * Converts the total amount to USD and logs the converted amount.
     *
     * @param fileType The type of the file containing the line.
     * @param fileLine The line of text containing the total amount.
     * @return The total amount converted to USD.
     */
    public static double getTotalAmount(String fileType, String fileLine) {
        String regex = RegexUtil.getTotalAmountRegexByFileType(fileType);
        double totalAmount = FileService.getLineTotalAmount(regex, fileType, fileLine);
        String lineCurrency = FileService.getLineCurrency(fileLine);
        double totalAmountUsd = ConversionService.convertAmountIntoUsd(lineCurrency, totalAmount);
        Logger.info(String.format(LINE_AMOUNT, totalAmountUsd));
        return totalAmountUsd;
    }
}
