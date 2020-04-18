package sharedseries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableCaching
@Configuration
public class SharedseriesApplication {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "sharedseries");
        SpringApplication.run(SharedseriesApplication.class, args);
    }
}
