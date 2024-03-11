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
    String INVALID_FILES_FOLDER = "invalidFiles";
    String INVALID_FILES_FOLDER_PATH = "invalidFiles/";
    String LOG_DIR = "log";
    String STATISTICS_DIR = "statistics";
    String LOG_FILE_NAME = "execution_log.txt";
    String ERROR_LOG_FILE_NAME = "error_log.txt";
    String STATISTICS_FILE_NAME = "final_statistics.txt";
    String RESOURCES_PATH = "resources/";
    String LOG_FILE_PATH = "log/execution_log.txt";
    String ERROR_LOG_FILE_PATH = "log/error_log.txt";
    String STATISTICS_FILE_PATH = "statistics/final_statistics.txt";
    String STATISTICS_DIR_MESSAGE = "Statistics dir: \"%s\"";
}
