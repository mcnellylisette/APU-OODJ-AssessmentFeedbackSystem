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
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        // Create a Window (Frame)
        JFrame frame = new JFrame("APU Feedback System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Add your LandingPage Panel to the Window
        frame.add(new LandingPage());
        
        // Show it
        frame.pack();
        frame.setLocationRelativeTo(null); // Center
        frame.setVisible(true);
    }
}


