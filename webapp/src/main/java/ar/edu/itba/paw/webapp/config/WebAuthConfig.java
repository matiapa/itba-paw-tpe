package ar.edu.itba.paw.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import ar.edu.itba.paw.webapp.auth.SgaOidcUserService;
import ar.edu.itba.paw.webapp.auth.LoginSuccessHandler;

@Configuration
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig {
    private Environment env;

    private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.google";

    public WebAuthConfig(Environment env) {
        this.env = env;
    }

    @Autowired
    private SgaOidcUserService sgaOidcUserService;

    @EnableWebSecurity
//    @PropertySource("classpath:/ar/edu/itba/paw/webapp/config/auth.properties")
    public class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
            .authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/register").hasRole("UNREGISTERED")
                .anyRequest().hasRole("USER")
            .and().logout()
                .logoutUrl("/signout")
                .logoutSuccessUrl("/login")
            .and().oauth2Login()
                .loginPage("/login").successHandler(new LoginSuccessHandler())
                .userInfoEndpoint().oidcUserService(sgaOidcUserService)
                .and()
            .and().csrf().disable()
            ;
        }

        @Override
        public void configure(final WebSecurity web) throws Exception {
            web.ignoring()
                .antMatchers("/assets/**");
        }
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(
            ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
//        String clientId = env.getRequiredProperty(CLIENT_PROPERTY_KEY + ".client-id");
//        String clientSecret = env.getRequiredProperty(CLIENT_PROPERTY_KEY + ".client-secret");
        String clientId = "991921071016-a10hrh7877uchu04bca45cdoabfh1in2.apps.googleusercontent.com";
        String clientSecret = "qrjbjfn1ZwEL-B8iE3Zs2zRW";
        ClientRegistration repository = CommonOAuth2Provider.GOOGLE.getBuilder("google").clientId(clientId).clientSecret(clientSecret).build();
        return new InMemoryClientRegistrationRepository(repository);
    }
}