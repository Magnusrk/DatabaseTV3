package G16;
import java.io.IOException;
import java.util.List;

import java.sql.*;
import java.util.Stack;


public class LoaderExample {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "Karen";
        String password = "";
        FootagesAndReportersLoader loader = new FootagesAndReportersLoader();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,username,password);
            List<FootageAndReporter> footagesAndReporters = loader.loadFootagesAndReporters(args[0]);

            insertFootage(connection,footagesAndReporters);
            connection.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertFootage(Connection connection, List<FootageAndReporter> footagesAndReporters) throws SQLException {
        Statement stmt = connection.createStatement();
        for (int i = 0; i < footagesAndReporters.size(); i++){
            Footage footage = footagesAndReporters.get(i).getFootage();

            String sql = "INSERT INTO footage (ID, title, date, duration) VALUES " +
                    "("+footage.getID()+","+footage.getTitle()+","+footage.getDate()+","+footage.getDuration()+")";

            Statement statement = connection.createStatement();
            statement.execute(sql);
        }

        stmt.close();

    }

    public static void insertReporters(Connection connection, List<FootageAndReporter> footagesAndReporters) throws SQLException {
        Statement stmt = connection.createStatement();
        for (int i = 0; i < footagesAndReporters.size(); i++){
            Reporter reporter = footagesAndReporters.get(i).getReporter();

            String sql = "INSERT INTO journalist (CPR, name, address, phone_number, email_addresses) VALUES " +
                    "("+reporter.getCPR()+","+reporter.getFirstName()+","+reporter.getStreetName()+","+reporter.getCountry()+")";

            Statement statement = connection.createStatement();
            statement.execute(sql);
        }

        stmt.close();

    }
}
