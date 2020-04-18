package users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@Configuration
public class UsersApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "users");
        SpringApplication.run(UsersApplication.class, args);
    }
}
