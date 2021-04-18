package ar.edu.itba.paw.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
public class WebAuthConfig {
    private Environment env;

    private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.google";

    public WebAuthConfig(Environment env) {
        this.env = env;
    }

    @EnableWebSecurity
    @PropertySource("classpath:/ar/edu/itba/paw/webapp/config/auth.properties")
    public static class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .oauth2Login()
            ;
        }
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(
            ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        String clientId = env.getRequiredProperty(CLIENT_PROPERTY_KEY + ".client-id");
        String clientSecret = env.getRequiredProperty(CLIENT_PROPERTY_KEY + ".client-secret");
        ClientRegistration repository = CommonOAuth2Provider.GOOGLE.getBuilder("google").clientId(clientId).clientSecret(clientSecret).build();
        return new InMemoryClientRegistrationRepository(repository);
    }
}