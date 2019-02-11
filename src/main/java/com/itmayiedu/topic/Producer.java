package com.itmayiedu.topic;

import com.itmayiedu.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {
    private static final String EXCHANGE_NAME = "tianyu_topic_exchange";

    public static void main(String[] args) throws Exception {
        Connection connection =
                ConnectionUtils.getConnection();
        if (connection != null)
            System.out.println("Producer start");

        final Channel channel = connection.createChannel();

        //define new exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String msg1 = "hello! email";
        channel.basicPublish(EXCHANGE_NAME, "1.1.email", null, msg1.getBytes());

        String msg2 = "hello! message";
        channel.basicPublish(EXCHANGE_NAME, "1.1.message", null, msg2.getBytes());

        String msg3 = "hello! wechat";
        channel.basicPublish(EXCHANGE_NAME, "1.wechat", null, msg3.getBytes());

        channel.close();
        connection.close();
    }
}
