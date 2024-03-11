package com.teachmeskills.finalAssignment.util;

import com.teachmeskills.finalAssignment.consts.files.FileConstants;
import com.teachmeskills.finalAssignment.service.FileService;

/**
 * The Logger class provides methods for logging messages.
 * It includes methods for logging informational, warning, and error messages.
 */
public class Logger {

    /**
     * Logs an informational message.
     *
     * @param postfix The message to log.
     */
    public static void info(String postfix) {
        String prefix = DateUtil.getCurrentDate() + " [INFO] ";
        String message = prefix + postfix;
        FileService.createFileIfNotExist(FileConstants.LOG_DIR, FileConstants.LOG_FILE_NAME);
        System.out.println(message);
        FileService.appendFile(FileConstants.LOG_FILE_PATH, message);
    }

    /**
     * Logs a warning message.
     *
     * @param postfix The message to log.
     */
    public static void warn(String postfix) {
        String prefix = DateUtil.getCurrentDate() + " [WARN] ";
        String message = prefix + postfix;
        FileService.createFileIfNotExist(FileConstants.LOG_DIR, FileConstants.LOG_FILE_NAME);
        System.out.println(message);
        FileService.appendFile(FileConstants.LOG_FILE_PATH, message);
    }

    /**
     * Logs an error message.
     *
     * @param postfix The message to log.
     */
    public static void error(String postfix) {
        String prefix = DateUtil.getCurrentDate() + " [ERROR] ";
        String message = prefix + postfix;
        info(postfix);
        FileService.createFileIfNotExist(FileConstants.LOG_DIR, FileConstants.ERROR_LOG_FILE_NAME);
        System.out.println(message);
        FileService.appendFile(FileConstants.ERROR_LOG_FILE_PATH, message);
    }
}
