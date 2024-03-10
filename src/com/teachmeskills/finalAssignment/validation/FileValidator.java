package com.teachmeskills.finalAssignment.validation;

import com.teachmeskills.finalAssignment.exception.InvalidFileException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.*;
import static com.teachmeskills.finalAssignment.consts.files.FileConstants.*;
import static com.teachmeskills.finalAssignment.consts.regex.RegexConstants.YEAR_REGEX;

public class FileValidator {

    public static boolean isFileValid(String fileName) throws InvalidFileException {
        return isTxtDocument(fileName) && isFileFromRequiredYear(fileName) && isFileTypeValid(fileName);
    }

    private static boolean isTxtDocument(String fileName) throws InvalidFileException {
        String extension = fileName.substring(fileName.lastIndexOf("."));
        if (extension.equals(TXT_FORMAT)) {
            return true;
        } else {
            throw new InvalidFileException(String.format(INVALID_FILE_EXTENSION_MESSAGE, fileName, extension));
        }
    }

    private static boolean isFileFromRequiredYear(String fileName) throws InvalidFileException {
        Pattern pattern = Pattern.compile(YEAR_REGEX);
        Matcher matcher = pattern.matcher(fileName);
        if (fileName.contains(YEAR)) {
            return true;
        } else if (matcher.find()) {
            String year = matcher.group();
            throw new InvalidFileException(String.format(INVALID_FILE_YEAR_MESSAGE, fileName, year));
        } else {
            throw new InvalidFileException(String.format(INVALID_FILE_PERIOD_MESSAGE, fileName));
        }
    }

    private static boolean isFileTypeValid(String fileName) throws InvalidFileException {
        for (String fileType : VALID_FILE_TYPES) {
            if (fileName.toLowerCase().contains(fileType)) {
                return true;
            }
        }
        throw new InvalidFileException(String.format(INVALID_FILE_TYPE_MESSAGE, fileName));
    }
}
