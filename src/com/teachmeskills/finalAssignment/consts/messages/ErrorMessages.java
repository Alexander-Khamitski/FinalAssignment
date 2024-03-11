package com.teachmeskills.finalAssignment.consts.messages;

public interface ErrorMessages {

    //Error messages
    String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials.";
    String EMPTY_CREDENTIALS_MESSAGE = "Empty login or password.";
    String REMAINING_ATTEMPTS_MESSAGE = "Try again. The number of remaining attempts: \"%d\"";
    String ACCESS_DENIED_MESSAGE = "You have used all attempts. Access denied.";
    String EXPIRED_SESSION_MESSAGE = "Session was expired";

    //Error file messages
    String INVALID_FILE_EXTENSION_MESSAGE = "Invalid file: \"%s\". Invalid file extension: \"%s\"";
    String INVALID_FILE_YEAR_MESSAGE = "Invalid file: \"%s\". Invalid file report period: \"%s\".";
    String INVALID_FILE_PERIOD_MESSAGE = "Invalid file: \"%s\". Invalid file report period.";
    String INVALID_FILE_TYPE_MESSAGE = "Invalid file: \"%s\". Invalid file type.";
    String INVALID_LINE_MESSAGE = "Invalid line: \"%s\". Line does not contain total amount.";

    //Exception messages
    String FILE_NOT_FOUND_EXCEPTION_MESSAGE = "File with path \"%s\" not found";
    String IO_EXCEPTION_MESSAGE = "Unexpected IOException: \"%s\"";
    String EXCEPTION_MESSAGE = "Unexpected Exception: \"%s\"";

    //Path messages
    String FOLDER_DOES_NOT_EXISTS_MESSAGE = "Folder with \"%s\" path does NOT exist.";
}
