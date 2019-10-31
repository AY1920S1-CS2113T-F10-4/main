
package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class ChangePasswordCommand extends Command {
    /**
     * This method will verify current password and write the new password to the Password.txt file.
     *
     * @param list    task lists
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @param commandStack
     * @throws IOException catch the error if the read file fails.
     */
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui,
                        final Storage storage, final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask, final TriviaManager triviaManager)
            throws DukeException, ParseException, IOException {
        System.out.println("Enter your current password:");
        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals(storage.readFromPasswordFile().get(0).toString())) {
                System.out.println("Enter new password:");
                ui.readCommand();
                String realPassword = ui.fullCommand;
                char[] decryption = realPassword.toCharArray();
                StringBuilder decodedPassword = new StringBuilder();
                for (int i = realPassword.length() - 1; i >= 0; i--) {
                    decodedPassword.append(decryption[i]);
                }
                storage.writeToPasswordFile(decodedPassword.toString());
                System.out.println("Password successfully changed.");
                break;
            } else {
                System.out.println("Wrong password, exit by entering esc or try again:");
                ui.readCommand();
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}