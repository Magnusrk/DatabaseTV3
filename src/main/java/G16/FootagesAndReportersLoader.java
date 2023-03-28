package G16;

/**
 * This class handles loading from a CSV data file.
 *
 * @author Giovanni Meroni
 * @author Thorbjørn Konstantinovitz
 *
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FootagesAndReportersLoader {

    public static final String SEMICOLON_DELIMITER = ";";
    public static final String COMMA_DELIMITER = ",";
    private static final int NUMBER_OF_FIELDS_EXPECTED = 10;
    private final String delimiter = SEMICOLON_DELIMITER;
    //int id = 0;

    SimpleDateFormat dateParser = new SimpleDateFormat("yyyyMMdd");

    public List<FootageAndReporter> loadFootagesAndReporters(String filename, Connection connection) throws FileNotFoundException, IOException, SQLException {
        List<FootageAndReporter> farList = new ArrayList<FootageAndReporter>();

        Statement stmt = connection.createStatement();

        String getID = "select * from getlastid";
        ResultSet rs = stmt.executeQuery(getID);
        rs.next();
        int id = rs.getInt(1);

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filename));

            String line;
            int lineNbr = 0;
            line = in.readLine();
            while ((line = in.readLine()) != null) {
                lineNbr++;
                List<String> values = new ArrayList<String>();
                try (Scanner rowScanner = new Scanner(line)) {
                    rowScanner.useDelimiter(delimiter);
                    while (rowScanner.hasNext()) {
                        values.add(rowScanner.next());
                    }
                    if(values.size() == 0)
                        continue;
                    if(values.size() == NUMBER_OF_FIELDS_EXPECTED) {

                        id++;
                        Integer ID = id;
                        String title = values.get(0);
                        Integer date = Integer.valueOf(values.get(1));
                        Integer duration = Integer.valueOf(values.get(2));
                        Integer cpr = Integer.valueOf(values.get(3));
                        String firstName = values.get(4);
                        String lastName = values.get(5);
                        String streetName = values.get(6);
                        Integer civicNumber = Integer.valueOf(values.get(7));
                        Integer zipCode  = Integer.valueOf(values.get(8));
                        String county = values.get(9);
                        int phoneNum = 0;
                        String email = null;
                        FootageAndReporter far = new FootageAndReporter(ID,title, date, duration, cpr, firstName, lastName, streetName, civicNumber, zipCode, county, phoneNum, email);
                        farList.add(far);
                    } else
                        throw new IOException("Invalid number of values on line " +lineNbr +". expected " +NUMBER_OF_FIELDS_EXPECTED +" values, found " +values.size());
                }
            }
        } finally {
            if(in != null)
                try { in.close(); } catch(Exception e) { /* Ignore */ };
        }

        return farList;
    }
}