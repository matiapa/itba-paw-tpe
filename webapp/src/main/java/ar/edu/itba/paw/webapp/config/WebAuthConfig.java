package ar.edu.itba.paw.webapp.config;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.itba.paw.webapp.auth.SuccessHandler;
import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan("ar.edu.itba.paw.webapp.auth")

public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    private final static String AUTH_PROPERTY_KEY = "itbahub.auth";

    @Autowired private UserDetailsServiceImpl userDetailsService;

    @Autowired private SuccessHandler successHandler;

    @Resource(name = "appProperties")
    private Properties appProps;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
           .invalidSessionUrl("/login")

        .and().authorizeRequests()
            .antMatchers("/register", "/login", "/register/verification").anonymous()
            .antMatchers("/**").authenticated()

        .and().formLogin()
            .usernameParameter("username")
            .passwordParameter("password")
            .defaultSuccessUrl("/", false)
            .loginPage("/login")
            .successHandler(successHandler)

        .and().rememberMe()
            .rememberMeParameter("rememberme")
            .userDetailsService(userDetailsService)
            .key(appProps.getProperty(AUTH_PROPERTY_KEY + ".rememberme_nonce"))
            .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))

        .and().logout()
            .logoutUrl("/signout")
            .logoutSuccessUrl("/login")

        .and().exceptionHandling()
            .accessDeniedPage("/error/403")

        .and().csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/assets/**", "/error/403");
    }

}