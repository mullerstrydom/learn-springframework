package learn.example.springframework.propertiesautobeanpopulate;

import learn.example.springframework.propertiesautobeanpopulate.datasource.MyDataSourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PropertiesAutoBeanPopulateApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PropertiesAutoBeanPopulateApplication.class, args);

        System.out.println("------ Properties from application.properties");
        MyDataSourceProperties myDataSourceProperties = ctx.getBean(MyDataSourceProperties.class);
        System.out.println(myDataSourceProperties.getUserName());
        System.out.println(myDataSourceProperties.getPassword());
        System.out.println(myDataSourceProperties.getDbUrl());
    }
}
