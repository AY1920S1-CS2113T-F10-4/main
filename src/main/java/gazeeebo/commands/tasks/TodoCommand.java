//@@author jessteoxizhi
package gazeeebo.commands.tasks;


import gazeeebo.commands.Command;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.tasks.*;
import gazeeebo.storage.Storage;
import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class TodoCommand extends Command {

    @Override
    public void execute(ArrayList<Task> list, final Ui ui, final Storage storage, final Stack<ArrayList<Task>> commandStack, final ArrayList<Task> deletedTask, final TriviaManager triviaManager) throws DukeException, ParseException, IOException {
        String description = "";
        try {
            if (ui.fullCommand.length() <= 4) {
                throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
            } else {
                description = ui.fullCommand.substring(5);
                triviaManager.learnInput(ui.fullCommand, storage);
            }
            Todo to = new Todo(description);
            list.add(to);
            System.out.println("Got it. I've added this task:");
            System.out.println(to.listFormat());
            System.out.println("Now you have " + list.size() + " tasks in the list.");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.writeToSaveFile(sb.toString());
        } catch (DukeException e) {
            System.out.println(e.getMessage());
            triviaManager.showPossibleInputs("todo");
            //triviaManager.showAllMap();
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