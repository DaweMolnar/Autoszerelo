/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.model;

import autoszerelo.database.controllers.JobJpaController;
import autoszerelo.database.entities.Job;
import autoszerelo.database.util.DatabaseEngine;
import autoszerelo.gui.main.JobTableInterface;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author dmolnar
 */
public class JobTable {
    private final JobJpaController controller;
    private final JobTableInterface cb; 
    private JTable table;
    private InnerTable innerTable;
    
    public JobTable(JobTableInterface cb) {
        this.controller = DatabaseEngine.getJobControllerInstance();
        this.cb = cb;
        
        innerTable = new InnerTable();
        table = new JTable(innerTable);
        table.setAutoCreateRowSorter(true);
        
        for(Job p : controller.findJobEntities()){
            innerTable.add(p);
        }
    }
    
    public void add(Job p){
        innerTable.add(p);
        controller.create(p);
        innerTable.fireTableDataChanged();
    }
    
    public Component getTable(){
        return table;
    }
    public void remove(Long id){
        //try{
            controller.destroy(id);
            innerTable.remove(id.intValue());
            innerTable.fireTableDataChanged();
        //} catch (NonexistentEntityException ex) {
        //    Logger.getLogger(PatientTable.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }
    
    private class InnerTable extends AbstractTableModel {

        private final String[] columns = {"Id","Dátum","Vevő neve","Teljes ár","Fizetés állapota"};
        private List<Job> data = new ArrayList<>();
        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            Job p = data.get(rowIndex);
            switch(columnIndex){
                case 2:
                    p.setClientname((String)aValue);
                    break;
                default:
                    break;
            }
            try{
                controller.edit(p);
                cb.onJobUpdate(p.getId());
            } catch (Exception ex) {
                Logger.getLogger(WorkerTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1 || columnIndex == 2;
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
                   return p.getPrice();
               case 4:
                   return p.getState();
               default:
                   return null;
           }
           
        }

        private void add(Job p) {
            data.add(p);
        }

        private void remove(Integer id) {
            for (Job p : data) {
                if (p.getId().equals(id)) {
                    data.remove(p);
                    break;
                }
            }
        }
        
    }
}
