package learn.example.springframework.propertiesbyprofile.config;

import learn.example.springframework.propertiesbyprofile.datasource.MyDataSourceProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
