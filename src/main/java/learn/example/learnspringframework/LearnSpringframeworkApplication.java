package learn.example.learnspringframework;

import learn.example.learnspringframework.datasource.MyDataSourceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LearnSpringframeworkApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(LearnSpringframeworkApplication.class, args);

        MyDataSourceProperties myDataSourceProperties = ctx.getBean(MyDataSourceProperties.class);
        System.out.println(myDataSourceProperties.getUserName());
        System.out.println(myDataSourceProperties.getPassword());
        System.out.println(myDataSourceProperties.getDbUrl());
    }

}
