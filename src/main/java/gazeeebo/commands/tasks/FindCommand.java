package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;
import gazeeebo.exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class FindCommand extends Command {
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui, final Storage storage, final Stack<ArrayList<Task>> commandStack, final ArrayList<Task> deletedTask, final TriviaManager triviaManager) throws DukeException, ParseException, IOException {
        try {
            if (ui.fullCommand.length() == 5) {
                throw new DukeException("OOPS!!! The description of a search cannot be empty.");
            } else {
                ArrayList<Task> searchedlist = new ArrayList<Task>();
                for (Task it : list) {
                    if (it.description.contains(ui.fullCommand.substring(4).trim())) {
                        searchedlist.add(it);
                    }
                }
                System.out.println("Here are the matching tasks in your list:");
                for (int i = 0; i < searchedlist.size(); i++) {
                    System.out.println(i + 1 + "." + searchedlist.get(i).listFormat());
                }
            }

        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }

}