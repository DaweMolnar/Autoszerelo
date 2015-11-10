/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.dialogs;

import autoszerelo.database.controllers.JobJpaController;
import autoszerelo.database.controllers.PartJpaController;
import autoszerelo.database.controllers.WorkerJpaController;
import autoszerelo.database.entities.Job;
import autoszerelo.database.entities.Parts;
import autoszerelo.database.entities.Workers;
import autoszerelo.database.util.DatabaseEngine;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
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
public class ModifyJobDialog  extends JDialog{
    List<Integer> partIds;
    private JComboBox jobId;
    private JTextField clientName;
    private JTextField clientAddress;
    private JTextField licenseNo;
    private JComboBox mId;
    private JTextField workLength;
    DefaultComboBoxModel mIdModel;
    DefaultComboBoxModel jobIdModel;
    private final PartJpaController partController;
    private final WorkerJpaController workerController;
    private final JobJpaController jobController;
    private JList li;
    DefaultListModel partModel;
    private JLabel l0;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JLabel l4;
    private JLabel l5;
    private JLabel l6;
    private boolean sent = false;
    private boolean closed = false;
    public ModifyJobDialog(){
        partIds = new ArrayList<>();
        this.partController = DatabaseEngine.getPartControllerInstance();
        this.workerController = DatabaseEngine.getWorkerControllerInstance();
        this.jobController = DatabaseEngine.getJobControllerInstance();
        setSize(350, 500);
        setTitle("Munkalap módosítása");
        setLayout(new GridLayout(8, 2));
        
        l0 = new JLabel("Munkalap");
        l1 = new JLabel("Név");
        l2 = new JLabel("Cím");
        l3 = new JLabel("Rendszám");
        l4 = new JLabel("MunkatársID");
        l5 = new JLabel("Munka hossza");
       
        jobId = new JComboBox();
        clientName = new JTextField();
        clientAddress = new JTextField();
        licenseNo = new JTextField();
        
        List<Workers> workers = workerController.findWorkerEntities();
        mIdModel = new DefaultComboBoxModel();
        mId = new JComboBox(mIdModel);
        for(Workers w: workers) {
            mIdModel.addElement(w);
        }
        
        List<Job> jobs = jobController.findJobEntities();
        jobIdModel = new DefaultComboBoxModel();
        jobId = new JComboBox(jobIdModel);
        for(Job j: jobs) {
            jobIdModel.addElement(j);
        }
        jobId.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateIdSelection();
            }
        });
        workLength = new JTextField();
        
        
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
        add(jobId);
        add(l1);
        add(clientName);
        add(l2);
        add(clientAddress);
        add(l3);
        add(licenseNo);
        add(l4);
        add(mId);
        add(l5);
        add(workLength);
        
        JButton addAlkat = new JButton("Alkatreszek hozzaadasa");
        
        addAlkat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addAlkatresz();
            }
        });
        add(scrollPane);
        add(addAlkat);
        
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
        updateIdSelection();
        setModal(true);
        setVisible(true);
        
    }
    
    public void addAlkatresz() {
        int[] li2 = li.getSelectedIndices();
        for(int i : li2) {
            partIds.add(((Parts)partModel.getElementAt(i)).getId());
        }
        li.clearSelection();
    }

    public final void updateIdSelection() {
       Job job = (Job)jobIdModel.getElementAt(jobId.getSelectedIndex());
       workLength.setText(Integer.toString(job.getLength()));
       clientName.setText(job.getClientname());
       clientAddress.setText(job.getAddress());
       licenseNo.setText(job.getLicenseNo());
       mId.setSelectedIndex(mIdModel.getIndexOf(workerController.findWorker(job.getWorkerid())));
    }
    
    public boolean isClosed(){
        return closed;
    }
    
    public boolean isSent(){
        return sent;
    }
    
    public String getClientName(){
        return clientName.getText();
    }
    
    public String getAddress(){
        return clientAddress.getText();
    }
    
    public String getPlateNumber(){
        return licenseNo.getText();
    }
    
    public Integer getWorkerId() {
        return ((Workers)mIdModel.getElementAt(mId.getSelectedIndex())).getId();
    }
    
    public Integer getLength() {
        return Integer.parseInt(workLength.getText());
    }

    public Integer getId() {
        return ((Job)jobIdModel.getElementAt(jobId.getSelectedIndex())).getId();
    }
    
    public List<Integer> getParts() {
        return partIds;
    }
}
