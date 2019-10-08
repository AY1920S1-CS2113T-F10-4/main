import Storage.Storage;
import Tasks.Deadline;
import Tasks.Task;
import UI.Ui;
import commands.DeadlineCommand;
import commands.SnoozeCommand;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Exception.DukeException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnoozeCommandTest {
    @Test
    public void testExecuteSnooze() throws ParseException,IOException,DukeException{
        DeadlineCommand deadlineCommand = new DeadlineCommand();
        SnoozeCommand snoozeCommand = new SnoozeCommand();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.FullCommand = "deadline return book /by 2008-07-07 03:03:03";
        try {
            deadlineCommand.execute(tasks,ui,storage, CommandStack, deletedTask);
        } catch (Exception.DukeException dukeException) {
            dukeException.printStackTrace();
        }
        int index = 0;
        int year = 1;
        int day =1;
        int month =1;
        int hour = 1;
        String Description = tasks.get(index).description;
        String date = tasks.get(index).toString().split("\\|")[3].substring(4);
        LocalDateTime newDate  = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        newDate = newDate.plusYears(year).plusMonths(month).plusDays(day).plusHours(hour);
        String newBy= newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Task snoozedDeadline = new Deadline(Description,newBy);
        tasks.remove(index);
        tasks.add(snoozedDeadline);
        assertEquals("D|\u2718|return book |by: 2009-08-08 04:03:03",tasks.get(0).toString());
    }
}