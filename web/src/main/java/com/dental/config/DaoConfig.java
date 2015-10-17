package com.dental.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:datasource.properties"})
public class DaoConfig {

  @Autowired
  private Environment env;

  @Bean
  public DataSource dataSource() {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    try {
      dataSource.setJdbcUrl(env.getRequiredProperty(DbConfig.DB_URL));
      dataSource.setUser(env.getRequiredProperty(DbConfig.DB_USERNAME));
      dataSource.setPassword(env.getRequiredProperty(DbConfig.DB_PASSWORD));
      dataSource.setDriverClass(env.getRequiredProperty(DbConfig.DB_DRIVER_CLASSNAME));
//      dataSource.setConnectionCustomizerClassName(JDBCConnectionCustomizer.class.getName());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(DbConfig.PACKAGES_SCAN));
    entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapater());
    entityManagerFactoryBean.setJpaProperties(hibernateProperties());
    return entityManagerFactoryBean;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapater() {
    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setDatabase(Database.valueOf(env.getRequiredProperty(DbConfig.DB_ENGINE)));
    adapter.setShowSql(env.getRequiredProperty(DbConfig.HIBERNATE_SHOW_SQL, Boolean.class));
    return adapter;
  }

  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put(DbConfig.HIBERNATE_JDBC_BATCH_SIZE, env.getProperty(DbConfig.HIBERNATE_JDBC_BATCH_SIZE));
    properties.put(DbConfig.HIBERNATE_DIALECT, env.getRequiredProperty(DbConfig.HIBERNATE_DIALECT));
    properties.put(DbConfig.HIBERNATE_SHOW_SQL, env.getRequiredProperty(DbConfig.HIBERNATE_SHOW_SQL));
    return properties;
  }

  @Bean
  public JpaTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return transactionManager;
  }

  private interface DbConfig {
    String DB_DRIVER_CLASSNAME = "db.driverClassName";
    String DB_URL = "db.url";
    String DB_USERNAME = "db.username";
    String DB_PASSWORD = "db.password";
    String HIBERNATE_DIALECT = "hibernate.dialect";
    String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    String HIBERNATE_JDBC_BATCH_SIZE = "hibernate.jdbc.batch_size";
    String PACKAGES_SCAN = "packages.scan";
    String DB_ENGINE = "db.engine";
  }
}
