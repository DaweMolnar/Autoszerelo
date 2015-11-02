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
public class DeleteJobDialog extends JDialog {
    private boolean deleted = false;
    private boolean closed = false;
    public DeleteJobDialog() {
        JButton button = new JButton("Torles");
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                closed = true;
                //elĂŠg csak bezĂĄrni mert a gui automatikusan megvizsgĂĄlja az ĂŠrtĂŠkeit
                deleted = true;
                setVisible(false);
                
            }
        });
        add(button);
    }
    public Integer getId() {
        return 0;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public boolean isClosed(){
        return closed;
    }
        
}
