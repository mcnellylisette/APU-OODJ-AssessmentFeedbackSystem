/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apu_oodj_afs.models;

public class Module {
    private String moduleId;
    private String moduleName;
    private String leaderId;

    public Module(String moduleId, String moduleName, String leaderId) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.leaderId = leaderId;
    }

    public String getModuleId() { return moduleId; }
    public String getModuleName() { return moduleName; }
    public String getLeaderId() { return leaderId; }

    public void setModuleName(String moduleName) { this.moduleName = moduleName; }

    @Override
    public String toString() {
        return moduleId + "|" + moduleName + "|" + leaderId;
    }
}
