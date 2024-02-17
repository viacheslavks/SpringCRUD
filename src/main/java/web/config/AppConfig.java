package web.config;


import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import web.model.User;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


@Configuration
@PropertySource(value = "classpath:db.properties")
@EnableTransactionManagement

@ComponentScan(value = "web")
public class AppConfig {
    private static final Logger LOGGER = Logger.getLogger(WConfig.class.getName());
    @Autowired
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPersistenceUnitName("entityManagerFactory");
        if (em.getDataSource() == null) {
            LOGGER.log(Level.INFO, "DataSource is null. Check your database configuration.");
            throw new IllegalStateException("DataSource is null. Check your database configuration.");
        }
        em.setPackagesToScan(env.getProperty("db.entity.package"));
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        em.setJpaVendorAdapter(getJpaVendorAdapter());

        Properties properties = additionalProperties();
        if (properties == null) {
            LOGGER.log(Level.INFO, "JPA properties are null. Check your Hibernate configuration.");
            throw new IllegalStateException("JPA properties are null. Check your Hibernate configuration.");
        }
        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    public JpaVendorAdapter getJpaVendorAdapter() {
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        return adapter;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager txManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(
                entityManagerFactory().getObject());
        return jpaTransactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
