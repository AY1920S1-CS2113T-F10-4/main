package Tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Timebound extends Task {
    public LocalDate dateStart;
    public LocalDate dateEnd;
    public static DateTimeFormatter fmtD = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public Timebound (String description, String period) {
        super(description);
        String[] dates = period.split(" and ");

        this.dateStart = LocalDate.parse(dates[0], fmtD);
        this.dateEnd = LocalDate.parse(dates[1], fmtD);
    }

    @Override
    public String toString() {
        return "P"+ " | " + super.getStatusIcon() + " | " + super.description + " | " + dateStart.format(fmtD) + " and " + dateEnd.format(fmtD);
    }

    @Override

    public String listFormat(){
        return "[P]" + "[" + super.getStatusIcon() + "] " + super.description + "(between: " + dateStart.format(DateTimeFormatter.ofPattern("dd LLL yyyy")) + " and " + dateEnd.format(DateTimeFormatter.ofPattern("dd LLL yyyy")) + ")";
    }
}