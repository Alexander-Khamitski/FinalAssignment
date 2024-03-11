package com.teachmeskills.finalAssignment.service;

import com.teachmeskills.finalAssignment.util.Logger;

import java.util.Scanner;

import static com.teachmeskills.finalAssignment.consts.files.FileConstants.RESOURCES_PATH;
import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.ACCESS_DENIED_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.REMAINING_ATTEMPTS_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.messages.LogMessages.ENTER_PATH_TO_FOLDER_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.messages.LogMessages.RETRY_ATTEMPTS;

/**
 * The UserRequestService class provides methods for obtaining user input related to file paths.
 * It includes a method to getting a path from user and validates the entered path.
 */
public class UserRequestService {

    /**
     * Prompts the user to enter a path and returns the entered path.
     *
     * @return The entered path.
     */
    public static String getPathFromUser() {
        Scanner scanner = new Scanner(System.in);
        String path = getPath(scanner);
        scanner.close();
        return path;
    }

    /**
     * Prompts the user to enter a path and validates the entered path.
     * If the entered path exists, returns the path.
     * If the maximum number of retry attempts is reached, logs an access denied message.
     *
     * @param scanner The Scanner object used to read user input.
     * @return The entered path if valid, or null if access is denied.
     */
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