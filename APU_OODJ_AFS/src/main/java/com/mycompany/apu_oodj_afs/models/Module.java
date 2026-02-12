package com.mycompany.apu_oodj_afs.models;

import java.io.Serializable;

public class Module implements Serializable {

    private String moduleCode;
    private String moduleName;
    private int creditHours;
    private String description;

    public Module(String moduleCode, String moduleName, int creditHours, String description) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.creditHours = creditHours;
        this.description = description;
    }

    // ===== Validation =====
    public boolean validateModule() {
        return moduleCode != null && !moduleCode.isBlank()
                && moduleName != null && !moduleName.isBlank()
                && creditHours > 0;
    }

    // ===== Getters =====
    public String getModuleCode() {
        return moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public String getDescription() {
        return description;
    }

    // ===== Setters =====
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ===== Display helper =====
    @Override
    public String toString() {
        return moduleCode + " - " + moduleName + " (" + creditHours + " credits)";
    }
}
