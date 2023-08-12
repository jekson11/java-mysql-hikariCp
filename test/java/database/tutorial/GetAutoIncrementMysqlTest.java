package database.tutorial;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class GetAutoIncrementMysqlTest {
  
    @Test
    void testGetAutoIncrementPreparedStatement() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String email = "jek@test";
        String comment = "p";
        String sql = "INSERT INTO comments(email, comment) VALUES(?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, comment);
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()){
            System.out.println(resultSet.getInt(1));
        }

        ConnectionUtil.getDataSource().close();
        connection.close();
        preparedStatement.close();
        resultSet.close();
    }

    @Test
    void testGetAutoIncrementStatement() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String sql = "INSERT INTO comments(email, comment) VALUES('tam@test', 'p')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = statement.getGeneratedKeys();

        if (resultSet.next()){
            System.out.println(resultSet.getInt(1));
        }

        statement.close();
        resultSet.close();
        ConnectionUtil.getDataSource().close();
        connection.close();
    }
}
