package com.mycompany.apu_oodj_afs.core;

import com.mycompany.apu_oodj_afs.models.Module;

import java.io.*;
import java.util.ArrayList;

public class AcademicLeaderDataService {

    private static final String MODULE_FILE = "data/modules.txt";

    // ===== Ensure file exists =====
    private static void ensureFile() throws IOException {
        File file = new File(MODULE_FILE);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    // ===== Load All Modules =====
    public static ArrayList<Module> loadModules() throws IOException {
        ensureFile();
        ArrayList<Module> modules = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(MODULE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length < 4) continue;

                Module module = new Module(
                        data[0],
                        data[1],
                        Integer.parseInt(data[2]),
                        data[3]
                );

                modules.add(module);
            }
        }

        return modules;
    }

    // ===== Save All Modules =====
    private static void saveModules(ArrayList<Module> modules) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MODULE_FILE))) {
            for (Module m : modules) {
                bw.write(m.getModuleCode() + "|" +
                        m.getModuleName() + "|" +
                        m.getCreditHours() + "|" +
                        m.getDescription());
                bw.newLine();
            }
        }
    }

    // ===== Add Module =====
    public static void addModule(Module module) throws IOException {
        ArrayList<Module> modules = loadModules();

        // Check duplicate module code
        for (Module m : modules) {
            if (m.getModuleCode().equalsIgnoreCase(module.getModuleCode())) {
                throw new IllegalArgumentException("Module code already exists.");
            }
        }

        modules.add(module);
        saveModules(modules);
    }

    // ===== Update Module =====
    public static void updateModule(Module updatedModule) throws IOException {
        ArrayList<Module> modules = loadModules();
        boolean found = false;

        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getModuleCode().equalsIgnoreCase(updatedModule.getModuleCode())) {
                modules.set(i, updatedModule);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Module not found.");
        }

        saveModules(modules);
    }

    // ===== Delete Module =====
    public static void deleteModule(String moduleCode) throws IOException {
        ArrayList<Module> modules = loadModules();
        boolean removed = modules.removeIf(
                m -> m.getModuleCode().equalsIgnoreCase(moduleCode)
        );

        if (!removed) {
            throw new IllegalArgumentException("Module not found.");
        }

        saveModules(modules);
    }
}
