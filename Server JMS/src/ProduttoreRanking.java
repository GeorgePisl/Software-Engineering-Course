/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.esameserverjms;

import java.util.*;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import javax.jms.JMSException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author biar
 */
public class ProduttoreRanking {
    
    final String id[] = {"1", "2", "3", "4", "5", "6", "7", "8"};
    
    private String generateId(){    
        Random rand = new Random();       
        int msg = rand.nextInt(this.id.length);       
        return this.id[msg];
    }
    
    private float ranking(){
        Random rand = new Random();
        return rand.nextFloat()*100;
    }
    
    private static final Logger LOG = LoggerFactory.getLogger(ProduttoreRanking.class);
    
    public void start() throws NamingException, JMSException {
        
        
            Context jndiContext = null;
            ConnectionFactory connectionFactory = null;
            Connection connection = null;
            Session session = null;
            Destination destination = null;
            MessageProducer producer = null;
            String destinationName = "dynamicTopics/professors";
            
        try {    
            Properties props = new Properties();
            
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
            jndiContext = new InitialContext(props);
            
        } catch (NamingException e) {
           LOG.info("EROOR IN JNDI: " + e.toString());
           System.exit(1);
        }
        
        
        try {
            connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
            destination = (Destination) jndiContext.lookup(destinationName);
        
        } catch (NamingException e) {
            LOG.info("JNDI API lookup failed: " + e.toString());
        }
        
                try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(destination);
            
              
                
                TextMessage message = null;
		String messageType = null;
		
                message = session.createTextMessage();

                float rank;
		while (true) {
			messageType = generateId();
			rank = ranking();
			message.setStringProperty("Nome", messageType);
			message.setFloatProperty("Rank", rank);
                        
			message.setText("ID: " + messageType + ", Ranking: "+ rank);

                                LOG.info(
					this.getClass().getName() + 
				        "Invio ranking: " + message.getText());

			producer.send(message);

			try {
				Thread.sleep(5000);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
        
        catch (JMSException e) {
            LOG.info("Exception occurred: " + e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                }
            }
        }
}
        
        
        
        
            

}
