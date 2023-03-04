package learn.example.hibernateeventlisteners.bootstrap;

import learn.example.hibernateeventlisteners.data.models.UserEntity;
import learn.example.hibernateeventlisteners.data.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationBootstrap implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args)  {
        System.out.println(":: Bootstrap ::" + Thread.currentThread().getId());
        userService.create(UserEntity.builder().userName("john.doe").firstName("John").lastName("Doe").build());
        userService.create(UserEntity.builder().userName("jane.doe").firstName("Jane").lastName("Doe").build());
        userService.findAll().forEach(System.out::println);
    }

    @Scheduled(fixedDelayString = "30000", initialDelayString = "10000")
    public void keepAlive(){
        System.out.println(">> Keep Alive <<");
    }
}
