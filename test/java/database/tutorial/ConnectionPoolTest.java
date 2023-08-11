package database.tutorial;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPoolTest {

    @Test
    void testHikariCp(){

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/employee_database");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("root");

        //configuration pool
        hikariConfig.setMaximumPoolSize(10);//maximum request for accept
        hikariConfig.setMinimumIdle(5);//minimum request yang di simpan when request yang masuk dikit
        hikariConfig.setIdleTimeout(60_000);//selama 60 detik koneksinya sepi maka akan di close kecuali yang 5
        hikariConfig.setMaxLifetime(10 * 60_000);//jika ada connection yang terbuka selama 10 * 60 detik maka akan di tutup

        //create connection pool
        try{
            HikariDataSource dataSource = new HikariDataSource(hikariConfig);
            Connection connection = dataSource.getConnection();
            System.out.println("Success connection");

            dataSource.close();
            connection.close();
            System.out.println("Connection close");
        }catch (SQLException ex){
            Assertions.fail(ex);
        }
    }

    @Test
    void testUtil() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        System.out.println("Success Connect");

        ConnectionUtil.getDataSource().close();
        connection.close();
        System.out.println("Connection Close");
    }

    @Test
    void testStatementExecuteUpdate() throws SQLException{
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        System.out.println("Success Connect");

        String sql = """
                    INSERT INTO employee(id, name, email)
                    VALUES ('luffy', 'Luffy', 'luffy@test'),
                           ('ichigo', 'Ichigo', 'ichigo@test')
                """;

        //untuk mengirim pritah sql
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

        ConnectionUtil.getDataSource().close();
        connection.close();
        System.out.println("Connection Close");

    }

    @Test
    void testStatementExecuteQuery()throws SQLException{
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        System.out.println("Success Connect");

        String sql = "SELECT * FROM employee";

        //CARA MENGAMBIL DATA DARI DATABASE
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);

        //MENGGUNAKAN PERULANGAN UNTUK MENGELUARKAND DATA
        while (result.next()){
            String id = result.getString("id");
            String name = result.getString("name");
            String email = result.getString("email");

            //STRING JOIN BIAR RAPI
            System.out.println(
                    String.join(", ", id, name, email)
            );
        }

        ConnectionUtil.getDataSource().close();
        connection.close();
        result.close();
        System.out.println("Close Connection");
    }


}
