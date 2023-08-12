package database.tutorial;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class PreparedStatementTest {

    @Test
    void testPreparedStatement() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String userName = "admin";
        String password = "admin";
        String sql = "SELECT * FROM admin WHERE userName = ? AND password = ? ";

        //CARA MENGGUNAKAN PreparedStatement ini bisa menggunakan semua execute
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userName);//setXxx()
        preparedStatement.setString(2, password);
        System.out.println(preparedStatement);

        //MEMASUKKAN preparedStatement ke result
        ResultSet result = preparedStatement.executeQuery();

        if (result.next()){
            System.out.println("Success Login With "+userName);
        }else {
            System.out.println("Failed Login");
        }


        ConnectionUtil.getDataSource().close();
        connection.close();
        preparedStatement.close();
        result.close();
    }
}
