package com.itmayiedu.pubsub;

import com.itmayiedu.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class EmailConsumer {

    private static final String QUEUE_NAME = "tianyu_email_queue";
    private static final String EXCHANGE_NAME = "tianyu_direct_exchange";

    public static void main(String[] args) throws Exception {
        Connection connection =
                ConnectionUtils.getConnection();
        if (connection != null) {
            System.out.println("EmailConsumer start");
        }

        final Channel channel = connection.createChannel();

        //consumer declare queue
        //param1
        //param2 message save disk
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        //consumer's queue bind exchange, message saves in queue.
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "email");

        channel.basicQos(1);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery
                    (String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    Thread.sleep(1000);
                    String msg = new String(body, "UTF-8");
                    System.out.println("EmailConsumer receive :" + msg);
                } catch (Exception e) {

                } finally {
                    //param2: requeue; true-->message back queue;  false--->delete the message from the queue
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }


            }
        };

        channel.basicConsume(QUEUE_NAME, false, defaultConsumer);
    }
}
