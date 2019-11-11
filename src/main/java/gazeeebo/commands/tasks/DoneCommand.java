//@@author jessteoxizhi

package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.storage.TasksPageStorage;
import gazeeebo.tasks.Task;
import gazeeebo.triviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;
import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Allows user to record which tasks are completed.
 */
public class DoneCommand extends Command {
    /**
     * The string "done " has 5 characters.
     */
    private static final int DONE_AND_SPACE_CHAR_COUNT = 5;
    /**
     * The string of task initials and status has 9 characters.
     */
    private static final int TASK_INITIALS_AND_STATUS_CHAR_COUNT = 9;

    /**
     * This class marks tasks as done and prints out
     * the next task to be done.
     *
     * @param list          List of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       The object that deals with storing data
     * @param commandStack the stack of previous commands.
     * @param deletedTask the list of deleted task.
     * @param triviaManager the object for triviaManager
     * @throws DukeException  Throws custom exception when
     *                        format of done command is wrong
     * @throws ParseException Catch error if parsing of command fails
     * @throws IOException    Catch error if the read file fails
     */
    @Override
    public void execute(final ArrayList<Task> list,
                        final Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException, IOException {
        try {
            if (ui.fullCommand.equals("done")) {
                throw new DukeException("The task done number"
                        + " cannot be empty.");
            }
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
        try {
            int numbercheck = Integer.parseInt(
                    ui.fullCommand.substring(DONE_AND_SPACE_CHAR_COUNT)) - 1;

            list.get(numbercheck).isDone = true;
            System.out.println("Nice! I've marked this task as done: ");
            System.out.println(list.get(numbercheck).listFormat());
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).description.contains(
                        list.get(numbercheck).description)
                        && list.get(i).listFormat().contains("/after")
                        && i != numbercheck) {
                    System.out.println("OK! Now you need to"
                            + "do the following:");
                    String[] temp = list.get(i).listFormat().split(
                            "\\(/after");
                    System.out.println(temp[0].substring(
                            TASK_INITIALS_AND_STATUS_CHAR_COUNT));
                }
            }


            RecurringCommand rc = new RecurringCommand();
            rc.addRecurring(list, numbercheck,
                    list.get(numbercheck).toString(), storage);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            TasksPageStorage tasksPageStorage = new TasksPageStorage();
            tasksPageStorage.writeToSaveFile(sb.toString());
            ui.showProgessiveBar(list);

        } catch (NumberFormatException e) {
            System.out.println("Wrong input for done command");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Done number not found");
        }
    }

    /**
     * Program does not exit and continues running
     * since command "bye" is not called.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
