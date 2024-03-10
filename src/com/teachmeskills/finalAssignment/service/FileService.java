package com.teachmeskills.finalAssignment.service;

import com.teachmeskills.finalAssignment.exception.InvalidFileException;
import com.teachmeskills.finalAssignment.session.Session;
import com.teachmeskills.finalAssignment.validation.FileValidator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.teachmeskills.finalAssignment.consts.conversion.ConversionConstants.*;
import static com.teachmeskills.finalAssignment.consts.files.FileConstants.*;
import static com.teachmeskills.finalAssignment.consts.messages.ErrorMessages.*;
import static com.teachmeskills.finalAssignment.consts.messages.LogMessages.*;
import static com.teachmeskills.finalAssignment.consts.regex.RegexConstants.*;

public class FileService {

    public static void readFilesAndAppendStatistics(Session session, String path) {
        if (session.isSessionAlive()) {
            List<File> files = getValidFileList(path);
            files.forEach(
                    file -> {
                        System.out.println(String.format(READING_FILE_MESSAGE, file.getPath()));
                        String fileType = getFileType(file);
                        if (fileType != null) {
                            readFileAndAppendStatistics(file.getPath(), fileType);
                        }
                    }
            );
        } else {
            System.out.println(EXPIRED_SESSION_MESSAGE);
        }
    }

    private static List<File> getValidFileList(String path) {
        try {
            return Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .filter(fileName -> isFileValid(fileName.getFileName().toString()))
                    .map(Path::toFile)
                    .toList();
        } catch (FileNotFoundException e) {
            System.out.println(String.format(FILE_NOT_FOUND_EXCEPTION_MESSAGE, path));
        } catch (IOException e) {
            System.out.println(String.format(IO_EXCEPTION_MESSAGE, e.getMessage()));
        }
        return null;
    }

    private static boolean isFileValid(String fileName) {
        try {
            if (FileValidator.isFileValid(fileName)) {
                System.out.println(String.format(VALID_FILE_MESSAGE, fileName));
                return true;
            }
        } catch (InvalidFileException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private static String getFileType(File file) {
        Pattern p = Pattern.compile(FILE_TYPES_REGEX);
        Matcher m = p.matcher(file.getName().toLowerCase());
        if (m.find()) {
            String fileType = m.group();
            System.out.println(String.format(FILE_TYPE, fileType));
            return fileType;
        } else {
            System.out.println(String.format(INVALID_FILE_TYPE_MESSAGE, file.getPath()));
        }
        return null;
    }

    private static void readFileAndAppendStatistics(String path, String fileType) {
        try {
            List<String> lines = Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
            lines.forEach(
                    line -> StatisticService.appendStatisticIfLineContainsTotalAmount(fileType, line)
            );
        } catch (FileNotFoundException e) {
            System.out.println(String.format(FILE_NOT_FOUND_EXCEPTION_MESSAGE, path));
        } catch (IOException e) {
            System.out.println(String.format(IO_EXCEPTION_MESSAGE, path));
        } catch (Exception e) {
            System.out.println(String.format(EXCEPTION_MESSAGE, e.getMessage()));
        }
    }

    public static double getLineTotalAmount(String regex, String fileType, String fileLine) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(fileLine);
        m.find();
        double amount = switch (fileType) {
            case ORDER -> Double.parseDouble(m.group().replace(",", ""));
            case BILL -> Double.parseDouble(m.group().replace(",", "."));
            case INVOICE -> Double.parseDouble(m.group());
            default -> throw new IllegalStateException(String.format(INVALID_FILE_TYPE_MESSAGE, fileType));
        };
        return amount;
    }

    public static String getLineCurrency(String fileLine) {
        if (fileLine.toUpperCase().contains(GBR)) {
            return GBR;
        } else if (fileLine.toUpperCase().contains(EUR)) {
            return EUR;
        } else {
            return USD;
        }
    }
}
