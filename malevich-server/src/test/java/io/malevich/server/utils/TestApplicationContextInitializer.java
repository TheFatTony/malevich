package io.malevich.server.utils;

import io.malevich.server.AppTest;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

public class TestApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {


    public void initialize(ConfigurableApplicationContext applicationContext) {
        DataSource dataSource = DataSourceBuilder
                .create()
                .username("root")
                .password(AppTest.mysql.getPassword())
                .url(AppTest.mysql.getJdbcUrl() + "?useSSL=false")
                .driverClassName("com.mysql.jdbc.Driver")
                .build();

        try {
            TestUtils.initFunction(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        TestPropertyValues values = TestPropertyValues.of(
                "spring.datasource.url=" + AppTest.mysql.getJdbcUrl() + "?useSSL=false",
                "spring.datasource.username=" + AppTest.mysql.getUsername(),
                "spring.datasource.password=" + AppTest.mysql.getPassword(),
                "malevich.composer.url=http://localhost:3000/api",
                "malevich.client.url=http://localhost:4200",
                "spring.datasource.driver-class-name=com.mysql.jdbc.Driver",
                "spring.datasource.type = org.apache.tomcat.jdbc.pool.DataSource",
                "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect",
                "spring.jpa.properties.hibernate.id.new_generator_mappings=false");

        values.applyTo(applicationContext);
    }


}
