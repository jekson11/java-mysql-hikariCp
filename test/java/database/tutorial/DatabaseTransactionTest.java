package database.tutorial;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseTransactionTest {

    @Test
    void testSetAutoCommit() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        //KALAU INI FALSE MAKA DATA YANG KITA MASUKKAN NGAK AKAN DI SIMPAN SEBELUM KITA COMMIT
        connection.setAutoCommit(false);
        String sql = "INSERT INTO comments(email, comment) VALUES(?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 1; i < 30; i++) {
            preparedStatement.clearParameters();
            preparedStatement.setString(1, "jek@test");
            preparedStatement.setString(2, "test");
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();

        preparedStatement.close();
        //AKAN NGEROLLBACK KE AWAL DATA YANG KITA MASUKKAN
        //INI AKAN ERROR KALAU setAutoCommitnya true
        connection.rollback();
        connection.commit();
        connection.close();
        ConnectionUtil.getDataSource().close();
    }
}
