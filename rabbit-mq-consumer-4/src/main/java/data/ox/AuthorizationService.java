package data.ox;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final ClientInfrastructureService clientInfrastructureService;
    private final DynamicListenerService dynamicListenerService;

    public void ifClientVerified(String sessionId) {
        String queueName = clientInfrastructureService
                .createQueueAndBinding(sessionId);
        dynamicListenerService.startListenerForClient(sessionId, queueName);
    }
}
