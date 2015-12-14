/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo;

import autoszerelo.gui.main.MainFrame;

/**
 *
 * @author dmolnar
 */
public class Autoszerelo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            new MainFrame().setVisible(true);
        } catch(Exception e) {
            System.err.println(e.getCause());
            System.exit(-1);
        }
    }
    
}
