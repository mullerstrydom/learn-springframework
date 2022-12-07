package learn.example.springframework.propertiesfromyaml;

import learn.example.springframework.propertiesfromyaml.datasource.MyDataSourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PropertiesFromYamlApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PropertiesFromYamlApplication.class, args);

        System.out.println("------ Properties from application.yaml");
        MyDataSourceProperties myDataSourceProperties = ctx.getBean(MyDataSourceProperties.class);
        System.out.println(myDataSourceProperties.getUserName());
        System.out.println(myDataSourceProperties.getPassword());
        System.out.println(myDataSourceProperties.getDbUrl());
    }

}
