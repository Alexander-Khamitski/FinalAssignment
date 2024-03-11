package com.teachmeskills.finalAssignment.service;

import com.teachmeskills.finalAssignment.util.Logger;

import java.util.Scanner;

import static com.teachmeskills.finalAssignment.consts.files.FileConstants.RESOURCES_PATH;
import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.ACCESS_DENIED_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.REMAINING_ATTEMPTS_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.messages.LogMessages.ENTER_PATH_TO_FOLDER_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.messages.LogMessages.RETRY_ATTEMPTS;

public class UserRequestService {

    public static String getPathFromUser() {
        Scanner scanner = new Scanner(System.in);
        String path = getPath(scanner);
        scanner.close();
        return path;
    }

    private static String getPath(Scanner scanner) {
        String path;
        int retryAttempts = 1;
        while (retryAttempts <= RETRY_ATTEMPTS) {
            Logger.info(String.format(ENTER_PATH_TO_FOLDER_MESSAGE, RESOURCES_PATH));
            path = scanner.nextLine();
            if (FileService.isFolderExist(path)) {
                return path;
            }
            int remainingRetryAttempts = RETRY_ATTEMPTS - retryAttempts;
            Logger.info(String.format(REMAINING_ATTEMPTS_MESSAGE, remainingRetryAttempts));
            retryAttempts++;
        }
        Logger.warn(ACCESS_DENIED_MESSAGE);
        return null;
    }
}