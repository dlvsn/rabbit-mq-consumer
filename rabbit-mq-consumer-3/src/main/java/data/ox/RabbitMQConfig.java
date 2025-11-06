package data.ox;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMQConfig {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Bean
    public ConnectionFactory connectionFactory() {
        log.info("RabbitMQ ConnectionFactory");
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("user");
        connectionFactory.setPassword("password");
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        log.info("RabbitMQ RabbitAdmin");
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public DirectExchange directExchange() {
        log.info("RabbitMQ DirectExchange");
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        log.info("RabbitMQ SimpleRabbitListenerContainerFactory");
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        return factory;
    }
}
