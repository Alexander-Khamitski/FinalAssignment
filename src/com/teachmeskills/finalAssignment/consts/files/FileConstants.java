package com.teachmeskills.finalAssignment.consts.files;

import java.util.Arrays;
import java.util.List;

public interface FileConstants {

    String TXT_FORMAT = ".txt";
    String YEAR = "2023";
    String ORDER = "order";
    String INVOICE = "invoice";
    String BILL = "bill";
    List<String> VALID_FILE_TYPES = Arrays.asList(ORDER, INVOICE, BILL);
}
