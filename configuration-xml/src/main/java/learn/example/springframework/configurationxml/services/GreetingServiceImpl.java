package learn.example.springframework.configurationxml.services;

public class GreetingServiceImpl implements GreetingService {

    @Override
    public String getGreeting() {
        return "Hello World!!";
    }
}
