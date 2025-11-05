package data.ox;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "client_notifications_exchange";
    @Value("${app.client.id}")
    private String clientId;

    @Bean
    public Exchange exchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue queue() {
        return new Queue(queueName(), true, false, false);
    }

    @Bean
    public Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to((DirectExchange) exchange)
                .with(clientId);
    }

    @Bean
    public String queueName() {
        return "queue_" + clientId;
    }
}
