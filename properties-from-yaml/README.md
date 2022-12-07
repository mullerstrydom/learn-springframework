# Learn Springframework

## Yaml properties
Instead of the `application.properties` you can have a `application.yaml` file to define your properties.
No additional changes need to be made, just replace the properties file with the yaml file:

### Define Properties
```yaml
# application.yaml
spring:
  profiles:
    active: EN
my:
  db:
    username: yml_user
    password: yml_password
    url: yml_url
```

### Configuration class: 
```java
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
```

### Main class:
```java 
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
```

### Ouput
```bash
2022-12-07T11:39:29.295+01:00  INFO 14850 --- [           main] l.e.s.p.PropertiesFromYamlApplication    : The following 1 profile is active: "EN"
2022-12-07T11:39:29.815+01:00  INFO 14850 --- [           main] l.e.s.p.PropertiesFromYamlApplication    : Started PropertiesFromYamlApplication in 1.094 seconds (process running for 2.249)
------ Properties from application.yaml
yml_user
yml_password
yml_url
```