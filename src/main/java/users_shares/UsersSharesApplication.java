package users_shares;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableCaching
@Configuration
public class UsersSharesApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "users_shares");
        SpringApplication.run(UsersSharesApplication.class, args);
    }
}
