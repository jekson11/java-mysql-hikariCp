package database.tutorial;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlInjectionTest {

    /*
    Biasanya kita akan menerima input data dari user, lalu membuat perintah SQL dari input user, dan mengirimnya menggunakan perintah SQL
    SQL Injection adalah sebuah teknik yang menyalahgunakan sebuah celah keamanan yang terjadi dalam lapisan basis data sebuah aplikasi.
    Biasa, SQL Injection dilakukan dengan mengirim input dari user dengan perintah yang salah, sehingga menyebabkan hasil SQL yang kita buat menjadi tidak valid
    SQL Injection sangat berbahaya, jika sampai kita salah membuat SQL, bisa jadi data kita tidak aman

    SOLUTION?
    Statement tidak didesain untuk bisa menangani SQL Injection
    Oleh karena itu, jika SQL query yang kita gunakan dibuat berdasarkan input dari user, maka kita jangan menggunakan Statement
    Untuk menghindari SQL Injection, terdapat interface bernama PreparedStatement, ini adalah jenis statement yang bisa menangani SQL Injection dengan baik
    Kita akan bahas PreparedStatement di chapter tersendiri
     */

    @Test
    void testSqlInjection() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        //INI YANG DI NAMAKAN SQL INJECTION MENGIRIM PRINTAH YANG MEMBUAT BASIS DATA KITA MENJADI SALAH
        //INI BERHASIL LOGIN PADAHAL DATANYA SALAH
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
