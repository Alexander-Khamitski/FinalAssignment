package com.teachmeskills.finalAssignment.service;

import com.teachmeskills.finalAssignment.exception.InvalidLineException;
import com.teachmeskills.finalAssignment.util.Conversion;
import com.teachmeskills.finalAssignment.util.Regex;
import com.teachmeskills.finalAssignment.util.Statistics;
import com.teachmeskills.finalAssignment.validation.StringValidator;

import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.INVALID_FILE_TYPE_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.files.FileConstants.*;
import static com.teachmeskills.finalAssignment.consts.statistics.StatisticsMessages.*;

public class StatisticService {

    public static void getFullStatistics() {
        System.out.println(DIVIDING_LINE);
        System.out.println(String.format(ORDERS_STATISTICS_MESSAGE, Statistics.getOrderTotalSum()));
        System.out.println(String.format(BILLS_STATISTICS_MESSAGE, Statistics.getBillTotalSum()));
        System.out.println(String.format(INVOICES_STATISTICS_MESSAGE, Statistics.getInvoiceTotalSum()));
        System.out.println(String.format(TOTAL_AMOUNT_STATISTICS_MESSAGE, Statistics.getTotalSum()));
    }

    public static void appendStatisticIfLineContainsTotalAmount(String fileType, String line) {
        try {
            if (StringValidator.isLineContainsTotalAmount(fileType, line)) {
                double totalAmount = getTotalAmount(fileType, line);
                setStatisticsValue(fileType, totalAmount);
            }
        } catch (InvalidLineException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void setStatisticsValue(String fileType, Double value) {
        switch (fileType) {
            case ORDER -> {
                Statistics.setOrderTotalSum(Statistics.getOrderTotalSum() + value);
                System.out.println(String.format(ORDERS_STATISTICS_MESSAGE, Statistics.getOrderTotalSum()));
            }
            case BILL -> {
                Statistics.setBillTotalSum(Statistics.getBillTotalSum() + value);
                System.out.println(String.format(BILLS_STATISTICS_MESSAGE, Statistics.getBillTotalSum()));
            }
            case INVOICE -> {
                Statistics.setInvoiceTotalSum(Statistics.getInvoiceTotalSum() + value);
                System.out.println(String.format(INVOICES_STATISTICS_MESSAGE, Statistics.getInvoiceTotalSum()));
            }
            default -> throw new IllegalStateException(String.format(INVALID_FILE_TYPE_MESSAGE, fileType));
        }
        Statistics.setTotalSum(Statistics.getTotalSum() + value);
        System.out.println(String.format(TOTAL_AMOUNT_STATISTICS_MESSAGE, Statistics.getInvoiceTotalSum()));
    }

    private static double getTotalAmount(String fileType, String fileLine) {
        String regex = Regex.getTotalAmountRegexByFileType(fileType);
        double totalAmount = FileService.getLineTotalAmount(regex, fileType, fileLine);
        String lineCurrency = FileService.getLineCurrency(fileLine);
        double totalAmountUsd = Conversion.convertAmountIntoUsd(lineCurrency, totalAmount);
        System.out.println(String.format(LINE_AMOUNT, totalAmountUsd));
        return totalAmountUsd;
    }
}
