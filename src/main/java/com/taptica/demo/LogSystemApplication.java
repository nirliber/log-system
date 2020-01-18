package com.taptica.demo;

import com.taptica.demo.loggers.LogWriter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.time.format.DateTimeFormatter;


@EnableScheduling
@SpringBootApplication
public class LogSystemApplication {

    public static final String TOPIC_EXCHANGE_NAME = "log-system-exchange";

    public static final String QUEUE_NAME = "log-system";

    public static final String ROUTING_KEY = "THIS.IS.SECRET";

    public static final File LOG_FILE = new File("reports.log").getAbsoluteFile();

    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss.SSSSz");


    //Each minute
    public static final String CRON_SCHEDULER_STRING = "0 * * * * *";


    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(LogWriter logWriter) {
        return new MessageListenerAdapter(logWriter, "receiveMessage");
    }

    public static void main(String[] args) {
        SpringApplication.run(LogSystemApplication.class, args);
    }

}
