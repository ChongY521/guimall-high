package com.gulimall.order.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author aqiang9  2020-09-11
 */
@EnableRabbit
@Configuration
public class MyRabbitmqConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;


    /**
     * 定制 rabbitTemplate
     * 1、服务收到消息就回调
     * 1) spring.rabbitmq.publisher-confirms=true / spring.rabbitmq.publisher-confirm-type=correlated
     * 2) 设置回调  setConfirmCallback
     * 2、消息真确抵达队列
     * 1）   spring.rabbitmq：
     * publisher-returns: true
     * template:
     * mandatory: true
     * 2） 设置回调 setReturnCallback
     * 3、 消费端确认 （保证消息被消费 ） 可以手动ack
     */
    @PostConstruct
    public void init() {
        // 只要抵达broker 就会 ack=true
        /**
         * correlationData:当前消息唯一关联数据 （唯一id） ,
         * ack： 是否收到 ,
         * cause ： 失败原因
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("------------------------------------------");
            System.out.println(correlationData);
            System.out.println(ack);
            System.out.println(cause);
        });

        /**
         * 只要消息没有投递给指定队列 ， 就触发这个失败回调
         * message : 投递失败的消息详细信息
         * replyCode ： 恢复状态码
         * replyText ： 回复文本内容
         * exchange ： 发给的那个交换机
         * routingKey ：  用的那个路由键
         */
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {

            System.out.println(message);
            System.out.println(replyCode);
            System.out.println(replyText);
            System.out.println(exchange);
            System.out.println(routingKey);
        });

//        rabbitTemplate.containerAckMode(AcknowledgeMode.MANUAL);
    }


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
