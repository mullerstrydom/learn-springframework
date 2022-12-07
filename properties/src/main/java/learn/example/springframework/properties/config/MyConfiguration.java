package learn.example.springframework.properties.config;

import learn.example.springframework.properties.datasource.MyDataSourceProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:datasource.properties")
public class MyConfiguration {

    @Bean
    MyDataSourceProperties myDataSourceProperties(@Value("${my.db.username}") String username,
                                                  @Value("${my.db.password}") String password,
                                                  @Value("${my.db.url}") String dbUrl) {
        MyDataSourceProperties myDataSourceProperties = new MyDataSourceProperties();
        myDataSourceProperties.setUserName(username);
        myDataSourceProperties.setPassword(password);
        myDataSourceProperties.setDbUrl(dbUrl);
        return myDataSourceProperties;
    }

}
