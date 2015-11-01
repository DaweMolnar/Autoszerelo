/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.main;

import autoszerelo.database.entities.Job;
import autoszerelo.database.entities.Workers;
import autoszerelo.gui.dialogs.NewJobDialog;
import autoszerelo.gui.dialogs.NewWorkerDialog;
import autoszerelo.gui.model.JobTable;
import autoszerelo.gui.model.WorkerTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author dmolnar
 */
public class MainFrame extends JFrame implements WorkerTableInterface, JobTableInterface{
    private JPanel panel;
    private WorkerTable wTable;
    private JobTable jTable;
    
    public MainFrame() {
        setTitle("Autoszerelo");
        setSize(new Dimension(300,400));
        setLayout(new GridLayout(2,1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        panel = new JPanel();
        wTable = new WorkerTable(this);
        jTable = new JobTable(this);
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
        menubar.add(menu);
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
