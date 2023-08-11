package database.tutorial;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class GetAutoIncrementMysqlTest {
    /*
    Kadang setelah melakukan INSERT data ke database yang memiliki primary key auto increment, kita ingin mendapatkan data id terbarunya
    di JDBC, ada kemampuan untuk mendapatkan auto generate data seperti auto increment dengan method getGenerateKeys() yang mengembalikan ResultSet
    Selanjutnya kita bisa melakukan iterasi terhadap ResultSet tersebut

    Secara default, Statement ataupun PreparedStatement tidak mengerti untuk mengambil auto generate key
    Kita perlu memberi tahunya agar Statement ataupun PreparedStatement mengambil auto generate id secara otomatis
    Untuk Statement, kita perlu memberi tahu ketika memanggil method executeUpdate(sql, Statement.RETURN_GENERATED_KEYS)
    Sedangkan untuk PreparedStatement, kita perlu memberi tahu ketika membuat prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
    Setelah itu, untuk mendapatkan auto generate key, kita bisa menggunakan method getGeneratedKeys(), method ini akan error jika kita lupa mengirim parameter generated keys

     */

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
