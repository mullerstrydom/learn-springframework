package learn.example.springframework.propertiesconstructorbinding;

import learn.example.springframework.propertiesconstructorbinding.config.ConstructorBindingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PropertiesConstructorBindingApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PropertiesConstructorBindingApplication.class, args);

        System.out.println("-----Instance 1");
        ConstructorBindingConfig constructorBindingConfig = ctx.getBean(ConstructorBindingConfig.class);
        System.out.println(constructorBindingConfig.getUsername());
        System.out.println(constructorBindingConfig.getPassword());
        System.out.println(constructorBindingConfig.getUrl());

        System.out.println("-----Instance 2");
        ConstructorBindingConfig constructorBindingConfig1 = ctx.getBean(ConstructorBindingConfig.class);
        System.out.println(constructorBindingConfig1.getUsername());
        System.out.println(constructorBindingConfig1.getPassword());
        System.out.println(constructorBindingConfig1.getUrl());
    }

}
