/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.dialogs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author dmolnar
 */
public class FinalizeJobDialog extends JDialog {
    private boolean finalized = false;
    private boolean closed = false;
    private JTextField tf0;
    private JLabel l0;
    public FinalizeJobDialog() {
       setSize(300, 400);
        setTitle("Munkalap lezaras");
        setLayout(new GridLayout(6, 2));
        
        l0 = new JLabel("Id");
        tf0 = new JTextField();
        add(l0);
        add(tf0);
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
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent winEvt) {
                closed = true;
                setVisible(false);
            }
        });
        
        setModal(true);
        setVisible(true);
    }
    public Integer getId() {
        return Integer.parseInt(tf0.getText());
    }
    public boolean isFinalized() {
        return finalized;
    }
    public boolean isClosed(){
        return closed;
    }
    
}
