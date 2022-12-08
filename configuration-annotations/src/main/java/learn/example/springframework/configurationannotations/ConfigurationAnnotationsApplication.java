package learn.example.springframework.configurationannotations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@SpringBootApplication
public class ConfigurationAnnotationsApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ConfigurationAnnotationsApplication.class, args);

        for (String beanName : Arrays.stream(ctx.getBeanDefinitionNames()).sorted().toList()) {
            String className = ctx.getBean(beanName).getClass().getName();
            if(className.startsWith("learn")){
                System.out.println(beanName + ": " + className);
            }
        }
    }

}
