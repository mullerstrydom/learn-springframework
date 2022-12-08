# Learn Springframework

## Configuration using annotations

You can add Beans to Spring using annotations such as `@Service`, `@Component`, `@Controller` etc. 
To see if your class has been picked up by spring's class scanner we can look in the context.

#### Service class
```java
@Service
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String getGreeting() {
        return "Hello World!!";
    }
}
```

#### Controller class
```java
@Controller
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
}
```

#### Main class
```java
@SpringBootApplication
public class ConfigurationAnnotationsApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ConfigurationAnnotationsApplication.class, args);

        for (String beanName : Arrays.stream(ctx.getBeanDefinitionNames()).sorted().toList()) {
            String className = ctx.getBean(beanName).getClass().getName();
            if(className.startsWith("learn")){ // to make sure we only look at our apps beans and not the internal spring beans
                System.out.println(beanName + ": " + className);
            }
        }
    }
}
```

#### Output
As you can see below is our instance of greetingControllerImpl, greetingServiceImpl and the main class configurationAnnotationsApplication 
```bash
2022-12-07T15:19:20.727+01:00  INFO 21817 --- [           main] .s.c.ConfigurationAnnotationsApplication : Started ConfigurationAnnotationsApplication in 1.564 seconds (process running for 2.074)
configurationAnnotationsApplication: learn.example.springframework.configurationannotations.ConfigurationAnnotationsApplication$$SpringCGLIB$$0
greetingControllerImpl: learn.example.springframework.configurationannotations.controllers.GreetingControllerImpl
greetingServiceImpl: learn.example.springframework.configurationannotations.services.GreetingServiceImpl
```