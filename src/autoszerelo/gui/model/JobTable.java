/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.model;

import autoszerelo.database.controllers.JobJpaController;
import autoszerelo.database.controllers.PartJpaController;
import autoszerelo.database.controllers.PartUsageJpaController;
import autoszerelo.database.controllers.WorkerJpaController;
import autoszerelo.database.entities.Job;
import autoszerelo.database.entities.Partusage;
import autoszerelo.database.util.DatabaseEngine;
import autoszerelo.gui.main.JobTableInterface;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author dmolnar
 */
public class JobTable {
    private final JobJpaController controller;
    private final WorkerJpaController workerController;
    private final PartUsageJpaController partUsagecontroller;
    private final PartJpaController partController;
    private final JobTableInterface cb; 
    private JTable table;
    private InnerTable innerTable;
    
    public JobTable(JobTableInterface cb) {
        this.controller = DatabaseEngine.getJobControllerInstance();
        this.partUsagecontroller = DatabaseEngine.getPartUsageControllerInstance();
        this.partController = DatabaseEngine.getPartControllerInstance();
        this.workerController = DatabaseEngine.getWorkerControllerInstance();
        this.cb = cb;
        
        innerTable = new InnerTable();
        table = new JTable(innerTable);
        table.setAutoCreateRowSorter(true);
        
        for(Job p : controller.findJobEntities()){
            innerTable.add(p,getPrice(p.getId()));
        }
    }
    public void finalize(Integer id) {
        Job j = controller.findJob(id);
        j.setState(true);
        controller.edit(j);
        innerTable.finalize(id, j);
        innerTable.fireTableDataChanged();
    }
    
    private Integer getPrice(Integer id) {
        Integer price = new Integer(0);
        Job j = controller.findJob(id);
        for(Partusage p : partUsagecontroller.findPartUsageByJobId(id)) {
            price += partController.findPart(p.getPartid()).getPrice();
        }
        price += workerController.findWorker(j.getWorkerid()).getWage()*j.getLength();
        return price;
    }

    public void add(Job p){
        controller.create(p);
        innerTable.add(p, getPrice(p.getId()));
        innerTable.fireTableDataChanged();
    }
    
    public void modify(Job p) {
        Job j = controller.findJob(p.getId());
        j.setClientname(p.getClientname());
        j.setAddress(p.getAddress());
        j.setLicenseNo(p.getLicenseNo());
        j.setWorkerid(p.getWorkerid());
        j.setLength(p.getLength());
        controller.edit(j);
        innerTable.remove(j.getId());
        innerTable.add(j,getPrice(j.getId()));
        innerTable.fireTableDataChanged();
    }
    public Component getTable(){
        return table;
    }
    public void remove(Integer id){
        //try{
            for(Partusage u : partUsagecontroller.findPartUsageByJobId(id)) {
                partUsagecontroller.destroy(u.getId());
            }
            //TODO remove from partusage table
            controller.destroy(id);
            innerTable.remove(id);
            innerTable.fireTableDataChanged();
        //} catch (NonexistentEntityException ex) {
        //    Logger.getLogger(PatientTable.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }

    private class InnerTable extends AbstractTableModel {

        private final String[] columns = {"Id","Dátum","Vevő neve","Teljes ár","Fizetés állapota"};
        private List<Job> data = new ArrayList<>();
        private List<Integer> price = new ArrayList<>();

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public String getColumnName(int column) {
               return columns[column];
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
           Job p = data.get(rowIndex);
           switch(columnIndex){
               case 0:
                   return p.getId();
               case 1:
                   return p.getDate();
               case 2:
                   return p.getClientname();
               case 3:
                   return price.get(rowIndex);
               case 4:
                   return p.getState();
               default:
                   return null;
           }
           
        }

        private void add(Job p, Integer pr) {
            data.add(p);
            price.add(pr);
        }
        private void finalize(Integer id, Job job) {
            data.set(data.indexOf(job), job);
        }

        private void remove(Integer id) {
            for (Job p : data) {
                if (p.getId().equals(id)) {
                    int index = data.indexOf(p);
                    price.remove(index);
                    data.remove(index);
                    break;
                }
            }
        }
        
    }
}
