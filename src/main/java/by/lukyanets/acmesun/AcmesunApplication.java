package by.lukyanets.acmesun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/secured.properties")
@SpringBootApplication
public class AcmesunApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcmesunApplication.class, args);
    }
}
