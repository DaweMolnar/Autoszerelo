/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.gui.model;

import autoszerelo.database.controllers.PartJpaController;
import autoszerelo.database.entities.Parts;
import autoszerelo.database.util.DatabaseEngine;

/**
 *
 * @author dmolnar
 */
public class PartTable {
    private final PartJpaController controller;
    
    public PartTable() {
        this.controller = DatabaseEngine.getPartControllerInstance();
    }
    
    public void add(Parts p){
        controller.create(p);
    }

    public void remove(Integer id){
        controller.destroy(id);
    }
}
