/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;

/**
 *
 * @author dmolnar
 */
public class FinalizeJobDialog extends JDialog {
    private boolean finalized = false;
    private boolean closed = false;
    public FinalizeJobDialog() {
        JButton button = new JButton("Lezaras");
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                closed = true;
                //elĂŠg csak bezĂĄrni mert a gui automatikusan megvizsgĂĄlja az ĂŠrtĂŠkeit
                finalized = true;
                setVisible(false);
                
            }
        });
        add(button);
    }
    public Integer getId() {
        return 0;
    }
    public boolean isFinalized() {
        return finalized;
    }
    public boolean isClosed(){
        return closed;
    }
    
}
