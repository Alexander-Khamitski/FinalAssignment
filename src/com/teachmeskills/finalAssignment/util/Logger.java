package com.teachmeskills.finalAssignment.util;

import com.teachmeskills.finalAssignment.consts.files.FileConstants;
import com.teachmeskills.finalAssignment.service.FileService;

public class Logger {

    public static void info(String postfix) {
        String prefix = DateUtil.getCurrentDate() + " [INFO] ";
        String message = prefix + postfix;
        FileService.createFileIfNotExist(FileConstants.LOG_DIR, FileConstants.LOG_FILE_NAME);
        System.out.println(message);
        FileService.appendFile(FileConstants.LOG_FILE_PATH, message);
    }

    public static void warn(String postfix) {
        String prefix = DateUtil.getCurrentDate() + " [WARN] ";
        String message = prefix + postfix;
        FileService.createFileIfNotExist(FileConstants.LOG_DIR, FileConstants.LOG_FILE_NAME);
        System.out.println(message);
        FileService.appendFile(FileConstants.LOG_FILE_PATH, message);
    }

    public static void error(String postfix) {
        String prefix = DateUtil.getCurrentDate() + " [ERROR] ";
        String message = prefix + postfix;
        info(postfix);
        FileService.createFileIfNotExist(FileConstants.LOG_DIR, FileConstants.ERROR_LOG_FILE_NAME);
        System.out.println(message);
        FileService.appendFile(FileConstants.ERROR_LOG_FILE_PATH, message);
    }
}
