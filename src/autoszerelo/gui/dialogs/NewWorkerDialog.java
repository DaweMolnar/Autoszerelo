/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.dialogs;

import autoszerelo.database.util.DatabaseEngine;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author dmolnar
 */
public class NewWorkerDialog extends JDialog{
    private final JTextField nameField;
    private final JTextField addressField;
    private final JTextField phoneField;
    private final JTextField priceField;
    private final JLabel nameLabel;
    private final JLabel addressLabel;
    private final JLabel phoneLabel;
    private final JLabel priceLabel;
    
    private String dialogError = "";
    private boolean sent = false;
    private boolean closed = false;
    public NewWorkerDialog(){
        setSize(300, 400);
        setTitle("Dolgozó hozzáadása");
        setLayout(new GridLayout(5, 2));
        nameLabel = new JLabel("Név");
        addressLabel = new JLabel("Cím");
        phoneLabel = new JLabel("Telefonszám");
        priceLabel = new JLabel("Óradíj");
       
        nameField = new JTextField();
        addressField = new JTextField();
        phoneField = new JTextField();
        priceField = new JTextField();
        
        add(nameLabel);
        add(nameField);
        add(addressLabel);
        add(addressField);
        add(phoneLabel);
        add(phoneField);
        add(priceLabel);
        add(priceField);
        
        JButton button = new JButton("Hozzaadas");
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(formValid()) {
                    closed = true;
                    sent = true;
                    setVisible(false);
                } else {
                    showErrorDialog();
                }
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
    
    private void showErrorDialog() {
        JOptionPane.showMessageDialog(this,
        dialogError,
        "Hozzaadasi hiba",
        JOptionPane.ERROR_MESSAGE);
    }
    
    private boolean formValid() {
        if(nameField.getText().isEmpty()
           || priceField.getText().isEmpty()
           || phoneField.getText().isEmpty()
           || addressField.getText().isEmpty())
        {
            dialogError = "A mezők nem mindegyike van kitöltve";
            return false;
        }
        if(!phoneField.getText().matches("\\+[3][6]\\d{1}0\\d{7}")) {
            dialogError = "A telefonszám nem megfelelő formátum (pl: +36301231234)";
            return false;
        }
        if(!priceField.getText().matches("\\d+")) {
            dialogError = "Az ar nem pozitív szám!";
            return false;
        }
        if(getWage()==0) {
            dialogError = "Dolgozo nem dolgozhat ingyen";
            return false;
        }
        return true;
    }

    public boolean isClosed(){
        return closed;
    }
    
    public boolean isSent(){
        return sent;
    }
    
    public String getWorkerName(){
        return nameField.getText();
    }
    
    public String getAddress(){
        return addressField.getText();
    }
    
    public String getPhoneNumber(){
        return phoneField.getText();
    }
    
    public Integer getWage() {
        return Integer.parseInt(priceField.getText());
    }
}
