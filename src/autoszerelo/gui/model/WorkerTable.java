/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.model;

import autoszerelo.database.controllers.WorkerJpaController;
import autoszerelo.database.entities.Workers;
import autoszerelo.database.util.DatabaseEngine;
import autoszerelo.gui.main.WorkerTableInterface;
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
public class WorkerTable {
    private final WorkerJpaController controller;
    private final WorkerTableInterface cb; 
    private JTable table;
    private InnerTable innerTable;
    
    public WorkerTable(WorkerTableInterface cb) {
        this.controller = DatabaseEngine.getWorkerControllerInstance();
        this.cb = cb;
        
        innerTable = new InnerTable();
        table = new JTable(innerTable);
        table.setAutoCreateRowSorter(true);
        
        for(Workers p : controller.findWorkerEntities()){
            innerTable.add(p);
        }
    }
    
    public void add(Workers p){
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
            innerTable.remove(id);
            innerTable.fireTableDataChanged();
        //} catch (NonexistentEntityException ex) {
        //    Logger.getLogger(PatientTable.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }
    
    private class InnerTable extends AbstractTableModel {

        private final String[] columns = {"Név","Cím","Telefonszám","Óradíj"};
        private List<Workers> data = new ArrayList<>();

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
           Workers p = data.get(rowIndex);
           switch(columnIndex){
               case 0:
                   return p.getName();
               case 1:
                   return p.getAddress();
               case 2:
                   return p.getId();
               case 3:
                   return p.getWage();
               default:
                   return null;
           }
           
        }

        private void add(Workers p) {
            data.add(p);
        }

        private void remove(Long id) {
            for (Workers p : data) {
                if (p.getId().equals(id)) {
                    data.remove(p);
                    break;
                }
            }
        }
        
    }
}
