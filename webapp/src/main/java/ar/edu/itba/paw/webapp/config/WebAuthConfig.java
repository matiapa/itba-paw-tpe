package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.SuccessHandler;
import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")

public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    private final static String AUTH_PROPERTY_KEY = "itbahub.auth";

    @Autowired private UserDetailsServiceImpl userDetailsService;

    @Autowired private SuccessHandler successHandler;

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
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "auth.properties";

        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        http.sessionManagement()
           .invalidSessionUrl("/login")

        .and().authorizeRequests()
            .antMatchers("/register", "/login", "/error/**", "/register/verification").anonymous()
            .antMatchers("/admin/statistics").hasAuthority("STATISTIC.READ")
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