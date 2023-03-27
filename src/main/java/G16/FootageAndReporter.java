package G16;
import java.util.Date;

public class FootageAndReporter {
    private final Reporter reporter;
    private final Footage footage;

    public FootageAndReporter(int id, String title, int date, Integer duration, Integer cpr, String firstName, String lastName, String streetName, Integer civicNumber, Integer zipCode, String county, int phoneNum, String email) {
        reporter = new Reporter(cpr, firstName, lastName, streetName, civicNumber, zipCode, county, phoneNum, email);
        footage = new Footage(id, title, date, duration, cpr);
    }

    public Reporter getReporter() {
        return reporter;
    }

    public Footage getFootage() {
        return footage;
    }
}
