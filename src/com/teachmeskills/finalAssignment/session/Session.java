package com.teachmeskills.finalAssignment.session;

import com.teachmeskills.finalAssignment.consts.encryptor.EncryptorConstants;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

public final class Session {

    private String accessToken;
    private Date expirationDate;

    public Session() {
        setAccessToken();
        setExpirationDate();
    }

    public boolean isSessionAlive() {
        return this.accessToken.length() == 24 && this.expirationDate.after(new Date());
    }

    private void setAccessToken() {
        this.accessToken = new Random()
                .ints(24, 0, EncryptorConstants.SALTED_SYMBOLS.length())
                .mapToObj(EncryptorConstants.SALTED_SYMBOLS::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private void setExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 1);
        this.expirationDate = calendar.getTime();
    }
}
