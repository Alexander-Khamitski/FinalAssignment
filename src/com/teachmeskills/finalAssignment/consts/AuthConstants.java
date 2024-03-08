package com.teachmeskills.finalAssignment.consts;

public interface AuthConstants {

    int RETRY_ATTEMPTS = 3;
    String ENTER_LOGIN_MESSAGE = "Enter login: ";
    String ENTER_PASSWORD_MESSAGE = "Enter password: ";
    String SUCCESSFUL_SIGN_IN_MESSAGE = "Successful sign in.";
    String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials.";
    String EMPTY_CREDENTIALS_MESSAGE = "Empty login or password.";
    String REMAINING_ATTEMPTS_MESSAGE = "Try again. The number of remaining attempts: ";
    String ACCESS_DENIED_MESSAGE = "You have used all three attempts. Access denied.";
}
