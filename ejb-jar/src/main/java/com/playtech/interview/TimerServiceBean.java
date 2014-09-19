package com.playtech.interview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.playtech.interview.model.AlertConfig;
import com.playtech.interview.model.EventAction;

//@Remote(TimerService.class)
//@Local(TimerServiceLocal.class)
@Stateless(name="timerService")
public class TimerServiceBean implements TimerService, TimerServiceLocal {

    private static final Logger log = Logger.getLogger(TimerServiceBean.class.getName());

    //The use of EXTENDED persistence contexts type is only available to @Stateful beans. 
    @PersistenceContext(unitName = "alertingPU", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    //Container maintains a javax.ejb.SessionContext for each session bean instance and makes this session context available to the beans. 
    //The bean may use the methods in the session context to make callback requests to the container. In addition, you can use the methods inherited from EJBContext
    @Resource
    private SessionContext sessionContext;

    @Resource(mappedName="topic/alertTopic")
    private Topic destination;

    @Resource(mappedName="ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Override
    public void scheduleTimer(long milliseconds) {
        sessionContext.getTimerService().createTimer(0, milliseconds, "=============== Expiration du Timer ===============");
    }

    @Override
    public void scheduleTimer() {
    	if (entityManager==null) System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! entityManager is null !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        AlertConfig config = (AlertConfig) entityManager.createQuery("SELECT cfg FROM AlertConfig cfg").getResultList().get(0);
   	 	
        scheduleTimer(config.getTimeout());
    }

    /**
     * When a timer expires (goes off), the container calls the method annotated @Timeout in the bean’s implementation class. 
     * The @Timeout method contains the business logic that handles the timed event.
     * @param timer
     */   
    @Timeout //Designates a method on a stateless session bean class or message driven bean class that should receive EJB timer expirations for this bean
    public void timeout(@SuppressWarnings("unused") Timer timer) {
    	System.out.println("***************************** TimerServiceBean.timeout : Debut ***************************** ");
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //L'en-tête contient des données standardisées dont le nom commence par JMS (JMSDestination, JMSDeliveryMode, JMSExpiration, JMSPriority, JMSMessageID, JMSTimestamp, JMSRedelivered,  JMSCorrelationID, JMSReplyTo et JMSType)
            //La plupart de ces données sont renseignées lors de l'appel à la méthode send() ou publish().
            
            //Chaque AlertConfig.timeout milliseconds un message JMS de type aleatoirement shoisi dans (INFO, WARNING, EXCEPTION) est crée dans la file 
            //Ce message a pour destination "topic/alertTopic"
            Message message = session.createTextMessage();

            EventType[] eventTypes = EventType.values();
            EventType randomEventType = eventTypes[new Random().nextInt(eventTypes.length)];
            message.setIntProperty(Constants.EVENT_TYPE_PROPERTY_NAME, randomEventType.ordinal());
            fillEventDetails(message);
            MessageProducer producer = session.createProducer(destination);
            producer.send(message);
            log.info("Message sent with eventType=" + randomEventType);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error sending message", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                log.log(Level.WARNING, "Problem closing JMS Connection", ex);
            }
        }
        System.out.println("***************************** TimerServiceBean.timeout : Fin ***************************** ");
    }

    @Override
    public void initializePersistentData() {
        List<EventAction> eventActions = loadActions();
        for (EventAction eventAction : eventActions) {
            entityManager.merge(eventAction);
        }

        AlertConfig config = getAlertConfig();
        entityManager.merge(config);
    }

    private AlertConfig getAlertConfig() {
        try {
            Properties eventActionProperties = new Properties();
            eventActionProperties.load(getClass().getResourceAsStream("/application.properties"));

            long timeout = Long.parseLong(eventActionProperties.getProperty("alert.timeout"));
            AlertConfig config = new AlertConfig();
            config.setTimeout(timeout);
            return config;
         } catch (IOException ex) {
             log.log(Level.SEVERE, "Failed to load alert configuration", ex);
             return null;
         }
    }

    private List<EventAction> loadActions() {
        try {
            Properties eventActionProperties = new Properties();
            eventActionProperties.load(getClass().getResourceAsStream("/eventActions.properties"));

            List<EventAction> eventActions = new ArrayList<EventAction>();
            for (Object key : eventActionProperties.keySet()) {
                EventType eventType = EventType.valueOf(key.toString());
                String value = eventActionProperties.getProperty(key.toString());
                String[] actions = value.split(",");
                for (String actionName : actions) {
                    ActionType actionType = ActionType.valueOf(actionName);

                    EventAction eventAction = new EventAction();
                    eventAction.setActionType(actionType);
                    eventAction.setEventType(eventType);
                    eventActions.add(eventAction);
                }
            }
            return eventActions;
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Failed to load initial data", ex);
            return new ArrayList<EventAction>();
        }
    }

    private void fillEventDetails(Message msg) throws JMSException {
        msg.setStringProperty(Constants.EVENT_MESSAGE_PROPERTY_NAME, getRandomString(20));
        msg.setStringProperty(Constants.EVENT_STACK_TRACE_PROPERTY_NAME, getRandomString(100));
        msg.setLongProperty(Constants.EVENT_TIMESTAMP_PROPERTY_NAME, System.currentTimeMillis());
    }

    private String getRandomString(int len) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i ++) {
            // appending a random ascii char in some arbitrary range
            sb.append(36 + Math.random() * 128);
        }

        return sb.toString();
    }

}
