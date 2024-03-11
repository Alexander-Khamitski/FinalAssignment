package com.teachmeskills.finalAssignment.service;

import com.teachmeskills.finalAssignment.encryptor.Encryptor;
import com.teachmeskills.finalAssignment.exception.WrongCredentialsException;
import com.teachmeskills.finalAssignment.session.Session;
import com.teachmeskills.finalAssignment.util.Credentials;
import com.teachmeskills.finalAssignment.util.Logger;
import com.teachmeskills.finalAssignment.validation.CredentialValidator;

import java.util.Scanner;

import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.ACCESS_DENIED_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.REMAINING_ATTEMPTS_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.messages.LogMessages.*;

/**
 * The AuthService class provides methods for user authentication.
 * It allows users to authenticate with their credentials using a username and password.
 */
public class AuthService {

    /**
     * Performs user authentication. Calls getUserCredentials(scanner) method.
     * If the credentials are valid, a new session is created and returned.
     * If the authentication fails after the maximum number of retry attempts, an access is denied.
     *
     * @return A Session object if authentication is successful, or null if authentication fails.
     */
    public static Session auth() {
        Scanner scanner = new Scanner(System.in);
        int retryAttempts = 1;
        while (retryAttempts <= RETRY_ATTEMPTS) {
            Credentials credentials = getUserCredentials(scanner);
            if (isCredentialsValid(credentials, retryAttempts)) {
                return new Session();
            }
            retryAttempts++;
        }
        scanner.close();
        Logger.info(ACCESS_DENIED_MESSAGE);
        return null;
    }

    /**
     * Prompts the user to enter their credentials via Scanner.
     * The entered login and password are encrypted before logging.
     *
     * @param scanner The Scanner object to read user input from.
     * @return The Credentials object containing the entered login and password.
     */
    public static Credentials getUserCredentials(Scanner scanner) {
        Logger.info(ENTER_LOGIN_MESSAGE);
        String login = scanner.nextLine();
        Logger.info(String.format(ENTERED_LOGIN, Encryptor.encrypt(login)));
        Logger.info(ENTER_PASSWORD_MESSAGE);
        String password = scanner.nextLine();
        Logger.info(String.format(ENTERED_PASSWORD, Encryptor.encrypt(password)));
        return new Credentials(login, password);
    }

    /**
     * Checks if the provided credentials are valid.
     * If the credentials are valid, logs a successful sign-in message.
     * If the credentials are invalid, logs an error message and notifies the user about the remaining retry attempts.
     * There are 3 retry attempts.
     *
     * @param credentials   The Credentials object containing the login and password to validate.
     * @param retryAttempts The number of retry attempts made for authentication.
     * @return true if the credentials are valid, false otherwise.
     */
    private static boolean isCredentialsValid(Credentials credentials, int retryAttempts) {
        try {
            if (CredentialValidator.isCredentialsValid(credentials)) {
                Logger.info(SUCCESSFUL_SIGN_IN_MESSAGE);
                return true;
            }
        } catch (WrongCredentialsException e) {
            Logger.error(e.getMessage());
            int remainingRetryAttempts = RETRY_ATTEMPTS - retryAttempts;
            Logger.info(String.format(REMAINING_ATTEMPTS_MESSAGE, remainingRetryAttempts));
        }
        return false;
    }
}
