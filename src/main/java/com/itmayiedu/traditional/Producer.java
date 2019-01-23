package com.itmayiedu.traditional;

import com.itmayiedu.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * producer send message to MQ's queue
 */
public class Producer {

    private static final String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        //1.get connection
        Connection connection = ConnectionUtils.getConnection();

        //2.create channel
        Channel channel = connection.createChannel();

        //3.create message
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 50; i++) {
            String msg = "tianyu test queue" + i;
            System.out.println("Producer send:" + msg);

            //4.send
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }

        channel.close();
        connection.close();
    }
}
