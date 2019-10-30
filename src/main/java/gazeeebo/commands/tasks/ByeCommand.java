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

public class ByeCommand extends Command {
    /**
     * Returns void execute function for gazeeebo.commands.ByeCommand.
     *
     * @param list An array list of type gazeeebo.Tasks.Task.
     * @param ui Class ui
     * @param storage class storage
     * @param commandStack
     * @param deletedTask
     * @return Void.
     * @throws DukeException | ParseException | IOException
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException {
        System.out.println("Bye! Hope to see you again soon!");
    }
    @Override
    public boolean isExit() {
        return true;
    }
}
