package G16;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Footage {
    private final int ID;
    private final String title;
    private final int date;
    private final Integer duration;
    private final int journalist;

    public Footage(int ID, String title, int date, Integer duration, int journalist) {
        this.ID = ID;
        this.title = title;
        this.date = date;
        this.duration = duration;
        this.journalist = journalist;
    }

    public int getID(){
        return ID;
    }
    public String getTitle() {
        return title;
    }
    public int getDate() { return date; }
    public Integer getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        final String D = ";";
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

        return getTitle() +D + dateFormatter.format(getDate()) +D + getDuration();
    }

}