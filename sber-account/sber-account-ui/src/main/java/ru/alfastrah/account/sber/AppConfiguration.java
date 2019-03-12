package ru.alfastrah.account.sber;

import com.vaadin.spring.access.SecuredViewAccessControl;
import com.vaadin.spring.annotation.EnableVaadin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ComponentScan(basePackages = "ru.alfastrah.account.sber")
@EnableVaadin
@Configuration
public class AppConfiguration {
    /**
     * The password encoder to use when encrypting passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecuredViewAccessControl securedViewAccessControl()
    {
        return new SecuredViewAccessControl();
    }
}
