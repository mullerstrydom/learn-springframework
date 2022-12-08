# Learn Springframework

## Configuration using xml

You can add Beans to Spring using a config xml file. This would mean that you do not need to annotate your classes. 
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


#### Configuration Xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean name="greetingServiceImpl"
          class="learn.example.springframework.configurationxml.services.GreetingServiceImpl"/>
    <bean name="greetingControllerImpl"
          class="learn.example.springframework.configurationxml.controllers.GreetingControllerImpl">
        <constructor-arg index="0" type="learn.example.springframework.configurationxml.services.GreetingService"
                         ref="greetingServiceImpl"/>
    </bean>

</beans>
```

#### Main class
Specify the resource as annotation in main class
```java
@SpringBootApplication
@ImportResource("classpath:config.xml")
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
2022-12-08T10:59:05.743+01:00  INFO 26931 --- [           main] l.e.s.c.ConfigurationXmlApplication      : Started ConfigurationXmlApplication in 1.597 seconds (process running for 2.108)
configurationXmlApplication: learn.example.springframework.configurationxml.ConfigurationXmlApplication$$SpringCGLIB$$0
greetingControllerImpl: controllers.learn.example.springframework.configurationxml.GreetingControllerImpl
greetingServiceImpl: services.learn.example.springframework.configurationxml.GreetingServiceImpl
```