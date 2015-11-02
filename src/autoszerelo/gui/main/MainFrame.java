/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.main;

import autoszerelo.database.entities.Job;
import autoszerelo.database.entities.Parts;
import autoszerelo.database.entities.Workers;
import autoszerelo.gui.dialogs.DeleteJobDialog;
import autoszerelo.gui.dialogs.FinalizeJobDialog;
import autoszerelo.gui.dialogs.NewJobDialog;
import autoszerelo.gui.dialogs.NewPartDialog;
import autoszerelo.gui.dialogs.NewWorkerDialog;
import autoszerelo.gui.model.JobTable;
import autoszerelo.gui.model.PartTable;
import autoszerelo.gui.model.WorkerTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
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
    private WorkerTable wTable;
    private JobTable jTable;
    private PartTable pTable;
    
    public MainFrame() {
        setTitle("Autoszerelo");
        setSize(new Dimension(300,400));
        setLayout(new GridLayout(2,1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        wTable = new WorkerTable(this);
        jTable = new JobTable(this);
        pTable = new PartTable();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Dolgozok",new JScrollPane(wTable.getTable()));
        tabbedPane.add("Munkalapok",new JScrollPane(jTable.getTable()));

        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Hozzaadas");
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
                    jTable.add(p);
                }
            }
        });
        menu.add(item2);
        JMenuItem item3 = new JMenuItem("Add Alkatrész");
        item3.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            NewPartDialog dialog = new NewPartDialog();
                if(dialog.isClosed() && dialog.isSent()){
                    Parts p = new Parts();
                    p.setId(dialog.getId());
                    p.setPrice(dialog.getPrice());
                    p.setName(dialog.getName());
                    pTable.add(p);
                }
            }
        });
        menu.add(item3);
        
        JMenu menu2 = new JMenu("Torles");
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
        menu2.add(item4);
        JMenu menu3 = new JMenu("Veglegesit");
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
        menu3.add(item5);
        menubar.add(menu);
        menubar.add(menu2);
        menubar.add(menu3);
        setJMenuBar(menubar);
        add(tabbedPane);
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
