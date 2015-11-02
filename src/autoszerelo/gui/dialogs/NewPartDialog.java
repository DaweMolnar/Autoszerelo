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
public class NewPartDialog extends JDialog{
    private JTextField tf1;
    private JTextField tf2;
    private JTextField tf0;
    private JLabel l0;
    private JLabel l1;
    private JLabel l2;
     private boolean sent = false;
    private boolean closed = false;
    public NewPartDialog(){
        setSize(300, 400);
        setTitle("Alkatrész hozzáadása");
        setLayout(new GridLayout(3, 2));
        
        l0 = new JLabel("Id");
        l1 = new JLabel("Név");
        l2 = new JLabel("Ár");
       
        tf0 = new JTextField();
        tf1 = new JTextField();
        tf2 = new JTextField();
        
        add(l0);
        add(tf0);
        add(l1);
        add(tf1);
        add(l2);
        add(tf2);
        
        JButton button = new JButton("Hozzaadas");
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                closed = true;
                //elĂŠg csak bezĂĄrni mert a gui automatikusan megvizsgĂĄlja az ĂŠrtĂŠkeit
                sent = true;
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
    
    public boolean isClosed(){
        return closed;
    }
    
    public boolean isSent(){
        return sent;
    }
    
    public String getPartName(){
        return tf1.getText();
    }
    
    public Integer getPrice() {
        return Integer.parseInt(tf2.getText());
    }
    
    public Integer getId() {
        return Integer.parseInt(tf0.getText());
    }
}
