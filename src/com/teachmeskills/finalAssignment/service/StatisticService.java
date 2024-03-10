package com.teachmeskills.finalAssignment.service;

import com.teachmeskills.finalAssignment.exception.InvalidLineException;
import com.teachmeskills.finalAssignment.util.Conversion;
import com.teachmeskills.finalAssignment.util.Logger;
import com.teachmeskills.finalAssignment.util.Regex;
import com.teachmeskills.finalAssignment.util.Statistics;
import com.teachmeskills.finalAssignment.validation.StringValidator;

import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.INVALID_FILE_TYPE_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.files.FileConstants.*;
import static com.teachmeskills.finalAssignment.consts.messages.LogMessages.VALID_LINE_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.statistics.StatisticsMessages.*;

public class StatisticService {

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

    public static void appendStatisticIfLineContainsTotalAmount(String fileType, String line) {
        try {
            if (StringValidator.isLineContainsTotalAmount(fileType, line)) {
                Logger.info(String.format(VALID_LINE_MESSAGE, line));
                double totalAmount = getTotalAmount(fileType, line);
                setStatisticsValue(fileType, totalAmount);
            }
        } catch (InvalidLineException e) {
            Logger.warn(e.getMessage());
        }
    }

    private static void setStatisticsValue(String fileType, Double value) {
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

    private static double getTotalAmount(String fileType, String fileLine) {
        String regex = Regex.getTotalAmountRegexByFileType(fileType);
        double totalAmount = FileService.getLineTotalAmount(regex, fileType, fileLine);
        String lineCurrency = FileService.getLineCurrency(fileLine);
        double totalAmountUsd = Conversion.convertAmountIntoUsd(lineCurrency, totalAmount);
        Logger.info(String.format(LINE_AMOUNT, totalAmountUsd));
        return totalAmountUsd;
    }
}
