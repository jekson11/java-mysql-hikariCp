package database.tutorial;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class PreparedStatementTest {
/*
    PreparedStatement adalah turunan dari Statement, jadi apapun yang bisa dilakukan Statement, bisa dilakukan juga oleh PreparedStatement
    Yang membedakan PreparedStatement dari Statement adalah, PreparedStatement memiliki kemampuan untuk mengamankan input dari user sehingga aman dari serangan SQL Injection

    Berbeda dengan Statement, pada PreparedStatement, ketika pembuatannya, sudah ditentukan SQL apa yang akan kita gunakan
    Oleh karena itu, PreparedStatement biasanya digunakan untuk sekali mengirim perintah SQL, jika ingin mengirim perintah SQL lagi, kita harus membuat PreparedStatement baru
    Untuk membuat PreparedStatement, kita bisa menggunakan method prepareStatement(sql) milik Connection

    Sekarang pertanyaannya, bagaimana cara menerima input user menggunakan PreparedStatement?
    Untuk menerima input dari user, SQL yang kita buat harus diubah juga
    Input dari user, perlu kita ubah menjadi ? (tanda tanya)
    Nanti ketika pembuatan object, kita bisa subtitusi datanya menggunakan setXxx(index, value) sesuai dengan tipe datanya, misal setString(), setInt() dan lain-lain

 */
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
