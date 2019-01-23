package com.itmayiedu.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtils {
    public static Connection getConnection(){
        Connection connection = null;
        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("192.168.13.45");
            factory.setPort(5672);
            factory.setVirtualHost("/tianyu");
            factory.setUsername("root");
            factory.setPassword("root");

            connection = factory.newConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
