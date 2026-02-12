/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.core;

import java.util.ArrayList;
import java.util.List;

public class ModuleLecturerRepository {
    private static final String FILE = "module_lecturers.txt";

    public List<String[]> findAll() throws Exception {
        List<String[]> out = new ArrayList<>();
        for (String line : FileUtil.safeRead(FILE)) {
            if (line == null || line.trim().isEmpty()) continue;
            String[] p = line.split("\\|", -1);
            if (p.length < 2) continue;
            out.add(new String[]{p[0].trim(), p[1].trim()});
        }
        return out;
    }

    public void assign(String moduleId, String lecturerId) throws Exception {
        List<String[]> all = findAll();

        // remove old assignment for same module+lecturer if exists
        boolean exists = false;
        for (String[] row : all) {
            if (row[0].equalsIgnoreCase(moduleId) && row[1].equalsIgnoreCase(lecturerId)) {
                exists = true;
                break;
            }
        }
        if (!exists) all.add(new String[]{moduleId.trim(), lecturerId.trim()});

        List<String> lines = new ArrayList<>();
        for (String[] row : all) lines.add(row[0] + "|" + row[1]);
        FileUtil.writeLines(FILE, lines);
    }
}

