package com.johndobie.springboot.testing.cheatsheet.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FlywayMigrationMockMvcTest {
    
    private static final String QUERY = "SELECT COUNT(*) FROM cheatsheet.message";
    
    @Autowired
    private DataSource dataSource;

    @Test
    public void testFlywayMigrations() throws Exception {
        try (Connection connection = dataSource.getConnection();    Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY)) {
                assertThat(resultSet.next()).isTrue();
                int count = resultSet.getInt(1);
                assertThat(count).isEqualTo(5);
        }
    }
}