package ar.edu.itba.paw.webapp.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

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
//    private Environment env;

//    public WebConfig(Environment env) {
//        this.env = env;
//    }

    public WebConfig(){}

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

        String url = "localhost:5432/paw-2021a-13";
        String user = "paw-2021a-13";
        String password = "aPjzv76sH";

        ds.setDriverClass(org.postgresql.Driver.class);
        ds.setUrl("jdbc:postgresql://" + url);
        ds.setUsername(user);
        ds.setPassword(password);

        return ds;
    }

    @Bean
    public MessageSource messageSource(){
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        messageSource.setCacheSeconds(5);
        return messageSource;
    }
}