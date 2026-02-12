package com.mycompany.apu_oodj_afs.core;

import com.mycompany.apu_oodj_afs.models.*;
import java.io.*;

public class LoginManager {

    public static User authenticate(String username, String password) {

        try (BufferedReader br = new BufferedReader(new FileReader("data/users.txt"))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split("\\|");
                if (data.length < 6) continue;

                String userID = data[0].trim();
                String uname = data[1].trim();
                String pass = data[2].trim();
                String name = data[3].trim();
                String email = data[4].trim();
                String role = data[5].trim();

                if (uname.equals(username) && pass.equals(password)) {

                    if (role.equalsIgnoreCase("Admin")) {
                        return new Admin(userID, uname, pass, name, role, email);

                    } else if (role.equalsIgnoreCase("Student")) {
                        return new Student(userID, uname, pass, name, role, email);

                    } else if (role.equalsIgnoreCase("Lecturer")) {
                        return new Lecturer(userID, uname, pass, name, role, email);

                    } else if (role.equalsIgnoreCase("AcademicLeader")) {
                        return new AcademicLeader(userID, uname, pass, name, role, email);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading users file.");
        }

        return null;
    }
}
