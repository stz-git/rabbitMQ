package com.itmayiedu.topic;

import com.itmayiedu.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class EmailConsumer {
    private static final String QUEUE_NAME = "tianyu_topic_email_queue";
    private static final String EXCHANGE_NAME = "tianyu_topic_exchange";

    public static void main(String[] args) throws Exception {
        Connection connection =
                ConnectionUtils.getConnection();
        if (connection != null)
            System.out.println("EmailConsumer start");

        final Channel channel = connection.createChannel();

        //define consumer-queue
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //define queue-exchange
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "#.email");

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery
                    (String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("EmailConsumer receive :" + msg);
            }
        };

        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }
}
