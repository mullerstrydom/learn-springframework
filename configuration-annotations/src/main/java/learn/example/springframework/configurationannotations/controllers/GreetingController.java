package learn.example.springframework.configurationannotations.controllers;

import org.springframework.ui.Model;

public interface GreetingController {

    String greeting(Model model);
}
