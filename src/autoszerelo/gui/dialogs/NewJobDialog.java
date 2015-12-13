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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *
 * @author dmolnar
 */
public class NewJobDialog  extends JDialog{
    List<Integer> partIds;
    
    private JTextField tf0;
    private JTextField tf1;
    private JTextField tf2;
    private JTextField tf3;
    private JComboBox mId;
    private JTextField tf5;
    private JLabel l0;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JLabel l4;
    private JLabel l5;
    private JList li;
    private JList selectedPartsli;
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
        setLayout(new GridLayout(9, 2));
        
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
        
        List<Workers> workers = workerController.findWorkerEntities();
        mIdModel = new DefaultComboBoxModel();
        mId = new JComboBox(mIdModel);
        for(Workers w: workers) {
            mIdModel.addElement(w);
        }
        tf5 = new JTextField();
        
        JScrollPane scrollPane = new JScrollPane();
        List<Parts> parts = partController.findPartEntities();
        partModel = new DefaultListModel();
        DefaultListSelectionModel m = new DefaultListSelectionModel();
        m.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        li = new JList(partModel);
        li.setSelectionModel(m);
        for (Parts part : parts) {
            partModel.addElement(part);
        }
        scrollPane.setViewportView(li);
        
        add(l0);
        add(tf0);
        add(l1);
        add(tf1);
        add(l2);
        add(tf2);
        add(l3);
        add(tf3);
        add(l4);
        add(mId);
        add(l5);
        add(tf5);

        JButton addAlkat = new JButton("Alkatreszek hozzaadasa");
        
        addAlkat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addAlkatresz();
            }
        });
        add(scrollPane);
        add(addAlkat);

        addPartRemoval();
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
        int[] li2 = li.getSelectedIndices();
        for(int i : li2) {
            partIds.add(((Parts)partModel.getElementAt(i)).getId());
        }
        updateSelectedParts();
        li.clearSelection();
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
        return ((Workers)mIdModel.getElementAt(mId.getSelectedIndex())).getId();
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
    
    public List<Integer> getParts() {
        return partIds;
    }
}
