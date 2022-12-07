package learn.example.springframework.propertiesconstructorbinding.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ConstructorBindingConfig.class)
public class MyConfiguration {

}
