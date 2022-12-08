package learn.example.springframework.configurationbean.config;

import learn.example.springframework.configurationbean.controllers.GreetingControllerImpl;
import learn.example.springframework.configurationbean.services.GreetingService;
import learn.example.springframework.configurationbean.services.GreetingServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Bean
    GreetingServiceImpl greetingServiceImpl(){
        return new GreetingServiceImpl();
    }

    @Bean
    GreetingControllerImpl greetingControllerImpl(GreetingService greetingService) {
        return new GreetingControllerImpl(greetingService);
    }
}
