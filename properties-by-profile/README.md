# Learn Springframework

## Overriding application.properties
You can specify a dev application.properties by creating a second application.properties file and adding the 
profile name in the file:
```file 
resources
- application.properties
- application-dev.properties
```

To use it to **override** the properties in `application.properties` you only need to set the active profile in your
default application.properties

```properties
spring.profiles.active=dev
```

#### Content of properties files
```properties
# application.properties
spring.profiles.active=dev
my.db.username=placeholder
my.db.password=placeholder
my.db.url=placeholder
```
```properties
# application-dev.properties
my.db.username=dev_username
my.db.password=dev_password
my.db.url=dev_url
```

#### Pojo
```java
public class MyDataSourceProperties {

    private String userName;
    private String password;
    private String dbUrl;
    
    // getters and setters...
}
```

#### Config class
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

#### Main class
```java
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
```

#### Output
```bash
2022-12-07T13:09:46.497+01:00  INFO 16935 --- [           main] c.e.s.p.PropertiesByProfileApplication   : The following 1 profile is active: "dev"
2022-12-07T13:09:47.140+01:00  INFO 16935 --- [           main] c.e.s.p.PropertiesByProfileApplication   : Started PropertiesByProfileApplication in 1.041 seconds (process running for 1.645)
------ Properties from application-dev.properties
dev_username
dev_password
dev_url
```

## Without setting the profile
```properties
# application.properties
spring.profiles.active=
my.db.username=placeholder
my.db.password=placeholder
my.db.url=placeholder
```

#### Output
```bash
2022-12-07T13:09:19.024+01:00  INFO 16844 --- [           main] c.e.s.p.PropertiesByProfileApplication   : No active profile set, falling back to 1 default profile: "default"
2022-12-07T13:09:19.515+01:00  INFO 16844 --- [           main] c.e.s.p.PropertiesByProfileApplication   : Started PropertiesByProfileApplication in 0.845 seconds (process running for 1.332)
------ Properties from application-dev.properties
placeholder
placeholder
placeholder
```