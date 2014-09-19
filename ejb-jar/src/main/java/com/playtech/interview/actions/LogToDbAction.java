package com.playtech.interview.actions;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import com.playtech.interview.EventDetails;
import com.playtech.interview.model.LogEntry;

public class LogToDbAction implements Action {

    private static final Logger log = Logger.getLogger(LogToDbAction.class.getName());

    private EntityManager entityManager;
//    @Resource UserTransaction userTran;

    public LogToDbAction(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void execute(EventDetails details) {
    	System.out.println("++++++++++++++++++++++++++ LogToDbAction.execute : Debut +++++++++++++++++++++++++++ ");
        log.info("Logged event to Database");
        // log to DB using the LogEntry
        LogEntry logEntry = new LogEntry();
        logEntry.setMessage(details.getMessage().substring(0, 254));
        logEntry.setStackTrace(details.getStackTrace().substring(0, 254));
        logEntry.setTimestamp(details.getTimestamp());
      
        entityManager.persist(logEntry);
        
        // begin transaction 
//       entityManager.getTransaction().begin();
//        if (!entityManager.contains(logEntry)) {
            //To add to entity manager : persist object 
//           entityManager.persist(logEntry);
            //To save to DB: flush entityManager          
//        }
//        // commit transaction at all
//       entityManager.getTransaction().commit();
        System.out.println("++++++++++++++++++++++++++ LogToDbAction.execute : Debut +++++++++++++++++++++++++++ ");

    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
