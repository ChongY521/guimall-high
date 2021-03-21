package com.gulimall.order;

import com.gulimall.order.entity.OrderItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author aqiang9  2020-09-11
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderApplicationTests {

    /**
     * RabbitTemplate  、AmqpAdmin 、 RabbitMessagingTemplate  、 CachingConnectionFactory
     * <p>
     * 接收消息
     *
     * @RabbitListener 类+方法  监听队列
     * @RabbitHandler 方法     可以区别   消息的类型
     */
    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void testExchange() {
//String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
        DirectExchange directExchange = new DirectExchange("hello-java-exchange", true, false);
        amqpAdmin.declareExchange(directExchange);
        log.info("交换机创建成功");
    }


    @Test
    public void testQueue() {
//    String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments

        Queue queue = new Queue("hello-java-queue", true, false, false);
        amqpAdmin.declareQueue(queue);
        log.info("交换机创建成功");
    }

    @Test
    public void testBinding() {
//        (String destination, Binding.DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments) {
        Binding binding = new Binding("hello-java-queue", Binding.DestinationType.QUEUE, "hello-java-exchange", "hello.java", null);
        amqpAdmin.declareBinding(binding);
        log.info("declareBinding创建成功");
    }


    @Autowired
    RabbitMessagingTemplate messagingTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate ;

    @Test
    public void testSendMsg() {
//        d(String exchange, String routingKey, Object payload

        rabbitTemplate.convertAndSend("hello-java-exchange", "hello.java", "hello 1" , new CorrelationData(UUID.randomUUID().toString() ));
        messagingTemplate.convertAndSend("hello-java-exchange", "hello.java", "hello 2");

        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setCategoryId(1L);
        orderItemEntity.setIntegrationAmount(BigDecimal.TEN);
        orderItemEntity.setGiftIntegration(222);

        messagingTemplate.convertAndSend("hello-java-exchange", "hello.java", orderItemEntity);
    }

    @Test
    public void testReceiveMsg() {
        String s = messagingTemplate.receiveAndConvert("hello-java-queue", String.class);
        System.out.println(s);

    }


}
