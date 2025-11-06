package data.ox;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClientInfrastructureService {
    private static final Map<String, Object> ARGUMENTS = Map
            .of(
                    "x-message-ttl", 30000,
                    "x-expires", 60000
            );
    private static final String QUEUE_NAME_TEMPLATE = "queue_%s";
    private final RabbitAdmin rabbitAdmin;
    private final DirectExchange directExchange;

    public String createQueueAndBinding(String sessionId) {
        String queueName = QUEUE_NAME_TEMPLATE
                .formatted(sessionId);

        Queue queue = new Queue(queueName,
                true,
                false,
                false,
                ARGUMENTS);
        rabbitAdmin.declareQueue(queue);

        Binding binding = BindingBuilder
                .bind(queue)
                .to(directExchange)
                .with(sessionId);

        rabbitAdmin.declareBinding(binding);

        return queueName;
    }
}
