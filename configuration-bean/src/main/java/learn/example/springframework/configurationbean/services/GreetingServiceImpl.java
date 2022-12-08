package learn.example.springframework.configurationbean.services;

public class GreetingServiceImpl implements GreetingService {

    @Override
    public String getGreeting() {
        return "Hello World!!";
    }
}
