package ru.job4j.cinema.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Класс описывает конфигурацию DataSource
 * @author Denis Kalchenko
 * @version 1.0
 */
@Configuration
public class DataSourceConfig {

    private final Environment environment;

    public DataSourceConfig(Environment environment) {
        this.environment = environment;
    }

    /**
     * Метод создает пул соединений с базой данных
     * @return возвращает экземпляр класса, реализующего интерфейс DataSource
     */
    @Bean
    public DataSource getDataSource() {
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(environment.getProperty("db.datasource.driver"));
        pool.setUrl(environment.getProperty("db.datasource.url"));
        pool.setUsername(environment.getProperty("db.datasource.username"));
        pool.setPassword(environment.getProperty("db.datasource.password"));
        return pool;
    }
}
