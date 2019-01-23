package com.itmayiedu.topic;

import com.itmayiedu.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class MessageConsumer {
    private static final String QUEUE_NAME = "tianyu_topic_message_queue";
    private static final String EXCHANGE_NAME = "tianyu_topic_exchange";

    public static void main(String[] args) throws Exception {
        Connection connection =
                ConnectionUtils.getConnection();
        if (connection != null)
            System.out.println("MessageConsumer start");

        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*.message");

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery
                    (String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("MessageConsumer receive :" + msg);
            }
        };

        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }
}
