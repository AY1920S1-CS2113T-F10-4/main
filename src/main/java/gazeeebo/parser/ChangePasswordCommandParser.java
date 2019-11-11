//@@author JasonLeeWeiHern

package gazeeebo.parser;

import gazeeebo.commands.Command;
import gazeeebo.storage.PasswordStorage;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.triviamanager.TriviaManager;
import gazeeebo.ui.Ui;
import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Allows user to change their current password
 * to a new password.
 */
public class ChangePasswordCommandParser extends Command {
    /**
     * This method will verify current password and
     * write the new password to the Password.txt file.
     *
     * @param list         task lists
     * @param ui           deals with printing things to the user.
     * @param storage      deals with storing data.
     * @param commandStack keep stack of previous commands.
     * @throws IOException catch the error if the read file fails.
     */
    @Override
    public void execute(final ArrayList<Task> list, final Ui ui,
                        final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException, IOException {
        System.out.println("Enter your current password:");
        ui.readCommand();
        PasswordStorage passwordStorage = new PasswordStorage();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals(passwordStorage
                    .readFromPasswordFile().get(0).toString())) {
                System.out.println("Enter new password:");
                ui.readCommand();
                String realPassword = ui.fullCommand;
                char[] decryption = realPassword.toCharArray();
                StringBuilder decodedPassword = new StringBuilder();
                for (int i = realPassword.length() - 1; i >= 0; i--) {
                    decodedPassword.append(decryption[i]);
                }
                passwordStorage.writeToPasswordFile(decodedPassword.toString());
                System.out.println("Password successfully changed.");
                break;
            } else {
                System.out.println("Wrong password, "
                        + "exit by entering esc or try again:");
                ui.readCommand();
            }
        }
    }

    /**
     * Exits program.
     *
     * @return true to exit
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
