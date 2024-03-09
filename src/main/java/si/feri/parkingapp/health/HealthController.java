package si.feri.parkingapp.health;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.env.Environment;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
@Tag(name = "Health")
public class HealthController {

    private final Environment environment;

    public HealthController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        var now = LocalDateTime.now();
        HashMap<String, String> response = new HashMap<>();

        response.put("status", "ok");
        response.put("time", now.toString());
        response.put("environment", environment.getActiveProfiles()[0]);

        return response;
    }
}
