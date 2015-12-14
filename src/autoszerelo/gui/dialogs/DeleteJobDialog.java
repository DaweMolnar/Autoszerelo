/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.dialogs;

import autoszerelo.database.controllers.JobJpaController;
import autoszerelo.database.entities.Job;
import autoszerelo.database.util.DatabaseEngine;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author dmolnar
 */
public class DeleteJobDialog extends JDialog {
    private final JobJpaController controller;
    DefaultComboBoxModel jobModel;
    private boolean deleted = false;
    private boolean closed = false;
    private String dialogError = "";
    private final JComboBox idField;
    private final JLabel idLabel;
    public DeleteJobDialog() {
        this.controller = DatabaseEngine.getJobControllerInstance();
        setSize(500, 50);
        setTitle("Munkalap törlése");
        setLayout(new GridLayout(1, 3));
        
        idLabel = new JLabel("Id");
        List<Job> jobs = controller.findJobEntities();
        jobModel = new DefaultComboBoxModel();
        idField = new JComboBox(jobModel);
        for(Job j: jobs) {
            jobModel.addElement(j);
        }
        add(idLabel);
        add(idField);
        JButton button = new JButton("Torles");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(formValid()) {
                    closed = true;
                    deleted = true;
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
        "Törlési hiba",
        JOptionPane.ERROR_MESSAGE);
    }

    private boolean formValid() {
        Job j = (Job)jobModel.getElementAt(idField.getSelectedIndex());
        if(j.getState()==true) {
            dialogError = "A Munkalap már ki van fizetve, így nem törölhető";
            return false;
        }
        return true;
    }
    
    public Integer getId() {
        return ((Job)jobModel.getElementAt(idField.getSelectedIndex())).getId();
    }
    public boolean isDeleted() {
        return deleted;
    }
    public boolean isClosed(){
        return closed;
    }
        
}
