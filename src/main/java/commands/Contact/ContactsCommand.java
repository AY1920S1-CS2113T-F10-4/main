package commands.Contact;

import Storage.Storage;
import Tasks.Task;
import UI.Ui;
import commands.Command;

import java.io.IOException;
import java.util.*;

public class ContactsCommand extends Command {
    private static String LINE_BREAK = "------------------------------------------\n";
    /**
     * This method is the list of all the contact numbers and you got add/find/delete contacts.
     * @param list list of all tasks
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @throws IOException Catch error if the read file fails
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack,ArrayList<Task> deletedTask) throws IOException {
        System.out.print("CONTACTS PAGE\n\n");
        HashMap<String, String> map = storage.Contact(); //Read the file
        Map<String, String> contact = new TreeMap<String, String>(map);

        System.out.print("Name:                         | Number:\n" + LINE_BREAK);
        for (String key : contact.keySet()) {
            if (!key.contains("NUS")) {
                System.out.print(key);
                int l = 30 - key.length();
                for (int i = 0; i < l; i++) {
                    System.out.print(" ");
                }
                System.out.print("| ");
                System.out.print(contact.get(key)+ "\n" + LINE_BREAK);
            }
        }
        System.out.print("\nNUS CONTACTS:\n");
        for (String key : contact.keySet()) {
            if (key.contains("NUS")) {
                System.out.print(key);
                int l = 30 - key.length();
                for (int i = 0; i < l; i++) {
                    System.out.print(" ");
                }
                System.out.print("| ");
                System.out.print(contact.get(key) + "\n" + LINE_BREAK);
            }
        }
        ui.ReadCommand();
        while (!ui.FullCommand.equals("esc")) {
            if (ui.FullCommand.equals("add")) {
                new AddContactCommand(ui,storage,contact);
            } else if (ui.FullCommand.split(" ")[0].equals("find")) {
                new FindContactCommand(ui,contact,LINE_BREAK);
            }
            else if (ui.FullCommand.equals("c_list")) {
                new ListContactCommand(contact,LINE_BREAK);
            } else if (ui.FullCommand.contains("delete")) {
                new DeleteContactCommand(ui,storage,contact);
            }
            ui.ReadCommand();
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
