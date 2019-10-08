package commands;

import Storage.Storage;
import Tasks.Task;
import UI.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class ChangePasswordCommand extends Command {
    /**
     *  This method will verify current password and write the new password to the Password.txt file.
     * @param list task lists
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @throws IOException
     */
    @Override
    public void execute (ArrayList<Task> list, Ui ui, Storage storage) throws IOException {
        System.out.println("Enter your current password:");
        ui.ReadCommand();
        ArrayList<String> password_list = new ArrayList<>();
        while(!ui.FullCommand.equals("esc")) {
            if (ui.FullCommand.equals(storage.Password().get(0))) {
                System.out.println("Enter new password:");
                ui.ReadCommand();
                storage.Storages_password(ui.FullCommand);
                System.out.println("Password successfully changed :)");
                break;
            } else {
                System.out.println("Wrong password, exit by entering esc or try again:");
                ui.ReadCommand();
            }
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
