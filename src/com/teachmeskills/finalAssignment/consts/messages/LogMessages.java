package com.teachmeskills.finalAssignment.consts.messages;

public interface LogMessages {

    int RETRY_ATTEMPTS = 3;
    String ENTER_LOGIN_MESSAGE = "Enter login: ";
    String ENTERED_LOGIN = "User entered next login: \"%s\"";
    String ENTER_PASSWORD_MESSAGE = "Enter password: ";
    String ENTERED_PASSWORD = "User entered next password: \"%s\"";
    String SUCCESSFUL_SIGN_IN_MESSAGE = "Successful sign in.";
    String FILE_TYPE = "File type: \"%s\"";
    String READING_FILE_MESSAGE = "Reading file: \"%s\"";
    String VALID_FILE_MESSAGE = "Valid file: \"%s\"";
    String VALID_LINE_MESSAGE = "Valid line: \"%s\". Line contains total amount.";
    String AMOUNT_IN_CURRENCY = "Amount in \"%s\": \"%f\"";
    String MOVED_FILE_MESSAGE = "Moved file \"%s\" to \"%s\".";
    String CAN_NOT_CREATE_FILE_MESSAGE = "Can't create file with path: \"%s\"";
    String ENTER_PATH_TO_FOLDER_MESSAGE = "Enter path to folder or use default (\"%s\"): ";
    String FOLDER_EXISTS_MESSAGE = "Folder with \"%s\" path exists.";

}
