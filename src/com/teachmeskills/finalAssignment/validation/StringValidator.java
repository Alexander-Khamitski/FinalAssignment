package com.teachmeskills.finalAssignment.validation;

import com.teachmeskills.finalAssignment.exception.InvalidLineException;
import com.teachmeskills.finalAssignment.util.RegexUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.INVALID_LINE_MESSAGE;

public class StringValidator {

    public static boolean isLineContainsTotalAmount(String fileType, String fileLine) throws  InvalidLineException {
        String regex = RegexUtil.getLineTotalAmountRegexByFiletype(fileType);
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(fileLine.toLowerCase());
        if (m.find()) {
            return true;
        }
        throw new InvalidLineException(String.format(INVALID_LINE_MESSAGE, fileLine));
    }
}
