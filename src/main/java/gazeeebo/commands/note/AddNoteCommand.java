//@@author yueyuu

package gazeeebo.commands.note;

import gazeeebo.storage.NoteStorage;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.exception.DukeException;
import gazeeebo.notes.Note;
import gazeeebo.notes.NoteList;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Adds a new note to a particular day, week or month.
 */
public class AddNoteCommand extends Command {

    protected static final String DAY = "day";
    protected static final String WEEK = "week";
    protected static final String MONTH = "month";

    protected static final String NOTE_DAILY = "NoteDaily.txt";
    protected static final String NOTE_WEEKLY = "NoteWeekly.txt";
    protected static final String NOTE_MONTHLY = "NoteMonthly.txt";

    /**
     * Decodes the user's input and handles incorrect input formats.
     *
     * @param command the command the user inputs
     * @param commandName the name of the command ie. addNote, editNote, deleteNote or listNote
     * @return the date specified by the user as a LocalDate object
     * @throws DukeException when the users input format is wrong
     */
    protected LocalDate processCommand(String[] command, String commandName) throws DukeException {
        //addNote day/week/month yyyy-MM-dd
        //<the note they want to add>
        try {
            try {
                if (!(command[1].equals(DAY) || command[1].equals(WEEK) || command[1].equals(MONTH))) {
                    throw new DukeException("The second word in the command has to be \'day\', \'week\' or \'month\'.");
                }
            } catch (ArrayIndexOutOfBoundsException b) {
                throw new DukeException("OOPS!!! The description of a(n) " + commandName + " cannot be empty.");
            }
            if (command[1].equals(MONTH)) {
                command[2] = command[2] + "-01";
            }
            LocalDate date = LocalDate.parse(command[2], Note.noteFormatter);
            if (command[1].equals(WEEK) && !date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                throw new DukeException("OOPS!!! The date provided must be a Monday.");
            }
            return date;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("Please input a date.");
            //return;
        } catch (DateTimeParseException a) {
            if (command[1].equals(MONTH)) {
                throw new DukeException("The date has to been in YYYY-MM format.");
            } else {
                throw new DukeException("The date has to been in YYYY-MM-DD format.");
            }
        }
    }

    /**
     * Adds a new note to the specified day, week or month if there are existing notes.
     * Else creates a new note object with the new note as the first note.
     * After that, writes to the text file.
     *
     * @param listOfNotes the list of Notes to add the new note to depending on if its a day, week or month
     * @param userDate the date specified by the user as a LocalDate object
     * @param usersNote the new note that the user wants to add
     * @param date the date specified by the user as a String object
     * @return the new note added
     */
    protected Note addToList(ArrayList<Note> listOfNotes, LocalDate userDate, String usersNote, String date,
                             String fileName) throws IOException {
        boolean hasNote = false;
        Note noteInQuestion = null;
        for (Note n: listOfNotes) {
            if (n.noteDate.equals(userDate)) {
                n.notes.add(usersNote);
                noteInQuestion = n;
                hasNote = true;
                break;
            }
        }
        if (!hasNote) {
            Note newNote = new Note(date, usersNote);
            listOfNotes.add(newNote);
            noteInQuestion = newNote;
        }
        NoteStorage.writeToFile(fileName, listOfNotes);
        return noteInQuestion;
    }

    /**
     * Tells the user that the new note had been added successfully.
     *
     * @param usersNote the note that the user wants to add
     * @param size the number of notes the user has for the specified period after the new note has been added
     * @param period is either day, week or month
     */
    protected void printConfirmationMessage(String usersNote, int size, String period) {
        System.out.println("Got it. I've added this note to that " + period +  ":");
        System.out.println(usersNote);
        System.out.println("Now you have " + size + " note(s) for that " + period + ".");
    }

    /** The main method that executes all the sub methods. */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack,
                        ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException {
        String[] command = ui.fullCommand.split(" ");
        LocalDate userDate;
        try {
            userDate = processCommand(command, "addNote");
        } catch (DukeException e) {
            ui.showErrorMessage(e);
            return;
        }

        ui.readCommand();
        String usersNote = ui.fullCommand;
        Note noteSpecified;
        try {
            switch (command[1]) {
            case DAY:
                noteSpecified = addToList(NoteList.daily, userDate, usersNote, command[2], NOTE_DAILY);
                break;
            case WEEK:
                noteSpecified = addToList(NoteList.weekly, userDate, usersNote, command[2], NOTE_WEEKLY);
                break;
            case MONTH:
                noteSpecified = addToList(NoteList.monthly, userDate, usersNote, command[2], NOTE_MONTHLY);
                break;
            default:
                noteSpecified = null;
                break;
            }

            assert noteSpecified != null : "there is a bug in AddNoteCommand";
            printConfirmationMessage(usersNote, noteSpecified.notes.size(), command[1]);
        } catch (IOException e) {
            System.out.println("The " + command[1] + " file cannot be opened.");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
