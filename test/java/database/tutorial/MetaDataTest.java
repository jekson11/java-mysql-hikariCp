package database.tutorial;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class MetaDataTest {
  

    @Test
    void testDatabaseMetaData() throws SQLException {
     
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        //CARA PENGGUNAN DATABASEMEETADATA
        DatabaseMetaData databaseMetaData = connection.getMetaData();

        //MENDAPATKAN NAMA DATABASE
        System.out.println(databaseMetaData.getDatabaseProductName());
        //MENDAPATKAN VERSI DATABASE
        System.out.println(databaseMetaData.getDatabaseProductVersion());

        //MENDAPATKAN INFORMASI MENGENAI TABLE YANG ADA DI DATABASE
        ResultSet resultSet = databaseMetaData.getTables("employee_database",null, null, null );
        //MENGGUNAKAN PERULANGAN UNTUK MENITERASI SEMUA TABLE YANG ADA
        while (resultSet.next()){
            System.out.println(resultSet.getString("TABLE_NAME"));
        }

        ConnectionUtil.getDataSource().close();
        connection.close();
    }

    @Test
    void testParameterMetaData() throws SQLException {
     

        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String sql = "INSERT INTO comments(email, comment) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,"jeks@test");
        preparedStatement.setString(2, "test12");
        preparedStatement.executeUpdate();

        //CARA MENGGUNAKAN PARAMETER METADATA
        ParameterMetaData parameterMetaData = preparedStatement.getParameterMetaData();
        //MELIHAT BERAPA JUMLAH PARAMETERNYA
        System.out.println(parameterMetaData.getParameterCount());
//        System.out.println(parameterMetaData.getParameterType(1)); //INI MEMEANG BELUM SUPORT
        System.out.println(parameterMetaData.getParameterMode(1));
        System.out.println(parameterMetaData.getParameterMode(2));

        preparedStatement.close();
        ConnectionUtil.getDataSource().close();
        connection.close();
    }
    
    @Test
    void testResultMetaData() throws SQLException {
      
        Connection connection = ConnectionUtil.getDataSource().getConnection();
        String sql = "SELECT * FROM sample_date";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        //GETCOLUMNCOUNT ITU JUMLAH COLUMN
        for (int i = 1; i < resultSetMetaData.getColumnCount(); i++) {
            System.out.println("Name: " + resultSetMetaData.getColumnName(i));
            System.out.println("Type: " + resultSetMetaData.getColumnType(i));
            System.out.println("Type Name: " + resultSetMetaData.getColumnTypeName(i));
            System.out.println("Database Name: " + resultSetMetaData.getCatalogName(i));
        }
        
        statement.close();
        resultSet.close();
        connection.close();
    }
}
