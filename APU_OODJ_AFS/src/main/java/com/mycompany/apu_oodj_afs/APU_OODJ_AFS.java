/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// package com.mycompany.apu_oodj_afs;

// /**
//  *
//  * @author jamesmcnellylisette
//  */
// public class Main {
    
// }

package com.mycompany.apu_oodj_afs;

import com.mycompany.apu_oodj_afs.gui.LandingPage;
// We don't need to import JFrame here anymore

public class APU_OODJ_AFS {

    public static void main(String[] args) {
        /*
         * The error "adding a window to a container" happens because
         * your LandingPage is a JFrame (a window). You cannot add a
         * window inside another window.
         *
         * The correct way is to create an instance of your LandingPage
         * and make it visible. All other settings (like size, close operation)
         * should be handled inside the LandingPage class itself for good practice.
         */
        LandingPage mainFrame = new LandingPage();
        mainFrame.setVisible(true);
    }
}


