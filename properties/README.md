# Learn Springframework
You can specify properties using
- properties file          (ie. my.db.username=property_username)
- environment variables    (ie. MY_DB_USERNAME=env_var_username)
- cmd line arguments       (ie. --my.db.username=cmdline_arg_username)

Properties file is overridden by environment variables.
Environment variables is overridden by cmd line arguments.

Use the `@Value` annotation to get the value from properties
Remember the `{}` for the syntax for `@Value`, ie 
```java 
@Value("${my.db.username}") String username
``` 

## Properties file
Use the annotation `@PropertySource("classpath:datasource.properties")` to define the properties file

#### properties file
```properties
# datasource.properties
my.db.username=special_properties_read_user
my.db.password=special_properties_read_password
my.db.url=http://special_properties.url/
```

#### Config class
```java
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
```

#### Main class
```java
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
```

#### Output
```bash
2022-12-07T12:53:15.154+01:00  INFO 16205 --- [           main] c.e.s.properties.PropertiesApplication   : No active profile set, falling back to 1 default profile: "default"
2022-12-07T12:53:15.655+01:00  INFO 16205 --- [           main] c.e.s.properties.PropertiesApplication   : Started PropertiesApplication in 0.941 seconds (process running for 1.846)
------ Properties from datasource.properties
special_properties_read_user
special_properties_read_password
http://special_properties.url/
```

### application.properties
The application.properties is automatically loaded by spring. So if you define your properties in here, you do not 
have to specify the `@PropertySource`.

## Environment variables
The naming convention for env variables is all uppercase and `.` is replaced with `_`, ie:
```
# property file
db.username=

# env variable
export DB_USERNAME=
```

## Commandline arguments
Commandline arguments are prefixed with `--`
```
# property file
db.username=

# commandline arguments
--db.username=
```