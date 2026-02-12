/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.core;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static Path getPath(String fileName) {
        return Path.of(System.getProperty("user.dir"), "data", fileName);
    }

    public static List<String> readLines(String fileName) throws IOException {
        Path p = getPath(fileName);
        if (!Files.exists(p)) {
            Files.createDirectories(p.getParent());
            Files.createFile(p);
        }
        return Files.readAllLines(p);
    }

    public static void writeLines(String fileName, List<String> lines) throws IOException {
        Path p = getPath(fileName);
        if (!Files.exists(p)) {
            Files.createDirectories(p.getParent());
            Files.createFile(p);
        }
        Files.write(p, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    }

    // small helper so repositories are easier
    public static List<String> safeRead(String fileName) throws IOException {
        List<String> lines = readLines(fileName);
        return lines == null ? new ArrayList<>() : lines;
    }
}

