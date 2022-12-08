# Learn Springframework

## Configuration using bean

You can add Beans to Spring using a Configuration Bean. This would mean that you do not need to annotate your classes. 
To see if your class has been picked up by spring's class scanner we can look in the spring context.

#### Service class
```java
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String getGreeting() {
        return "Hello World!!";
    }
}
```

#### Controller class
```java
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


#### Configuration Bean
```java
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
```

#### Main class
```java
@SpringBootApplication
public class ConfigurationBeanApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ConfigurationBeanApplication.class, args);
        
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
2022-12-07T15:30:42.519+01:00  INFO 22697 --- [           main] l.e.s.c.ConfigurationBeanApplication     : Started ConfigurationBeanApplication in 1.538 seconds (process running for 2.012)
configurationBeanApplication: learn.example.springframework.configurationbean.ConfigurationBeanApplication$$SpringCGLIB$$0
greetingControllerImpl: learn.example.springframework.configurationbean.controllers.GreetingControllerImpl
greetingServiceImpl: learn.example.springframework.configurationbean.services.GreetingServiceImpl
myConfiguration: learn.example.springframework.configurationbean.config.MyConfiguration$$SpringCGLIB$$0
```