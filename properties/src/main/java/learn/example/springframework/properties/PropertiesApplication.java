package learn.example.springframework.properties;

import learn.example.springframework.properties.datasource.MyDataSourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PropertiesApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PropertiesApplication.class, args);

        System.out.println("------ Properties from datasource.properties");
        MyDataSourceProperties myDataSourceProperties = ctx.getBean(MyDataSourceProperties.class);
        System.out.println(myDataSourceProperties.getUserName());
        System.out.println(myDataSourceProperties.getPassword());
        System.out.println(myDataSourceProperties.getDbUrl());
    }

}
