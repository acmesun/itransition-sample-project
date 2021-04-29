package by.lukyanets.acmesun.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Configuration
@PropertySource("classpath:/cloudinary.properties")
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(
            @Value("${cloudinary.name}") String cloudName,
            @Value("${cloudinary.key}") String cloudKey,
            @Value("${cloudinary.secret}") String cloudSecret
    ) {
        return new Cloudinary(Map.of(
                "cloud_name", cloudName,
                "api_key", cloudKey,
                "api_secret", cloudSecret
        ));
    }
}
