package com.teachmeskills.finalAssignment.runner;

import com.teachmeskills.finalAssignment.encryptor.Encryptor;
import com.teachmeskills.finalAssignment.service.AuthService;
import com.teachmeskills.finalAssignment.session.Session;

public class Runner {

    public static void main(String[] args) {
        System.out.println(Encryptor.decrypt("dMG41HzILLn5VkzOZNX3JdAdZ2Wq1ZhiabWdwwuIYO2d9ht5"));
        System.out.println(Encryptor.decrypt("SmWN5MKCYFXFZGhvVt2gVPUtcunBVoz5dSAV=g=5"));
        Session session = AuthService.auth();
    }
}
