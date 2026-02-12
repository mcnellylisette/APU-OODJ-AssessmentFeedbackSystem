/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.core;

import com.mycompany.apu_oodj_afs.models.Module;
import java.util.ArrayList;
import java.util.List;

public class ModuleRepository {
    private static final String FILE = "modules.txt";

    public List<Module> findAll() throws Exception {
        List<Module> out = new ArrayList<>();
        for (String line : FileUtil.safeRead(FILE)) {
            if (line == null || line.trim().isEmpty()) continue;
            String[] p = line.split("\\|", -1);
            if (p.length < 3) continue;

            out.add(new Module(p[0].trim(), p[1].trim(), p[2].trim()));
        }
        return out;
    }

    public void saveAll(List<Module> modules) throws Exception {
        List<String> lines = new ArrayList<>();
        for (Module m : modules) lines.add(m.toString());
        FileUtil.writeLines(FILE, lines);
    }
}
