/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoszerelo.database.util;

import autoszerelo.database.controllers.JobJpaController;
import autoszerelo.database.controllers.WorkerJpaController;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author dmolnar
 */
public class DatabaseEngine {
  private static EntityManagerFactory emf;
  private static WorkerJpaController wc;
  private static JobJpaController jc;
 
  public static EntityManagerFactory getEntityManagerFactoryInstance(){
      if(emf == null){
          emf = Persistence.createEntityManagerFactory("AutoszereloPU");
      }
      return emf;
  }
  
  public static WorkerJpaController getWorkerControllerInstance(){
      if(wc == null){
          wc = new WorkerJpaController(getEntityManagerFactoryInstance());
      }
      return wc;
  }
    public static JobJpaController getJobControllerInstance(){
      if(jc == null){
          jc = new JobJpaController(getEntityManagerFactoryInstance());
      }
      return jc;
  }
}
