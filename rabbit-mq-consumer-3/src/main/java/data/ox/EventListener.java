package data.ox;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventListener {
    @Value("${app.client.id}")
    private String clientId;

    @RabbitListener(queues = "#{queueName}")
    public void receiveMessage(String message) {
        log.info("Client {} received message: {}", clientId, message);
    }
}
