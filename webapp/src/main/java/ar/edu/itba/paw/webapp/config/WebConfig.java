package ar.edu.itba.paw.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@EnableWebMvc
@ComponentScan({
        "ar.edu.itba.paw.webapp.controller",
        "ar.edu.itba.paw.services",
        "ar.edu.itba.paw.persistence"
})
@Configuration
//@PropertySource("classpath:/ar/edu/itba/paw/webapp/config/auth.properties")
public class WebConfig {
    private static String DB_PROPERTY_KEY = "itbahub.persistence.db";
    private Environment env;

    public WebConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
//        String url = env.getRequiredProperty(DB_PROPERTY_KEY + ".url");
//        String user = env.getRequiredProperty(DB_PROPERTY_KEY + ".user");
//        String password = env.getRequiredProperty(DB_PROPERTY_KEY + ".password");

        String url = "ec2-54-161-239-198.compute-1.amazonaws.com:5432/d8j7sdks62b5ck";
        String user = "rnwisaepqwgigm";
        String password = "4d6fe4204a429d92faa2a8dc671d799f3a53237da87868bb5362812473256456";

        ds.setDriverClass(org.postgresql.Driver.class);
        ds.setUrl("jdbc:postgresql://" + url);
        ds.setUsername(user);
        ds.setPassword(password);

        return ds;
    }

}