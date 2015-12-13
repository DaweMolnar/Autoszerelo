/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.dialogs;

import autoszerelo.database.controllers.WorkerJpaController;
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
    private final JTextField idField;
    private final JLabel idLabel;
    private final JLabel nameLabel;
    private final JLabel addressLabel;
    private final JLabel phoneLabel;
    private final JLabel priceLabel;
    
    private final WorkerJpaController workerController;
    
    private String dialogError = "";
    private boolean sent = false;
    private boolean closed = false;
    public NewWorkerDialog(){
        setSize(300, 400);
        setTitle("Dolgozó hozzáadása");
        setLayout(new GridLayout(6, 2));
        this.workerController = DatabaseEngine.getWorkerControllerInstance();
        idLabel = new JLabel("Id");
        nameLabel = new JLabel("Név");
        addressLabel = new JLabel("Cím");
        phoneLabel = new JLabel("Telefonszám");
        priceLabel = new JLabel("Óradíj");
       
        idField = new JTextField();
        nameField = new JTextField();
        addressField = new JTextField();
        phoneField = new JTextField();
        priceField = new JTextField();
        
        add(idLabel);
        add(idField);
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
        if(idField.getText().isEmpty() 
           || nameField.getText().isEmpty()
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
        if(!idField.getText().matches("\\d+")) {
            dialogError = "Az id nem pozitív szám!";
            return false;
        }
        if(!priceField.getText().matches("\\d+")) {
            dialogError = "Az ar nem pozitív szám!";
            return false;
        }
        if(workerController.findWorker(Integer.parseInt(idField.getText()))!=null) {
            dialogError = "Már létezik dolgozó az adott id-vel!";
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
    
    public Integer getId() {
        return Integer.parseInt(idField.getText());
    }
}
