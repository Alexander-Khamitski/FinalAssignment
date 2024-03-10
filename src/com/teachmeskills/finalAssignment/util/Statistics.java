package com.teachmeskills.finalAssignment.util;

public final class Statistics {
    private static double ORDER_TOTAL_SUM = 0.0;
    private static double INVOICE_TOTAL_SUM = 0.0;
    private static double BILL_TOTAL_SUM = 0.0;
    private static double TOTAL_SUM = 0.0;

    public static double getOrderTotalSum() {
        return ORDER_TOTAL_SUM;
    }

    public static void setOrderTotalSum(double orderTotalSum) {
        ORDER_TOTAL_SUM = orderTotalSum;
    }

    public static double getInvoiceTotalSum() {
        return INVOICE_TOTAL_SUM;
    }

    public static void setInvoiceTotalSum(double invoiceTotalSum) {
        INVOICE_TOTAL_SUM = invoiceTotalSum;
    }

    public static double getBillTotalSum() {
        return BILL_TOTAL_SUM;
    }

    public static void setBillTotalSum(double billTotalSum) {
        BILL_TOTAL_SUM = billTotalSum;
    }

    public static double getTotalSum() {
        return TOTAL_SUM;
    }

    public static void setTotalSum(double totalSum) {
        TOTAL_SUM = totalSum;
    }
}


