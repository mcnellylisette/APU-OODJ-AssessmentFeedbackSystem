package com.mycompany.apu_oodj_afs.core;

import com.mycompany.apu_oodj_afs.models.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public final class UserProfileService {

    private UserProfileService() {}

    // Uses same robust resolver idea; but points to users.txt
    private static Path resolveUsersFile() throws IOException {
        Path cwd = Paths.get(System.getProperty("user.dir"));

        Path p1 = cwd.resolve("data").resolve("users.txt"); // module/data/users.txt
        Path p2 = cwd.getParent() != null
                ? cwd.getParent().resolve("data").resolve("users.txt") // ../data/users.txt
                : null;

        if (Files.exists(p1)) return p1;
        if (p2 != null && Files.exists(p2)) return p2;

        // Create in module folder if missing
        Path dataDir = cwd.resolve("data");
        if (!Files.exists(dataDir)) Files.createDirectories(dataDir);
        Files.createFile(p1);
        return p1;
    }

    // Update the user record in users.txt by userID (safe rewrite)
    // File format: userID|username|password|name|email|role
    public static void updateUserProfile(User user, String newName, String newEmail, String newPasswordOrNull) throws IOException {
        if (user == null) throw new IllegalArgumentException("User is required.");

        String userId = user.getuserID();
        if (isBlank(userId)) throw new IllegalArgumentException("User ID missing.");

        if (isBlank(newName)) throw new IllegalArgumentException("Name cannot be empty.");
        if (isBlank(newEmail) || !newEmail.contains("@")) throw new IllegalArgumentException("Enter a valid email address.");

        Path usersFile = resolveUsersFile();
        List<String> lines = readLines(usersFile);

        boolean updated = false;
        List<String> out = new ArrayList<>();

        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length < 6) {
                out.add(line); // keep bad/unknown lines unchanged
                continue;
            }

            String fileUserId = p[0].trim();

            if (fileUserId.equalsIgnoreCase(userId.trim())) {
                String username = p[1].trim();
                String password = p[2]; // keep exact
                String role = p[5].trim();

                String finalPassword = (newPasswordOrNull != null && !newPasswordOrNull.isBlank())
                        ? newPasswordOrNull
                        : password;

                // userID|username|password|name|email|role
                String newLine = userId + "|" + username + "|" + finalPassword + "|" +
                        newName.trim() + "|" + newEmail.trim() + "|" + role;

                out.add(newLine);
                updated = true;
            } else {
                out.add(line);
            }
        }

        if (!updated) {
            throw new IllegalStateException("User ID not found in users.txt: " + userId);
        }

        writeAllAtomically(usersFile, out);

        // Update in-memory object (name/email setters exist in your User.java)
        user.setName(newName.trim());
        user.setEmail(newEmail.trim());
    }

    private static List<String> readLines(Path file) throws IOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) list.add(line);
            }
        }
        return list;
    }

    private static void writeAllAtomically(Path target, List<String> lines) throws IOException {
        Path tmp = target.resolveSibling(target.getFileName().toString() + ".tmp");

        try (BufferedWriter bw = Files.newBufferedWriter(tmp, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        }

        Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
