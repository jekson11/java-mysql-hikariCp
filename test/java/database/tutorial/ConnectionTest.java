package database.tutorial;

import com.mysql.cj.jdbc.Driver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {


    @BeforeAll
    static void testSetUp(){
        try {
            Driver driver = new Driver();
        }catch (SQLException exception){
            Assertions.fail(exception);
        }
    }

    @Test
    void testConnection(){

        String jdbcUrl = "jdbc:mysql://localhost:3306/employee_database";
        String userName = "root";
        String password = "root";

        try{
            Connection connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Successfully connect");
            connection.close();
        }catch (SQLException exception){
            System.out.println();
            Assertions.fail(exception);
        }
    }
}
