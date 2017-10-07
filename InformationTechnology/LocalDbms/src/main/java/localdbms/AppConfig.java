package localdbms;

import localdbms.model.User;
import localdbms.model.UserFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    public Bootstrap bootstrap() {
        return new Bootstrap();
    }

    @Bean
    @Scope("prototype")
    public User user() {
        return new User();
    }

    @Bean
    public UserFactory userFactory() {
        return this::user;
    }

}
