package com.playtech.interview;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import com.playtech.interview.actions.Action;
import com.playtech.interview.actions.ActionFactory;
import com.playtech.interview.model.EventAction;
import com.playtech.interview.model.LogEntry;

/**
 * 
 * @author Administrateur
 *	In several respects, a message-driven bean resembles a stateless session bean.
 *	A message-driven bean’s instances retain no data or conversational state for a specific client.
 *	All instances of a message-driven bean are equivalent, allowing the EJB container to assign a message to any message-driven bean instance. The container can pool these instances to allow streams of messages to be processed concurrently.
 *	A single message-driven bean can process messages from multiple clients.
 *	Message-driven beans have the following characteristics:
 *	•	They execute upon receipt of a single client message.
 *	•	They are invoked asynchronously.
 *	•	They are relatively short-lived.
 *	•	They do not represent directly shared data in the database, but they can access and update this data.
 *	•	They can be transaction-aware.
 *	•	They are stateless.
 *
 *
 *	Les paramètres nécessaires à la configuration de l'EJB notamment le type et la destination sur laquelle le bean doit écouter doivent être précisés grâce à l'attribut activationConfig. 
 *	Cet attribut est un tableau d'objets de type ActivationConfigProperty.
 */

@MessageDriven(activationConfig = {
		//@ActivationConfigProperty(propertyName="acknowledgeMode", propertyValue="Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),       
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/alertTopic")})
public class AlertBean implements MessageListener {

	//The use of EXTENDED persistence contexts type is only available to @Stateful beans. 
	//Note that the scope really means the scope of the Persistence Context that the Entity Manager manages. It is not the scope of the EntityManager itself.
	@PersistenceContext(unitName = "alertingPU", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;
	
	private int i=0;

    private static final Logger log = Logger.getLogger(AlertBean.class.getName());

    @SuppressWarnings("unchecked")
    @Override    
    //The TransactionAttribute annotation specifies whether the container is to invoke a business method within a transaction context. 
    //The TransactionAttribute annotation can be used for session beans and message driven beans. It can only be specified if container managed transaction demarcation is used.
//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void onMessage(Message msg) {
        try {
        	i++;
        	System.out.println("---------------------------- AlertBean.onMessage : Debut ---------------------------- ");
            int eventId = msg.getIntProperty(Constants.EVENT_TYPE_PROPERTY_NAME);

            EventDetails details = getEventDetails(msg);

            EventType eventType = EventType.values()[eventId];
            log.info("Message received of type: " + eventType);

            Query query = entityManager.createQuery("SELECT ea FROM EventAction ea WHERE ea.eventType=:eventType");
            System.out.println("==========eventType = "+eventType);
            System.out.println("==========Message = "+details.getMessage());          
                        
            query.setParameter("eventType", eventType);
            List<EventAction> result = query.getResultList();
                       
            for (EventAction eventActionType : result) {            	
                performAction(eventActionType.getActionType(), details);
            }

        } catch (JMSException ex) {
            log.log(Level.SEVERE, "Error receiving message", ex);
        }
        	System.out.println("---------------------------- AlertBean.onMessage : Fin ---------------------------- ");
    }

    private EventDetails getEventDetails(Message msg) throws JMSException {
        EventDetails details = new EventDetails();

        details.setMessage(msg.getStringProperty(Constants.EVENT_MESSAGE_PROPERTY_NAME));
        details.setStackTrace(msg.getStringProperty(Constants.EVENT_STACK_TRACE_PROPERTY_NAME));
        details.setTimestamp(msg.getLongProperty(Constants.EVENT_TIMESTAMP_PROPERTY_NAME));

        return details;
    }

  
    /**
     * Permet de logger le message dans le type de support (bdd, fichier, mail, ...) convenable
     * @param actionType
     * @param details
     */
    private void performAction(ActionType actionType, EventDetails details) {
    	
    	 System.out.println("performAction==========actionType.name = "+actionType.name());
        ActionFactory factory = new ActionFactory();
        factory.setEntityManager(entityManager);
        //
        LogEntry logEntry = new LogEntry();
        logEntry.setMessage(details.getMessage().substring(0, 254));
        logEntry.setStackTrace(details.getStackTrace().substring(0, 254));
        logEntry.setTimestamp(details.getTimestamp());
        
//        AlertConfig ac = new AlertConfig();
//        ac.setTimeout(12000);
//        entityManager.persist(ac);
//        entityManager.flush();
       // entityManager.persist(logEntry);
        //
//        EntityTransaction tx = entityManager.getTransaction();
//        tx.begin();
//        entityManager.persist(logEntry);
//        tx.commit();
        //

        Action action = factory.createAction(actionType);
        action.execute(details);        
    }

}
