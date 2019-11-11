//@@author yueyuu

package gazeeebo.commands.schedule;

import gazeeebo.commands.note.ListNoteCommand;
import gazeeebo.notes.Note;
import gazeeebo.notes.NoteList;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.Timebound;
import gazeeebo.triviamanager.TriviaManager;
import gazeeebo.ui.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.commands.Command;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Lists out all the tasks that the user has on the specified day.
 */
public class ScheduleDailyCommand extends Command {
    protected static final String EVENT = "gazeeebo.tasks.Event";
    protected static final String DEADLINE = "gazeeebo.tasks.Deadline";
    protected static final String TIMEBOUND = "gazeeebo.tasks.Timebound";

    public static final String LIST_NOTE_MESSAGE = "\nNotes:";

    //format for the command: scheduleDaily <yyyy-MM-dd>

    /**
     * This is the main body of the ScheduleDaily command.
     *
     * @param list the tasks list.
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data to the Save.txt file.
     * @param commandStack not used
     * @param deletedTask not used
     * @throws NullPointerException if tDate doesn't get updated.
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack,
                        ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException,
            ParseException, IOException, NullPointerException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] command = ui.fullCommand.split(" ");
        if (command.length > 2) {
            System.out.println("The command should be in the format \"scheduleDaily yyyy-MM-dd\".");
            return;
        }
        LocalDate userDate;
        try {
            userDate = LocalDate.parse(command[1], fmt);
        } catch (DateTimeParseException e) {
            System.out.println("Please input the date in yyyy-MM-dd format.");
            return;
        } catch (IndexOutOfBoundsException i) {
            System.out.println("OOPS!!! The description of a scheduleDaily cannot be empty.");
            return;
        }
        ArrayList<Task> schedule = new ArrayList<Task>();
        for (Task t: list) {
            LocalDate taskDate = null;
            switch (t.getClass().getName()) {
            case EVENT:
                taskDate = ((Event) t).date;
                break;
            case DEADLINE:
                taskDate = ((Deadline) t).by.toLocalDate();
                break;
            case TIMEBOUND:
                LocalDate startDate = ((Timebound) t).dateStart;
                LocalDate endDate = ((Timebound) t).dateEnd;
                if (userDate.equals(startDate) || userDate.equals(endDate)
                        || (userDate.isAfter(startDate) && userDate.isBefore(endDate))) {
                    schedule.add(t);
                }
                break;
            default: continue;
            }
            if (userDate.equals(taskDate)) {
                schedule.add(t);
            }
        }
        if (schedule.isEmpty()) {
            System.out.println("You have nothing scheduled on this day!");
        } else {
            System.out.println("Here is your schedule for " + userDate.format(fmt) + ":");
            for (int i = 0; i < schedule.size(); i++) {
                System.out.println((i + 1) + "." + schedule.get(i).listFormat());
            }
        }
        System.out.println(LIST_NOTE_MESSAGE);
        printNotes(NoteList.daily, userDate);
    }

    protected void printNotes(ArrayList<Note> periodList, LocalDate dateToList) {
        for (Note n: periodList) {
            if (n.noteDate.equals(dateToList)) {
                ListNoteCommand lnc = new ListNoteCommand();
                lnc.printOutNoteList(n.notes);
                return;
            }
        }
    }

    /**
     * Tells the main Duke class that the system should not exit and continue running.
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }

}


