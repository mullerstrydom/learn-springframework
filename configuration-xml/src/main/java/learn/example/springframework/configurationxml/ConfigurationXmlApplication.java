package learn.example.springframework.configurationxml;

import learn.example.springframework.configurationxml.controllers.GreetingControllerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.util.Arrays;

@SpringBootApplication
@ImportResource("classpath:config.xml")
public class ConfigurationXmlApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ConfigurationXmlApplication.class, args);

        for (String beanName : Arrays.stream(ctx.getBeanDefinitionNames()).sorted().toList()) {
            String className = ctx.getBean(beanName).getClass().getName();
            if(className.startsWith("learn")){
                System.out.println(beanName + ": " + className);
            }
        }

        GreetingControllerImpl controller = ctx.getBean(GreetingControllerImpl.class);
        System.out.println(controller.test());

    }
}
