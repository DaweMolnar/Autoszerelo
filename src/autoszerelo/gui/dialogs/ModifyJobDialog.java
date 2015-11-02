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
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author dmolnar
 */
public class ModifyJobDialog  extends JDialog{
    private JTextField tf0;
    private JTextField tf1;
    private JTextField tf2;
    private JTextField tf3;
    private JTextField tf4;
    private JTextField tf5;
    private JLabel l0;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JLabel l4;
    private JLabel l5;
    private boolean sent = false;
    private boolean closed = false;
    public ModifyJobDialog(){
        setSize(300, 400);
        setTitle("Munkalap hozzáadása");
        setLayout(new GridLayout(4, 2));
        
        l0 = new JLabel("Id");
        l1 = new JLabel("Név");
        l2 = new JLabel("Cím");
        l3 = new JLabel("Rendszám");
        l4 = new JLabel("MunkatársID");
        l5 = new JLabel("Munka hossza");
       
        tf0 = new JTextField();
        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf4 = new JTextField();
        tf5 = new JTextField();
        
        add(l0);
        add(tf0);
        add(l1);
        add(tf1);
        add(l2);
        add(tf2);
        add(l3);
        add(tf3);
        add(l4);
        add(tf4);
        add(l5);
        add(tf5);
        
        JButton button = new JButton("Modositas");
        
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
    
    public String getClientName(){
        return tf1.getText();
    }
    
    public String getAddress(){
        return tf2.getText();
    }
    
    public String getPlateNumber(){
        return tf3.getText();
    }
    
    public Integer getWorkerId() {
        return Integer.parseInt(tf4.getText());
    }
    
    public Integer getLength() {
        return Integer.parseInt(tf5.getText());
    }
    public Date getDate() {
        return new Date();
    }
    public Integer getId() {
        return Integer.parseInt(tf0.getText());
    }
}
