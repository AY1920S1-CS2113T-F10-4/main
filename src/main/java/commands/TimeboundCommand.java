package commands;

import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import java.io.IOException;
import Tasks.*;
import Exception.DukeException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;


public class TimeboundCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException {
        String description = "";
        String duration = ui.FullCommand.split("/")[1];
        try {
            if (duration.length() > 6 && duration.length() < 33){
                throw new DukeException("OOPS!!! There is no proper duration of time allocated for this task.");
            } else {
                description = ui.FullCommand.split("/between ")[0];
            }
            String period = ui.FullCommand.split("/between ")[1];

            Timebound tb = new Timebound(description, period);
            list.add(tb);
            System.out.println("Got it. I've added this task:");
            System.out.println(tb.listFormat());
            System.out.println("Now you have " + list.size() + " tasks in the list.");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.Storages(sb.toString());
        }

        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
    public void undo(String command, ArrayList<Task> list, Storage storage) throws IOException {
        for (Task it : list) {
            if (it.description.contains(command.split("/between ")[0])) {
                list.remove(it);
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.Storages(sb.toString());
    }
    @Override
    public boolean isExit() {
        return false;
    }
}