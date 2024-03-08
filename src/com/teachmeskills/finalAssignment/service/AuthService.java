package com.teachmeskills.finalAssignment.service;

import com.teachmeskills.finalAssignment.consts.AuthConstants;
import com.teachmeskills.finalAssignment.exception.WrongCredentialsException;
import com.teachmeskills.finalAssignment.session.Session;
import com.teachmeskills.finalAssignment.util.Credentials;
import com.teachmeskills.finalAssignment.validation.CredentialValidator;

import java.util.Scanner;

public class AuthService {

    public static Session auth() {
        Scanner scanner = new Scanner(System.in);
        int retryAttempts = 0;
        while (retryAttempts < AuthConstants.RETRY_ATTEMPTS) {
            Credentials credentials = getUserCredentials(scanner);
            if (isCredentialsValid(credentials, retryAttempts)) {
                return new Session();
            }
            retryAttempts++;
        }
        scanner.close();
        System.out.println(AuthConstants.ACCESS_DENIED_MESSAGE);
        return null;
    }

    public static Credentials getUserCredentials(Scanner scanner) {
        System.out.println(AuthConstants.ENTER_LOGIN_MESSAGE);
        String login = scanner.nextLine();
        System.out.println(AuthConstants.ENTER_PASSWORD_MESSAGE);
        String password = scanner.nextLine();
        return new Credentials(login, password);
    }

    private static boolean isCredentialsValid(Credentials credentials, int retryAttempts) {
        try {
            if (CredentialValidator.isCredentialsValid(credentials)) {
                return true;
            } else {
                int remainingRetryAttempts = AuthConstants.RETRY_ATTEMPTS - retryAttempts;
                System.out.println(AuthConstants.REMAINING_ATTEMPTS_MESSAGE + remainingRetryAttempts);
            }
        } catch (WrongCredentialsException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
