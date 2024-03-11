package com.teachmeskills.finalAssignment.service;

import com.teachmeskills.finalAssignment.exception.InvalidFileException;
import com.teachmeskills.finalAssignment.session.Session;
import com.teachmeskills.finalAssignment.util.Logger;
import com.teachmeskills.finalAssignment.validation.FileValidator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
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
                        Logger.info(String.format(READING_FILE_MESSAGE, file.getPath()));
                        String fileType = getFileType(file);
                        if (fileType != null) {
                            readFileAndAppendStatistics(file.getPath(), fileType);
                        }
                    }
            );
        } else {
            Logger.error(EXPIRED_SESSION_MESSAGE);
        }
    }

    private static List<File> getValidFileList(String path) {
        try {
            return Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .filter(file -> getFileIfFileIsValid(file.toFile()))
                    .map(Path::toFile)
                    .toList();
        } catch (FileNotFoundException e) {
            Logger.error(String.format(FILE_NOT_FOUND_EXCEPTION_MESSAGE, path));
        } catch (IOException e) {
            Logger.error(String.format(IO_EXCEPTION_MESSAGE, e.getMessage()));
        }
        return null;
    }

    private static boolean getFileIfFileIsValid(File file) {
        try {
            if (FileValidator.isFileValid(file.getName())) {
                Logger.info(String.format(VALID_FILE_MESSAGE, file.getName()));
                return true;
            }
        } catch (InvalidFileException e) {
            Logger.warn(e.getMessage());
            moveFile(file, INVALID_FILES_FOLDER_PATH);
        }
        return false;
    }

    private static String getFileType(File file) {
        Pattern p = Pattern.compile(FILE_TYPES_REGEX);
        Matcher m = p.matcher(file.getName().toLowerCase());
        if (m.find()) {
            String fileType = m.group();
            Logger.info(String.format(FILE_TYPE, fileType));
            return fileType;
        } else {
            Logger.warn(String.format(INVALID_FILE_TYPE_MESSAGE, file.getPath()));
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
            Logger.error(String.format(FILE_NOT_FOUND_EXCEPTION_MESSAGE, path));
        } catch (IOException e) {
            Logger.error(String.format(IO_EXCEPTION_MESSAGE, path));
        } catch (Exception e) {
            Logger.error(String.format(EXCEPTION_MESSAGE, e.getMessage()));
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

    private static void moveFile(File file, String path) {
        File invalidFilesPackage = new File(INVALID_FILES_FOLDER);
        if (!invalidFilesPackage.exists()) {
            invalidFilesPackage.mkdirs();
        }
        try {
            Files.move(Paths.get(file.getPath()), Paths.get(path + file.getName()), StandardCopyOption.REPLACE_EXISTING);
            Logger.info(String.format(MOVED_FILE_MESSAGE, file.getPath(), path + file.getName()));
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
    }

    public static void appendFile(String path, String value) {
        try {
            value += "\n";
            Files.write(Path.of(path), value.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            Logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void createFileIfNotExist(String path, String fileName) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(path + "/" + fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            Logger.error(String.format(CAN_NOT_CREATE_FILE_MESSAGE, path));
        }
    }

    public static boolean isFolderExist(String path) {
        File dir = new File(path);
        boolean isFolderExist = dir.exists();
        if (isFolderExist) {
            Logger.info(String.format(FOLDER_EXISTS_MESSAGE, path));
        }
        Logger.error(String.format(FOLDER_DOES_NOT_EXISTS_MESSAGE, path));
        return isFolderExist;
    }

}
