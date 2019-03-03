package imporven.activiti.Rudiments.hello;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseTest {
    public static void main(String[] args) {
        try {
            Driver driver = new Driver();
            Properties properties = new Properties();
            properties.put("user","root");
            properties.put("password","rootroot");
            Connection connect = driver.connect("jdbc:mysql://127.0.0.1:3306/activiti6ui?characterEncoding=UTF-8", properties);
            System.out.println(connect);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
