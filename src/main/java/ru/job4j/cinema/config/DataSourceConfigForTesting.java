package ru.job4j.cinema.config;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.cinema.Main;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;

public class DataSourceConfigForTesting {

    private Properties loadDbProperties() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(Main.class.getClassLoader()
                                .getResourceAsStream("application.properties"))
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("db.test.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return cfg;
    }

    public DataSource getDataSource() {
        Properties cfg = loadDbProperties();
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(cfg.getProperty("db.test.driver"));
        pool.setUrl(cfg.getProperty("db.test.url"));
        pool.setUsername(cfg.getProperty("db.test.username"));
        pool.setPassword(cfg.getProperty("db.test.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        return pool;
    }
}
