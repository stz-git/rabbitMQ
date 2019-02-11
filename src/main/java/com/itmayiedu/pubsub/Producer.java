package com.itmayiedu.pubsub;

import com.itmayiedu.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {

    private static final String EXCHANGE_NAME = "tianyu_direct_exchange";

    public static void main(String[] args) throws Exception {
        Connection connection =
                ConnectionUtils.getConnection();
        if (connection != null)
            System.out.println("Producer start");

        Channel channel = connection.createChannel();

        //producer bind exchange
        //param1:exchange name
        //param2:exchange type : fanout,direct,topic,header
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        for (int i = 0; i < 50; i++) {
            String errorMsg = "hello! please send email. count :"+(i+1);
            System.out.println("Producer send errorMsg:" + errorMsg);

            //send msg to exchange
            //param1: exchange's name
            //param2: routing key
            //
            //param4: msg
            channel.basicPublish(EXCHANGE_NAME, "email", null, errorMsg.getBytes());
        }

        String infoMsg = "hello! please send msg";
        System.out.println("Producer send infoMsg:" + infoMsg);
        channel.basicPublish(EXCHANGE_NAME, "msg", null, infoMsg.getBytes());

        channel.close();
        connection.close();
    }
}
