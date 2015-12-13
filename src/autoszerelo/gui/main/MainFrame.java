/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.main;

import autoszerelo.database.controllers.PartUsageJpaController;
import autoszerelo.database.entities.Job;
import autoszerelo.database.entities.Parts;
import autoszerelo.database.entities.Partusage;
import autoszerelo.database.entities.Workers;
import autoszerelo.database.util.DatabaseEngine;
import autoszerelo.gui.dialogs.DeleteJobDialog;
import autoszerelo.gui.dialogs.FinalizeJobDialog;
import autoszerelo.gui.dialogs.ModifyJobDialog;
import autoszerelo.gui.dialogs.NewJobDialog;
import autoszerelo.gui.dialogs.NewPartDialog;
import autoszerelo.gui.dialogs.NewWorkerDialog;
import autoszerelo.gui.model.JobTable;
import autoszerelo.gui.model.PartTable;
import autoszerelo.gui.model.WorkerTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author dmolnar
 */
public class MainFrame extends JFrame implements WorkerTableInterface, JobTableInterface{
    private final WorkerTable wTable;
    private final JobTable jTable;
    private final PartTable pTable;
    private final PartUsageJpaController controller;
    public MainFrame() {
        controller = DatabaseEngine.getPartUsageControllerInstance();
        setTitle("Autoszerelo");
        setSize(new Dimension(600,400));
        setLayout(new GridLayout(1,1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        wTable = new WorkerTable(this);
        jTable = new JobTable(this);
        pTable = new PartTable();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Dolgozok",new JScrollPane(wTable.getTable()));
        tabbedPane.add("Munkalapok",new JScrollPane(jTable.getTable()));

        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Hozzaadas");
        addAddMunkatarsMenu(menu);
        addAddMunkalapMenu(menu); //TODO check
        addAddAlkatreszMenu(menu); //TODO check

        JMenu menu2 = new JMenu("Torles");
        addTorolMunkalapMenu(menu2); //TODO check

        JMenu menu3 = new JMenu("Veglegesit");
        addVeglegesitMenu(menu3); //TODO check

        
        JMenu menu4 = new JMenu("Modositas");
        addModositMenu(menu4); //TODO check
        
        menubar.add(menu);
        menubar.add(menu2);
        menubar.add(menu3);
        menubar.add(menu4);
        setJMenuBar(menubar);
        add(tabbedPane);
    }
    
    private void addAddMunkatarsMenu(JMenu menu) {
        JMenuItem item = new JMenuItem("Add munkatars");
        
        item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            NewWorkerDialog dialog = new NewWorkerDialog();
                if(dialog.isClosed() && dialog.isSent()){
                    Workers p = new Workers();
                    p.setName(dialog.getWorkerName());
                    p.setAddress(dialog.getAddress());
                    p.setPhone(dialog.getPhoneNumber());
                    p.setWage(dialog.getWage());
                    p.setId(dialog.getId());
                    wTable.add(p);
                }
            }
        });
        menu.add(item);
    }
    
    private void addAddMunkalapMenu(JMenu menu) {
        JMenuItem item2 = new JMenuItem("Add munkalap");
        item2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            NewJobDialog dialog = new NewJobDialog();
                if(dialog.isClosed() && dialog.isSent()){
                    Job p = new Job();
                    p.setId(dialog.getId());
                    p.setClientname(dialog.getClientName());
                    p.setAddress(dialog.getAddress());
                    p.setLicenseNo(dialog.getPlateNumber());
                    p.setWorkerid(dialog.getWorkerId());
                    p.setDate(dialog.getDate());
                    p.setState(false);
                    p.setLength(dialog.getLength());
                    List<Integer> parts = dialog.getParts();
                    for(Integer part : parts) {
                        controller.create(new Partusage(null,dialog.getId(),part));
                    }
                    jTable.add(p);
                }
            }
        });
        menu.add(item2);
    }
    
    private void addAddAlkatreszMenu(JMenu menu) {
        JMenuItem item3 = new JMenuItem("Add Alkatrész");
        item3.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            NewPartDialog dialog = new NewPartDialog();
                if(dialog.isClosed() && dialog.isSent()){
                    Parts p = new Parts();
                    p.setId(dialog.getId());
                    p.setPrice(dialog.getPrice());
                    p.setName(dialog.getPartName());
                    pTable.add(p);
                }
            }
        });
        menu.add(item3);
    }
    
    private void addTorolMunkalapMenu(JMenu menu) {
        JMenuItem item4 = new JMenuItem("Torol Munkalap");
        item4.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            DeleteJobDialog dialog = new DeleteJobDialog();
                if(dialog.isClosed() && dialog.isDeleted()){
                    Parts p = new Parts();
                    jTable.remove(dialog.getId());
                }
            }
        });
        menu.add(item4);
    }
    
    private void addVeglegesitMenu(JMenu menu) {
        JMenuItem item5 = new JMenuItem("Munkalap veglegesitese");
        item5.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            FinalizeJobDialog dialog = new FinalizeJobDialog();
                if(dialog.isClosed() && dialog.isFinalized()){
                    jTable.finalize(dialog.getId());
                }
            }
        });
        menu.add(item5);
    }
    
    private void addModositMenu(JMenu menu) {
        JMenuItem item6 = new JMenuItem("Munkalap modositasa");
        item6.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            ModifyJobDialog dialog = new ModifyJobDialog();
                if(dialog.isClosed() && dialog.isSent()){
                    Job p = new Job();
                    p.setId(dialog.getId());
                    p.setClientname(dialog.getClientName());
                    p.setAddress(dialog.getAddress());
                    p.setLicenseNo(dialog.getPlateNumber());
                    p.setWorkerid(dialog.getWorkerId());
                    p.setState(false);
                    p.setLength(dialog.getLength());
                    List<Integer> parts = dialog.getParts();
                    jTable.removeAlkatresz(p.getId());
                    for(Integer part : parts) {
                        controller.create(new Partusage(null,dialog.getId(),part));
                    }
                    jTable.modify(p);
                }
            }
        });
        menu.add(item6);
    }
    
    @Override
    public void onWorkerUpdate(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void onJobUpdate(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
