package database.tutorial;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlInjectionTest {

    @Test
    void testSqlInjection() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String userName = "admin'; #";
        String password = "admin";

        String sql = "SELECT * FROM admin WHERE userName = " +
                "'"+userName+"' AND password = '"+password+"'";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()){
            System.out.println("Successes Login With " + userName);
        }else {
            System.out.println("Failed Login");
        }

        ConnectionUtil.getDataSource().close();
        statement.close();
        connection.close();
        resultSet.close();
    }


}
