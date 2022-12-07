package learn.example.springframework.propertiesbyprofile;

import learn.example.springframework.propertiesbyprofile.datasource.MyDataSourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PropertiesByProfileApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PropertiesByProfileApplication.class, args);

        System.out.println("------ Properties from application-dev.properties");
        MyDataSourceProperties myDataSourceProperties = ctx.getBean(MyDataSourceProperties.class);
        System.out.println(myDataSourceProperties.getUserName());
        System.out.println(myDataSourceProperties.getPassword());
        System.out.println(myDataSourceProperties.getDbUrl());
    }
}
