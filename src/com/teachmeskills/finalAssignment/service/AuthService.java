package com.teachmeskills.finalAssignment.service;

import com.teachmeskills.finalAssignment.exception.WrongCredentialsException;
import com.teachmeskills.finalAssignment.session.Session;
import com.teachmeskills.finalAssignment.util.Credentials;
import com.teachmeskills.finalAssignment.validation.CredentialValidator;

import java.util.Scanner;

import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.ACCESS_DENIED_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.REMAINING_ATTEMPTS_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.messages.LogMessages.*;

public class AuthService {

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
        System.out.println(ACCESS_DENIED_MESSAGE);
        return null;
    }

    public static Credentials getUserCredentials(Scanner scanner) {
        System.out.println(ENTER_LOGIN_MESSAGE);
        String login = scanner.nextLine();
        System.out.println(ENTER_PASSWORD_MESSAGE);
        String password = scanner.nextLine();
        return new Credentials(login, password);
    }

    private static boolean isCredentialsValid(Credentials credentials, int retryAttempts) {
        try {
            if (CredentialValidator.isCredentialsValid(credentials)) {
                System.out.println(SUCCESSFUL_SIGN_IN_MESSAGE);
                return true;
            }
        } catch (WrongCredentialsException e) {
            System.out.println(e.getMessage());
            int remainingRetryAttempts = RETRY_ATTEMPTS - retryAttempts;
            System.out.println(String.format(REMAINING_ATTEMPTS_MESSAGE, remainingRetryAttempts));
        }
        return false;
    }
}
