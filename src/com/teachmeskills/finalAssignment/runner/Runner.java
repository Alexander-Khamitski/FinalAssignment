package com.teachmeskills.finalAssignment.runner;

import com.teachmeskills.finalAssignment.service.AuthService;
import com.teachmeskills.finalAssignment.service.FileService;
import com.teachmeskills.finalAssignment.service.StatisticService;
import com.teachmeskills.finalAssignment.session.Session;

public class Runner {

    public static void main(String[] args) {
        Session session = AuthService.auth();
        if (session != null) {
//            FileService.readFilesAndAppendStatistics(session, "resources/orders/");
//            FileService.readFilesAndAppendStatistics(session, "resources/bills/");
//            FileService.readFilesAndAppendStatistics(session, "resources/invoices/");
            FileService.readFilesAndAppendStatistics(session, "resources/");
            StatisticService.getFullStatistics();
        }
    }
}
