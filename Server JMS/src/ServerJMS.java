/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.esameserverjms;

import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 *
 * @author biar
 */
public class ServerJMS {
    
    public static void main(String[] args) throws NamingException, JMSException{
        ProduttoreRanking prod = new ProduttoreRanking();
        prod.start();
        
    }
}
