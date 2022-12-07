# Learn Springframework

## @ConfigurationProperties
You can specify a class to load specific properties from the properties file.

#### properties file
```properties
# application.properties
spring.profiles.active=EN
my.db.username=from_properties_user
my.db.password=from_properties_password
my.db.url=from_properties_url
```

#### Configuration class
- Using `@Configuration`, springs component scanner will pick up your class as a Bean.
- Using `@ConfigurationProperties("my.db")` spring will populate the fields in the class with the values
  from properties, but only the properties that start with `my.db.`
```java 
@Configuration
@ConfigurationProperties("my.db")
public class MyDataSourceProperties {

    private String userName;
    private String password;
    private String dbUrl;
    
    // getters and setters..
}
```

#### 
```java 
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
```

#### Output:
```bash
2022-12-07T13:21:55.289+01:00  INFO 17437 --- [           main] .p.PropertiesAutoBeanPopulateApplication : The following 1 profile is active: "EN"
2022-12-07T13:21:55.750+01:00  INFO 17437 --- [           main] .p.PropertiesAutoBeanPopulateApplication : Started PropertiesAutoBeanPopulateApplication in 0.817 seconds (process running for 1.3)
------ Properties from application.properties
from_properties_user
from_properties_password
null
```

### Take note:
You will see that the getDbUrl() printout is "null", instead of "from_properties_url". This is because the property is 
defined in the properties file as `my.db.url` and not `my.db.dbUrl`