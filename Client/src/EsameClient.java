/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.esameclient;


import com.mycompany.esameserversoap.Professor;
import com.mycompany.esameserversoap.ServerSoapImplService;
import com.mycompany.esameserversoap.ServerSoapInterface;
import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author biar
 */
public class EsameClient {
    
    public static void main(String[] args) throws NamingException, JMSException{
    
    ServerSoapInterface ser = new ServerSoapImplService().getServerSoapImplPort();
    
    Context jndiContext = null; 
        ConnectionFactory connectionFactory = null; 
        Connection connection = null; 
        Session session = null; 
        Destination destination = null; 
        String destinationName = "dynamicTopics/professors";
        
        Properties props = new Properties(); 
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL,"tcp://localhost:61616"); 
        jndiContext = new InitialContext(props);
        
        connectionFactory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory"); 
        destination = (Destination)jndiContext.lookup(destinationName);
        
        connection = connectionFactory.createConnection(); 
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageConsumer consumer = session.createConsumer(destination);
        connection.start();
        
        
        while(true){
            Message m = consumer.receive();
            if(m!=null){
                if (m instanceof TextMessage){
                TextMessage message = (TextMessage) m;
                
                String id = message.getStringProperty("Nome");
                float rank = message.getFloatProperty("Rank");
                
                Professor prof = ser.getDetails(id);
                //System.out.println("sono qui: " + id);
                System.out.println("ID: " + id + "," + "RANK: " + rank + " ... bravo prof " + prof.getName() + " " + prof.getSurname());
                }
            
           }
        }
        
    }
}
    
