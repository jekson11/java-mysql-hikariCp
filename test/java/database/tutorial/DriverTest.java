package database.tutorial;

import com.mysql.cj.jdbc.Driver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class DriverTest {

    @Test
    void testDriver(){
        try {
            Driver driver = new Driver();
        }catch (SQLException exception){
            Assertions.fail(exception);
        }
    }

}
