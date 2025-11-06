package data.ox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DynamicListenerService {
    private static final String LISTENER_ID = "client_listener";

    private final RabbitListenerEndpointRegistry registry;
    private final SimpleRabbitListenerContainerFactory containerFactory;

    public void startListenerForClient(String sessionId, String queueName) {
        MessageListenerContainer container = registry.getListenerContainer(LISTENER_ID);
        if (container != null) {
            log.info("Stopping existing listener for old session");
            container.stop();
            registry.unregisterListenerContainer(LISTENER_ID);
        }

        SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
        endpoint.setId(LISTENER_ID);
        endpoint.setQueueNames(queueName);

        endpoint.setMessageListener(message ->
                log.info("Message received for session {}: {}",
                        sessionId,
                        new String(message.getBody()))
        );

        registry.registerListenerContainer(endpoint, containerFactory, true);
        log.info("Started listener for session {} on queue {}", sessionId, queueName);
    }
}
