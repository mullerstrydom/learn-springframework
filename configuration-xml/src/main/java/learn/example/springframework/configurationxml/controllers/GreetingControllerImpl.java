package learn.example.springframework.configurationxml.controllers;

import learn.example.springframework.configurationxml.services.GreetingService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class GreetingControllerImpl implements GreetingController {

    private final GreetingService greetingService;

    public GreetingControllerImpl(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Override
    @RequestMapping("/greeting")
    public String greeting(Model model) {
        model.addAttribute("greeting", greetingService.getGreeting());
        return "webapp/hello";
    }

    public String test() {
        return greetingService.getGreeting();
    }

}
