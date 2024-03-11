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

/**
 * The FileService class provides methods for reading files and appending statistics to them.
 * It also includes utility methods for file handling such as moving files, appending to files,
 * creating files if they do not exist, and checking if a folder exists.
 */
public class FileService {

    /**
     * Reads files from the specified path, appends statistics to valid files.
     * If the provided session is expired, logs an error message.
     *
     * @param session The Session object representing the user's session.
     * @param path    The path from which files will be read and statistics appended.
     */
    public static void readFilesAndAppendStatistics(Session session, String path) {
        if (session.isSessionAlive()) {
            List<File> files = getValidFileList(path);
            if (!files.isEmpty()) {
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
                Logger.warn(String.format(NO_VALID_FILE_MESSAGE, path));
            }
        } else {
            Logger.error(EXPIRED_SESSION_MESSAGE);
        }
    }

    /**
     * Provides a list of valid files from the specified path.
     * Filters out invalid files.
     *
     * @param path The path from which files will be retrieved.
     * @return A list of valid File objects, or null if an error occurs.
     */
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

    /**
     * Checks if a file is valid based on its name.
     * Logs messages for valid files and moves invalid files to a invalidFiles folder.
     *
     * @param file The File object to validate.
     * @return true if the file is valid, false otherwise.
     */
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

    /**
     * Determines the type of a file based on its name.
     * Logs the file type if found.
     *
     * @param file The File object representing the file.
     * @return The type of the file, or null if the file type cannot be determined.
     */
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

    /**
     * Reads a file, processes each line, and appends statistics if applicable.
     * Logs error messages if the file cannot be read or if an exception occurs.
     *
     * @param path     The path of the file to read.
     * @param fileType The type of the file.
     */
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

    /**
     * Extracts the total amount from a line of text based on the provided regex pattern and file type.
     *
     * @param regex     The regex pattern to match the total amount.
     * @param fileType  The type of the file.
     * @param fileLine  The line of text containing the total amount.
     * @return The total amount extracted from the line.
     * @throws IllegalStateException If the file type is invalid.
     */
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

    /**
     * Determines the currency of a line of text based on its content.
     *
     * @param fileLine The line of text to analyze.
     * @return The currency found in the line.
     */
    public static String getLineCurrency(String fileLine) {
        if (fileLine.toUpperCase().contains(GBR)) {
            return GBR;
        } else if (fileLine.toUpperCase().contains(EUR)) {
            return EUR;
        } else {
            return USD;
        }
    }

    /**
     * Moves a file to the specified path.
     *
     * @param file The file to move.
     * @param path The destination path for the file.
     */
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

    /**
     * Appends new value to a file.
     *
     * @param path  The path of the file to append to.
     * @param value The value to append.
     */
    public static void appendFile(String path, String value) {
        try {
            value += "\n";
            Files.write(Path.of(path), value.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            Logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a file if it does not exist.
     *
     * @param path     The directory path where the file will be created.
     * @param fileName The name of the file to create.
     */
    public static void createFileIfNotExist(String path, String fileName) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
            Logger.info(String.format(CREATE_NEW_DIR_MESSAGE, path));
        }
        File file = new File(path + "/" + fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
                Logger.info(String.format(CREATE_NEW_FILE_MESSAGE, path + "/" + fileName));
            }
        } catch (IOException e) {
            Logger.error(String.format(CAN_NOT_CREATE_FILE_MESSAGE, path));
        }
    }

    /**
     * Checks if a folder exists.
     *
     * @param path The path to check for the existence of a folder.
     * @return true if the folder exists, false otherwise.
     */
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
