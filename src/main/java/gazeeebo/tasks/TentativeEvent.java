package gazeeebo.tasks;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class TentativeEvent extends Task {
    public ArrayList<String> tentativetimes;

    /**
     * This method initialize a tentative event.
     * @param description String of description of event.
     * @param tentativetimes Times slots that are possible.
     */
    public TentativeEvent(String description,ArrayList<String> tentativetimes) {
        super(description);
        this.tentativetimes = tentativetimes;
    }

    /**
     * This method changes tentative event to a String format.
     * @return String format of the event.
     */
    @Override
    public String toString() {
        String timeslots = "TE" + "|" + super.getStatusIcon() + "|" + super.description;
        for (int i = 0; i < tentativetimes.size(); i++) {
            timeslots +=  "|" + tentativetimes.get(i);
        }
        return timeslots;
    }

    /**
     * This method changes tentative event to String format for saving purpose.
     * @return String of tentative event.
     */
    @Override
    public String listFormat() {
        String timeslots = "[TE]" + "[" + super.getStatusIcon() + "] " + description + "\n";
        for (int i = 0; i < tentativetimes.size(); i++) {
            DateTimeFormatter fmtED = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter fmtET = DateTimeFormatter.ofPattern("HH:mm:ss");
            String[] dateTime = tentativetimes.get(i).split(" ");
            String[] time = dateTime[1].split("-");
            String datestring = LocalDate
                    .parse(dateTime[0], fmtED)
                    .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
            String output = datestring + " "
                    +  LocalTime.parse(time[0], fmtET).format(fmtET)
                    + "-" + LocalTime.parse(time[1], fmtET).format(fmtET)
                    + ")";
            if (i == 0) {
                timeslots += "at " + output + "\n";
            } else {
                timeslots += "or " + output + "\n";
            }
        }
        return timeslots;
    }
}