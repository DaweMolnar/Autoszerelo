/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.dialogs;

import autoszerelo.database.controllers.JobJpaController;
import autoszerelo.database.controllers.PartJpaController;
import autoszerelo.database.controllers.PartUsageJpaController;
import autoszerelo.database.controllers.WorkerJpaController;
import autoszerelo.database.entities.Job;
import autoszerelo.database.entities.Parts;
import autoszerelo.database.entities.Partusage;
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
import javax.swing.JOptionPane;
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
    private final JTextField clientName;
    private final JTextField addressField;
    private final JTextField licenseField;
    private JComboBox mId;
    private final JTextField lengthField;
    DefaultComboBoxModel mIdModel;
    DefaultComboBoxModel jobIdModel;
    private final PartJpaController partController;
    private final WorkerJpaController workerController;
    private final JobJpaController jobController;
    private final PartUsageJpaController partusageController;
    private JList li;
    private JList selectedPartsli;
    DefaultListModel partModel;
    DefaultListModel selectedPartModel;
    private final JLabel munkalapLabel;
    private final JLabel nameLabel;
    private final JLabel addressLabel;
    private final JLabel licenseLabel;
    private final JLabel workerLabel;
    private final JLabel lengthLabel;
    private String dialogError = "";
    private boolean sent = false;
    private boolean closed = false;
    

    public ModifyJobDialog(){
        partIds = new ArrayList<>();
        this.partController = DatabaseEngine.getPartControllerInstance();
        this.workerController = DatabaseEngine.getWorkerControllerInstance();
        this.jobController = DatabaseEngine.getJobControllerInstance();
        this.partusageController = DatabaseEngine.getPartUsageControllerInstance();
        setSize(350, 500);
        setTitle("Munkalap módosítása");
        setLayout(new GridLayout(9, 2));
        
        munkalapLabel = new JLabel("Munkalap");
        nameLabel = new JLabel("Név");
        addressLabel = new JLabel("Cím");
        licenseLabel = new JLabel("Rendszám");
        workerLabel = new JLabel("MunkatársID");
        lengthLabel = new JLabel("Munka hossza");
       
        jobId = new JComboBox();
        clientName = new JTextField();
        addressField = new JTextField();
        licenseField = new JTextField();
        lengthField = new JTextField();

        addIdSelector();
        if(jobIdModel.getSize()==0) {
            dialogError = "Nincs modosithato munkalap";
            showErrorDialog();
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            return;
        }
        add(nameLabel);
        add(clientName);
        add(addressLabel);
        add(addressField);
        add(licenseLabel);
        add(licenseField);
        addWorkerSelector();
        add(lengthLabel);
        add(lengthField);
        
        addPartSelector();
        initPartList();
        addPartRemoval();
        
        JButton button = new JButton("Modositas");
        
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
        updateIdSelection();
        setModal(true);
        setVisible(true);
        
    }
        private void showErrorDialog() {
        JOptionPane.showMessageDialog(this,
        dialogError,
        "Módosítási hiba",
        JOptionPane.ERROR_MESSAGE);
    }

    private boolean formValid() {
        if(lengthField.getText().isEmpty()
           || licenseField.getText().isEmpty()
           || clientName.getText().isEmpty()
           || addressField.getText().isEmpty()) {
            dialogError = "Nem minden kötelező mező van kitöltve";
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
    
    private void addIdSelector() {
        List<Job> jobs = jobController.findOpenJobEntities();
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
        add(munkalapLabel);
        add(jobId);
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

    private void initPartList() {
        partIds.clear();
        List<Partusage> usages = partusageController.findPartUsageByJobId(getId());
        for(Partusage usage : usages) {
            partIds.add(usage.getPartid());
        }
    }
    private void addPartSelector() {
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
        JButton addAlkat = new JButton("Alkatreszek hozzaadasa");
        
        addAlkat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addAlkatresz();
            }
        });
        add(scrollPane);
        add(addAlkat);
        
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

    public final void updateIdSelection() {
       Job job = (Job)jobIdModel.getElementAt(jobId.getSelectedIndex());
       lengthField.setText(Integer.toString(job.getLength()));
       clientName.setText(job.getClientname());
       addressField.setText(job.getAddress());
       licenseField.setText(job.getLicenseNo());
       mId.setSelectedIndex(mIdModel.getIndexOf(workerController.findWorker(job.getWorkerid())));
       initPartList();
       updateSelectedParts();
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

    public Integer getId() {
        return ((Job)jobIdModel.getElementAt(jobId.getSelectedIndex())).getId();
    }
    
    public List<Integer> getParts() {
        return partIds;
    }
}
