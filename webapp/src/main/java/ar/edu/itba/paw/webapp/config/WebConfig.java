package ar.edu.itba.paw.webapp.config;

import java.nio.charset.StandardCharsets;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Properties;

import java.io.FileInputStream;
import java.io.IOException;

@EnableAsync
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan({
        "ar.edu.itba.paw.webapp.controller",
        "ar.edu.itba.paw.services",
        "ar.edu.itba.paw.persistence"
})
@Configuration
public class WebConfig {

    private final static String DB_PROPERTY_KEY = "itbahub.persistence.db";

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
    public DataSource dataSource() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "auth.properties";

        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        String url = appProps.getProperty(DB_PROPERTY_KEY + ".url");
        String user = appProps.getProperty(DB_PROPERTY_KEY + ".user");
        String password = appProps.getProperty(DB_PROPERTY_KEY + ".password");

        ds.setDriverClass(org.postgresql.Driver.class);
        ds.setUrl("jdbc:postgresql://" + url);
        ds.setUsername(user);
        ds.setPassword(password);

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IOException {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan("ar.edu.itba.paw.models");
        factoryBean.setDataSource(dataSource());

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendorAdapter);

        final Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");

        // TODO: Remove this for production
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("format_sql", "true");

        factoryBean.setJpaProperties(properties);

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public MessageSource messageSource(){
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        messageSource.setCacheSeconds(5);
        return messageSource;
    }
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }


    @Bean
    public JavaMailSender getJavaMailSender() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "auth.properties";

        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(appProps.getProperty("itbahub.mailer.email"));
        mailSender.setPassword(appProps.getProperty("itbahub.mailer.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.debug","true");

        return mailSender;
    }

    @Bean
    public ITemplateResolver thymeleafTemplateResolver(){
        ClassLoaderTemplateResolver templateResolver= new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("mail-templates/");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine(ITemplateResolver templateResolver){
        SpringTemplateEngine templateEngine= new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    @Bean
    public ResourceBundleMessageSource emailMessageSource(){
        ResourceBundleMessageSource messageSource= new ResourceBundleMessageSource();
        messageSource.setBasename("mailMessages");
        return messageSource;
    }

}