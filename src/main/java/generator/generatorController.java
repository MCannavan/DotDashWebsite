package generator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class generatorController {
    @GetMapping("/generator")
    public String greeting() {
        return "generator";
    }
}
