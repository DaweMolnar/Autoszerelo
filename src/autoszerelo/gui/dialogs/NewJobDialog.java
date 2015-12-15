/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.dialogs;

import autoszerelo.database.controllers.PartJpaController;
import autoszerelo.database.controllers.WorkerJpaController;
import autoszerelo.database.entities.Parts;
import autoszerelo.database.entities.Workers;
import autoszerelo.database.util.DatabaseEngine;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *
 * @author dmolnar
 */
public class NewJobDialog  extends JDialog{
    List<Integer> partIds;

    private final JTextField nameField;
    private final JTextField addressField;
    private final JTextField licenseField;
    private JComboBox mId;
    private final JTextField lengthField;
    private final JLabel nameLabel;
    private final JLabel addressLabel;
    private final JLabel licenseLabel;
    private final JLabel workerLabel;
    private final JLabel lengthLabel;
    private JList allPartList;
    private JList selectedPartsli;
    private String dialogError = "";
    DefaultListModel partModel;
    DefaultListModel selectedPartModel;
    DefaultComboBoxModel mIdModel;
    private boolean sent = false;
    private boolean closed = false;
    private final PartJpaController partController;
    private final WorkerJpaController workerController;

    public NewJobDialog(){
        partIds = new ArrayList<>();
        this.partController = DatabaseEngine.getPartControllerInstance();
        this.workerController = DatabaseEngine.getWorkerControllerInstance();
        setSize(300, 450);
        setTitle("Munkalap hozzáadása");
        setLayout(new GridLayout(8, 2));
        
        nameLabel = new JLabel("Név");
        addressLabel = new JLabel("Cím");
        licenseLabel = new JLabel("Rendszám");
        workerLabel = new JLabel("MunkatársID");
        lengthLabel = new JLabel("Munka hossza");
       
        nameField = new JTextField();
        addressField = new JTextField();
        licenseField = new JTextField();
        lengthField = new JTextField();
        
        add(nameLabel);
        add(nameField);
        add(addressLabel);
        add(addressField);
        add(licenseLabel);
        add(licenseField);
        addWorkerSelector();
        addPartSelector();
        addPartRemoval();
        add(lengthLabel);
        add(lengthField);
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
        if(lengthField.getText().isEmpty()
           || licenseField.getText().isEmpty()
           || nameField.getText().isEmpty()
           || addressField.getText().isEmpty())
        {
            dialogError = "A kötelező mezők nincsenek kitöltve";
            return false;
        }
        if(!licenseField.getText().matches("[a-zA-Z]{3}\\d{3}")) {
            dialogError = "A rendszám nem megfelelő formátum (pl: abc123)";
            return false;
        }
        if(!lengthField.getText().matches("\\d+")) {
            dialogError = "Az munka hossza nem pozitív szám!";
            return false;
        }
        return true;
    }

    public final void addPartRemoval() {
        JScrollPane scrollPane = new JScrollPane();
        selectedPartModel = new DefaultListModel();
        DefaultListSelectionModel m = new DefaultListSelectionModel();
        m.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        selectedPartsli = new JList(selectedPartModel);
        selectedPartsli.setSelectionModel(m);
        updateSelectedParts();
        scrollPane.setViewportView(selectedPartsli);
        add(scrollPane);
        JButton button = new JButton("Alkatresz Torlese");
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                removeAlkatresz();
            }
        });
        add(button);
    }
    private void addWorkerSelector() {
       List<Workers> workers = workerController.findWorkerEntities();
        mIdModel = new DefaultComboBoxModel();
        mId = new JComboBox(mIdModel);
        for(Workers w: workers) {
            mIdModel.addElement(w);
        } 
        add(workerLabel);
        add(mId);
    }
    
    private void addPartSelector() {
        JScrollPane alkatreszPane = new JScrollPane();
        List<Parts> parts = partController.findPartEntities();
        partModel = new DefaultListModel();
        DefaultListSelectionModel m = new DefaultListSelectionModel();
        m.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        allPartList = new JList(partModel);
        allPartList.setSelectionModel(m);
        for (Parts part : parts) {
            partModel.addElement(part);
        }
        alkatreszPane.setViewportView(allPartList);
        JButton addAlkat = new JButton("Alkatreszek hozzaadasa");
        addAlkat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addAlkatresz();
            }
        });
        add(alkatreszPane);
        add(addAlkat);
    }

    public void updateSelectedParts() {
         List<Parts> parts = partController.findPartEntities();
         selectedPartModel.clear();
         for (Integer partId : partIds) {
            selectedPartModel.addElement(partController.findPart(partId));
        }
    }
    
    public void removeAlkatresz() {
        int[] li2 = selectedPartsli.getSelectedIndices();
        for(int i : li2) {
            partIds.remove(((Parts)selectedPartModel.getElementAt(i)).getId());
        }
        updateSelectedParts();
        selectedPartsli.clearSelection();
    }
    
    public void addAlkatresz() {
        int[] li2 = allPartList.getSelectedIndices();
        for(int i : li2) {
            partIds.add(((Parts)partModel.getElementAt(i)).getId());
        }
        updateSelectedParts();
        allPartList.clearSelection();
    }
            
    
    public boolean isClosed(){
        return closed;
    }
    
    public boolean isSent(){
        return sent;
    }
    
    public String getClientName(){
        return nameField.getText();
    }
    
    public String getAddress(){
        return addressField.getText();
    }
    
    public String getPlateNumber(){
        return licenseField.getText();
    }
    
    public Integer getWorkerId() {
        return ((Workers)mIdModel.getElementAt(mId.getSelectedIndex())).getId();
    }
    
    public Integer getLength() {
        return Integer.parseInt(lengthField.getText());
    }
    public Date getDate() {
        return new Date();
    }
    
    public List<Integer> getParts() {
        return partIds;
    }
}
