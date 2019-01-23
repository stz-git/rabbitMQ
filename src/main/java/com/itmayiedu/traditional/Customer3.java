package com.itmayiedu.traditional;

import com.itmayiedu.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Customer3 {

    private static final String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws Exception{

        //1.get connection
        Connection connection = ConnectionUtils.getConnection();
        if(null != connection){
            System.out.println("customer start");
        }

        //2.create channel
        final Channel channel = connection.createChannel();

        //3.build channel
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicQos(1);

        //4.define how to handle message
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery
                    (String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException{
                String msg = new String(body,"UTF-8");
                System.out.println("customer3 start handle");
                System.out.println("customer3 get :"+msg);
                try {
                    Thread.sleep(6000);
                    System.out.println("customer3 complete!");
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

            }
        };

        //5.listning queue to get message
        /**
         * @Param2 是否开启自动应答模式（收到消息后通知rabbitmq将该消息从队列中删除）
         */
        channel.basicConsume(QUEUE_NAME,false,defaultConsumer);
    }
}
