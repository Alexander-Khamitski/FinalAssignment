package com.teachmeskills.finalAssignment.validation;

import com.teachmeskills.finalAssignment.encryptor.Encryptor;
import com.teachmeskills.finalAssignment.exception.WrongCredentialsException;
import com.teachmeskills.finalAssignment.storage.StorageMock;
import com.teachmeskills.finalAssignment.util.Credentials;

import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.EMPTY_CREDENTIALS_MESSAGE;
import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.WRONG_CREDENTIALS_MESSAGE;

public class CredentialValidator {

    public static boolean isCredentialsValid(Credentials credentials) throws WrongCredentialsException {
        StorageMock storage = new StorageMock();
        String encryptedLogin = storage.getLogin();
        String encryptedPassword = storage.getPassword();
        String decodedLogin = Encryptor.decrypt(encryptedLogin);
        String decodedPassword = Encryptor.decrypt(encryptedPassword);
        if (credentials.login().trim().equalsIgnoreCase(decodedLogin)
                && credentials.password().trim().equals(decodedPassword)) {
            return true;
        } else if (credentials.login().isEmpty() || credentials.password().isEmpty()) {
            throw new WrongCredentialsException(EMPTY_CREDENTIALS_MESSAGE);
        } else {
            throw new WrongCredentialsException(WRONG_CREDENTIALS_MESSAGE);
        }
    }
}
