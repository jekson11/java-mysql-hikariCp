package database.tutorial;

import org.junit.jupiter.api.Test;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;

public class DateTimeAndTimStampTest {

    @Test
    void testDateTimeSAndTimeStamp() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String sql = """
                INSERT INTO sample_date(sample_date, sample_time, sample_timestamp)
                VALUES(?, ?, ?)
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDate(1, new Date(System.currentTimeMillis()));
        preparedStatement.setTime(2, new Time(System.currentTimeMillis()));
        preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

        System.out.println(preparedStatement.executeUpdate());

        ConnectionUtil.getDataSource().close();
        connection.close();
        preparedStatement.close();

    }

    @Test
    void testDateTimeSAndTimeStamp2() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String sql = """
               SELECT * FROM sample_date
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            System.out.println("id: " + resultSet.getString("id"));
            System.out.println("Date: " + resultSet.getString("sample_date"));
            System.out.println("Time: " + resultSet.getString("sample_Time"));
            System.out.println("TimeStamp: " + resultSet.getString("Sample_timeStamp"));
        }

        resultSet.close();
        ConnectionUtil.getDataSource().close();
        connection.close();
        preparedStatement.close();

    }

    @Test
    void testDateTimeSAndTimeStampStatement() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String sql = """
               SELECT * FROM sample_date
                """;

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            System.out.println("id: " + resultSet.getString("id"));
            System.out.println("Date: " + resultSet.getString("sample_date"));
            System.out.println("Time: " + resultSet.getString("sample_Time"));
            System.out.println("TimeStamp: " + resultSet.getString("Sample_timeStamp"));
        }

        resultSet.close();
        ConnectionUtil.getDataSource().close();
        connection.close();
        statement.close();

    }


    @Test
    void testDateTimeSAndTimeStampStatementWithCalendar() throws SQLException {
        Connection connection = ConnectionUtil.getDataSource().getConnection();

        String sql = """
                INSERT INTO sample_date(sample_date, sample_time, sample_timestamp)
                VALUES(?, ?, ?)
                """;

        //BEGINI KALAU MAU TIMENYE DARI CALENDAR
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,2003);
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DATE, 11);
        calendar.set(Calendar.HOUR_OF_DAY,12);
        calendar.set(Calendar.MINUTE,12);
        calendar.set(Calendar.SECOND,12);

        java.util.Date currentDate = calendar.getTime();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDate(1, new Date(currentDate.getTime()));
        preparedStatement.setTime(2, new Time(currentDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(currentDate.getTime()));

        System.out.println(preparedStatement.executeUpdate());

        ConnectionUtil.getDataSource().close();
        connection.close();
        preparedStatement.close();

    }
}
