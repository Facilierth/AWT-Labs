package pl.edu.pwr.awt_lab.Lab5;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
    @RequestMapping("/")
    public String run() {
        return "Hello ! I'm, running";
    }

}
