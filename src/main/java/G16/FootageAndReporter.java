package G16;
import java.util.Date;

public class FootageAndReporter {
    private final Reporter reporter;
    private final Footage footage;

    public FootageAndReporter(int id, String title, Date date, Integer duration, Integer cpr, String firstName, String lastName, String streetName, Integer civicNumber, Integer zipCode, String county) {
        reporter = new Reporter(cpr, firstName, lastName, streetName, civicNumber, zipCode, county);
        footage = new Footage(id, title, date, duration);
    }

    public Reporter getReporter() {
        return reporter;
    }

    public Footage getFootage() {
        return footage;
    }
}
