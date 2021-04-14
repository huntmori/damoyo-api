package com.damoyo.api.api.config.database;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactory"
    ,transactionManagerRef = "primaryTransactionManager"
    ,basePackages = {
        "com.damoyo.api.api"
    }
)
public class PrimaryDatabaseConfig 
{
    @Primary
    @Bean(name="primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource()
    {
        return DataSourceBuilder
                .create()
                .build();
    }

    @Primary
    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManager(
        EntityManagerFactoryBuilder builder,
        @Qualifier("primaryDataSource") DataSource dataSource
    )
    {
        return  builder
                    .dataSource(dataSource)
                    .packages("com.damoyo.api.api.entity")
                    .persistenceUnit("primaryUnit")
                    .build();
    }

    @Primary
    @Bean(name="primaryTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(
        @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory
    )
    {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
