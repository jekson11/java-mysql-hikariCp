package database.tutorial;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class BatchProcessTest {
   
    @Test
    void testBatchProcessStatement() throws SQLException {

        Connection connection = ConnectionUtil.getDataSource().getConnection();
        String sql = "INSERT INTO comments(email, comment) VALUES('jek@', 'p')";
        Statement statement = connection.createStatement();

        for (int i = 1; i < 1000; i++) {
            statement.addBatch(sql);
        }
        statement.executeBatch();

        statement.close();
        ConnectionUtil.getDataSource().close();
        connection.close();
    }


    @Test
    void testBatchProcessStatementPrepared() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String sql = "INSERT INTO comments(email, comment) VALUES(?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (int i = 1; i < 10; i++) {
            //KALAU MENGGUNAKAN PREPARED STATEMENT KITA HARU MENGGUNAKAN METHOD INI
            preparedStatement.clearParameters();
            preparedStatement.setString(1, "jek@test");
            preparedStatement.setString(2, "p");
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();

        preparedStatement.close();
        connection.close();
        ConnectionUtil.getDataSource().close();
    }

    @Test
    void testExecuteDelete() throws SQLException {

        Connection connection = ConnectionUtil.getDataSource().getConnection();
        String sql = "DELETE FROM comments";
        Statement statement = connection.createStatement();

        System.out.println(statement.executeUpdate(sql));

        statement.close();
        ConnectionUtil.getDataSource().close();
        connection.close();
    }
}
