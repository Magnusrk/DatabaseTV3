package G16;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.io.IOException;
import java.util.List;

import java.sql.*;

public class LoaderExample {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "Karen";
        String password = "";
        FootagesAndReportersLoader loader = new FootagesAndReportersLoader();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,username,password);
            List<FootageAndReporter> footagesAndReporters = loader.loadFootagesAndReporters(args[0], connection);


            createDatebase(connection);
            //createFootageTable(connection);

            //createjournalistTable(connection);
            insertFootage(connection,footagesAndReporters);
            insertReporters(connection,footagesAndReporters);


            connection.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createDatebase(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String use = "USE test";
        stmt.execute(use);

    }

    public static void createFootageTable(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String reset = "DROP TABLE IF EXISTS Footage";
        stmt.execute(reset);
        String sql = "CREATE TABLE Footage (ID varchar(11),title varchar(100), Date date," +
                "duration varchar(100), CPR varchar(11)," +
                "PRIMARY KEY (ID)," +
                "FOREIGN KEY (CPR) references Journalist(CPR) ON delete set null " +
                ")";
        stmt.execute(sql);
    }

    public static void createjournalistTable(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        String keyChecks = "SET FOREIGN_KEY_CHECKS=0";
        stmt.execute(keyChecks);
        String reset = "DROP TABLE IF EXISTS Journalist";
        stmt.execute(reset);
        String sql = "CREATE TABLE Journalist (CPR varchar(11),First_name varchar(100), Last_name varchar(100)," +
                "Street varchar(100), Civic_num varchar(20), ZIP_code varchar(20), County varchar(56)," +
                "Phone_number varchar(20), Email_address varchar(256)," +
                "PRIMARY KEY (CPR)" +
                ")";
        stmt.execute(sql);
    }

    public static void insertFootage(Connection connection, List<FootageAndReporter> footagesAndReporters) throws SQLException {
        Statement stmt = connection.createStatement();
        String D = "\"";
        for (int i = 0; i < footagesAndReporters.size(); i++){
            Footage footage = footagesAndReporters.get(i).getFootage();
            Reporter reporter = footagesAndReporters.get(i).getReporter();

            String sql = "INSERT INTO footage (ID, title, date, duration, CPR) VALUES " +
                    "("+D+footage.getID()+D+","+D+footage.getTitle()+D+","+footage.getDate()+","+D+footage.getDuration()+D+","+D+reporter.getCPR()+D+")";
            try {
                stmt.execute(sql);
            } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e){

            }
        }

        stmt.close();

    }

    public static void insertReporters(Connection connection, List<FootageAndReporter> footagesAndReporters) throws SQLException {
        Statement stmt = connection.createStatement();
        String D = "\"";
        for (int i = 0; i < footagesAndReporters.size(); i++){
            Reporter reporter = footagesAndReporters.get(i).getReporter();
                String sql = "INSERT INTO journalist (CPR, First_name, Last_name, Street, Civic_num, ZIP_code, County, Phone_number, Email_address) VALUES " +
                        "(" + reporter.getCPR() + "," + D + reporter.getFirstName() + D + "," + D + reporter.getLastName() + D + ","
                        + D + reporter.getStreetName() + D + "," + D + reporter.getCivicNumber() + D + "," + D + reporter.getZIPCode() + D + "," +
                        D + reporter.getCounty() + D + "," + D + reporter.getPhoneNum() + D + "," + D + reporter.getEmail() + D + ")";
                try {
                    stmt.execute(sql);
            } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e){

            }
        }

        stmt.close();

    }
}
