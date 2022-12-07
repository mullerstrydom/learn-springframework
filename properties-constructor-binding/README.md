# Learn Springframework

## Constructor Binding properties
Instead of using setters in java, you can set final properties using the constructor and load it as a bean in spring 
using the @EnableConfigurationProperties

#### Config class
Important here is the @EnableConfigurationProperties. This will allow that the `ConstructorBeanConfig` is loaded as a 
bean in Spring and initialized using the constructor containing all properties
```java
@Configuration
@EnableConfigurationProperties(ConstructorBindingConfig.class)
public class MyConfiguration {

}
```

#### Class for properties - to be set using constructor
```java
@ConfigurationProperties("my.db")
public class ConstructorBindingConfig {
    private final String username;
    private final String password;
    private final String url;

    @ConstructorBinding
    public ConstructorBindingConfig(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }
    
    // only getters...
}
```

#### Main class
```java
@SpringBootApplication
public class PropertiesConstructorBindingApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PropertiesConstructorBindingApplication.class, args);

        ConstructorBindingConfig constructorBindingConfig = ctx.getBean(ConstructorBindingConfig.class);
        System.out.println(constructorBindingConfig.getUsername());
        System.out.println(constructorBindingConfig.getPassword());
        System.out.println(constructorBindingConfig.getUrl());
    }
}
```

#### Output
```bash
2022-12-07T13:56:58.502+01:00  INFO 19173 --- [           main] .PropertiesConstructorBindingApplication : No active profile set, falling back to 1 default profile: "default"
2022-12-07T13:56:59.114+01:00  INFO 19173 --- [           main] .PropertiesConstructorBindingApplication : Started PropertiesConstructorBindingApplication in 1.009 seconds (process running for 1.524)
test_user
test_password
test_url
```