package gazeeebo.commands;

import gazeeebo.Storage.Storage;
import gazeeebo.Tasks.Task;
import gazeeebo.UI.Ui;
import java.io.IOException;

import gazeeebo.Exception.DukeException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class DeleteCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException {
            if (ui.FullCommand.length() == 6) {
                throw new DukeException("OOPS!!! The description of a deletion cannot be empty.");
            } else {
                if (ui.FullCommand.length() == 8) {
                    int index = Integer.parseInt(ui.FullCommand.substring(6).trim()) - 1;
                    deletedTask.add(list.get(index));
                    String taskremoved = list.get(index).listFormat();
                    list.remove(index);
                    System.out.println("Noted. I've removed this task: ");
                    System.out.println(taskremoved);
                    System.out.println("Now you have " + list.size() + " tasks in the list.");

                    } else if (ui.FullCommand.contains("all")) { //delete all tasks at once
                        for (int i = 0; i < list.size(); i++){
                            deletedTask.add(list.get(i));
                        }
                        list.clear();
                        System.out.println("Noted. I've removed all the tasks.");
                        System.out.println("Now you have " + list.size() + " tasks in the list.");

                    } else if (ui.FullCommand.contains("and")) { //delete multiple chosen tasks
                    int numOfAnds = 0;
                    for(int i = 0; i < ui.FullCommand.length(); i++) {
                        if (ui.FullCommand.charAt(i) == 'a') {
                            numOfAnds++;
                        }
                    }
                    String[] strNumberList = ui.FullCommand.substring(7).split(" and ", numOfAnds+1);
                    int size = strNumberList.length;
                    int[] intNumberList = new int[size];
                    for(int j = 0; j < size; j++){
                        intNumberList[j] = Integer.parseInt(strNumberList[j]);
                    }
                    Arrays.sort(intNumberList);
                    int count = 1;
                    System.out.println("Noted. I've removed this task: ");
                    for(int k = 0; k < size; k++) {
                        int index = intNumberList[k] - count;
                        String taskremoved = list.get(index).listFormat();
                        deletedTask.add(list.get(index));
                        list.remove(index);
                        System.out.println(taskremoved);
                        count++;
                    }
                    System.out.println("Now you have " + list.size() + " tasks in the list.");
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.Storages(sb.toString());
            }
    }
    public void undo(ArrayList<Task> list, Storage storage,ArrayList<Task> deletedTask) throws IOException {
        list.addAll(deletedTask);
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