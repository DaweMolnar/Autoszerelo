/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.main;

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
public class MainFrame extends JFrame implements WorkerTableInterface {
    private JPanel panel;
    private WorkerTable wTable;
    
    public MainFrame() {
        setTitle("Autoszerelo");
        setSize(new Dimension(300,400));
        setLayout(new GridLayout(2,1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        panel = new JPanel();
        wTable = new WorkerTable(this);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(new JScrollPane(wTable.getTable()));

        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Hozzaadas");
        JMenuItem item = new JMenuItem("Add munkatars");
        
        item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
            }
        });
        menu.add(item);
        menubar.add(menu);
        setJMenuBar(menubar);
        add(tabbedPane);
    }
    
    @Override
    public void onWorkerUpdate(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
