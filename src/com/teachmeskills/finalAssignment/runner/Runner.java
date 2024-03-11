package com.teachmeskills.finalAssignment.runner;

import com.teachmeskills.finalAssignment.encryptor.Encryptor;
import com.teachmeskills.finalAssignment.service.AuthService;
import com.teachmeskills.finalAssignment.service.FileService;
import com.teachmeskills.finalAssignment.service.StatisticService;
import com.teachmeskills.finalAssignment.service.UserRequestService;
import com.teachmeskills.finalAssignment.session.Session;

public class Runner {

    public static void main(String[] args) {
        Session session = AuthService.auth();
        if (session != null) {
            String path = UserRequestService.getPathFromUser();
            if (path != null) {
                FileService.readFilesAndAppendStatistics(session, path);
                StatisticService.getFullStatistics();
            }
        }
    }
}
