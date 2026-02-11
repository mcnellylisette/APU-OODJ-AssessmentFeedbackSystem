package com.mycompany.apu_oodj_afs.core;

import com.mycompany.apu_oodj_afs.models.Student;
import com.mycompany.apu_oodj_afs.models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public final class UserAuthService {

    private static final Path USERS_FILE = Paths.get("data").resolve("users.txt");

    private UserAuthService() {}

    public static void ensureUserFileExists() throws IOException {
        Path dir = USERS_FILE.getParent();
        if (!Files.exists(dir)) Files.createDirectories(dir);
        if (!Files.exists(USERS_FILE)) Files.createFile(USERS_FILE);
    }

    public static User login(String username, String password) throws IOException {
        ensureUserFileExists();

        if (isBlank(username)) throw new IllegalArgumentException("Username is required.");
        if (isBlank(password)) throw new IllegalArgumentException("Password is required.");

        List<String> lines = readLines();

        for (String line : lines) {
            // userID|username|password|name|email|role
            String[] p = line.split("\\|", -1);
            if (p.length < 6) continue;

            String userID = p[0];
            String fileUsername = p[1];
            String filePassword = p[2];
            String name = p[3];
            String email = p[4];
            String role = p[5];

            if (fileUsername.equals(username) && filePassword.equals(password)) {
                // Create correct subtype based on role
                if (role.equalsIgnoreCase("Student")) {
                    return new Student(userID, fileUsername, filePassword, name, role, email);
                }

                // For now: if other roles not implemented yet, return a safe placeholder error
                throw new IllegalStateException("Role '" + role + "' is not implemented yet.");
            }
        }

        throw new IllegalArgumentException("Invalid username or password.");
    }

    private static List<String> readLines() throws IOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(USERS_FILE, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) list.add(line);
            }
        }
        return list;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
