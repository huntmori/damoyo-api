package com.damoyo.api.api.config.database;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource(value={"classpath:datasource.yml"})
@EnableJpaRepositories
public class PrimaryDatabaseConfig 
{
    @Primary
    @Bean(name="primaryDataSourceProperties")
    public DataSourceProperties  primaryDataSource()
    {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name="primaryDatasource")
    public DataSource primaryDataSource(
        @Qualifier("primaryDataSourceProperties") DataSourceProperties dataSourceProperties
    )
    {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
        EntityManagerFactoryBuilder builder
        ,@Qualifier("primaryDatasource") DataSource dataSource
    )
    {
        return null;
    }

    @Primary
    @Bean
    public PlatformTransactionManager primaryTransactionManager(
        @Qualifier("primaryEntityManagerFactory") EntityManagerFactory entityManagerFactory
    )
    {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
