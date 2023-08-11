package database.tutorial;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class BatchProcessTest {
    /*
    Batch process adalah proses mengirim perintah secara banyak sekaligus.
    Biasanya batch process dilakukan dalam kasus tertentu saja, misal ketika kita ingin mengirim import data dari file excel ke database yang jumlahnya jutaan.
    Biasanya dalam batch process, yang diutamakan adalah kecepatan, karena jika perintah SQL nya di execute satu satu dan menunggu response satu satu, maka sudah pasti akan sangat lambat sekali

    JDBC mendukung proses eksekusi perintah SQL secara batch di Statement ataupun di PreparedStatement
    Di Statement, terdapat method addBatch(sql) untuk menambahkan perintah ke proses batch
    Sedangkan di PreparedStatement terdapat method addBatch() untuk menambahkan proses ke batch, lalu bisa gunakan method clearParameters() untuk menghapus parameter input user sebelumnya.
    Setelah proses batch selesai, untuk mengeksekusinya, kita bisa gunakan perintah executeBatch()

    PERINGAA!!!
    Proses batch akan disimpan di memory sebelum dikirim ke database
    Oleh karena itu, bijaklah membuat proses batch, jangan terlalu banyak menambahkan ke batch, misal per 100 atau per 1000
    Jika sudah mencapai 100 atau 1000, kita bisa mengirim batch tersebut menggunakan perintah executeBatch()

     */

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
