package learn.example.springframework.propertiesconstructorbinding.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("my.db")
public class ConstructorBindingConfig {
    private final String username;
    private final String password;
    private final String url;

    @ConstructorBinding
    public ConstructorBindingConfig(String username, String password, String url) {
        System.out.println("---------Initialize ConstructorBindingConfig");
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }
}
