package data.ox;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @PostMapping
    public void testReceiver(@RequestParam String sessionId) {
        authorizationService.ifClientVerified(sessionId);
    }
}
