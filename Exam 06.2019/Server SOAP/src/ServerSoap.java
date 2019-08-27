/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.esameserversoap;

import javax.xml.ws.Endpoint;

/**
 *
 * @author biar
 */
public class ServerSoap {
    public static void main(String[] args){
        
        String address = "http://localhost:9000/soapserver";
        
        ServerSoapImpl implementor = new ServerSoapImpl();
    
        Endpoint.publish(address, implementor);
        
        System.out.println("SOAP Server is running.. ");
    }
    
}
