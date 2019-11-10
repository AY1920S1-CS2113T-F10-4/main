package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.storage.Storage;
import gazeeebo.storage.TasksPageStorage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.text.ParseException;

import gazeeebo.exception.DukeException;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Stack;

public class RescheduleCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException {
        try {
            if (ui.fullCommand.length() == 10) {
                throw new DukeException("OOPS!!! The object of a rescheduling cannot be null.");
            } else {
                int index = Integer.parseInt(ui.fullCommand.split(" ")[1]) - 1;
                String Decription = list.get(index).description;
                ;
                System.out.println("You are rescheduling this task: " + Decription);
                System.out.println("Please type in your new timeline");
                ui.readCommand();
                String time = ui.fullCommand;
                System.out.println("Are you sure you want to reschedule this task? (yes/no)");
                ui.readCommand();
                if (ui.fullCommand.equals("yes")) {
                    if (list.get(index).listFormat().contains("by")) {
                        Task RescheduledDeadline = new Deadline(Decription, time);
                        list.remove(index);
                        list.add(RescheduledDeadline);
                        System.out.println("Noted. I've changed this task's timeline: ");
                        System.out.println(RescheduledDeadline.listFormat());
                    } else {
                        Event RescheduledEvent = new Event(Decription, time);
                        list.remove(index);
                        list.add(RescheduledEvent);
                        System.out.println("Noted. I've changed this task's timeline: ");
                        System.out.println(RescheduledEvent.listFormat());
                    }

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        sb.append(list.get(i).toString() + "\n");
                    }
                    TasksPageStorage tasksPageStorage = new TasksPageStorage();
                    tasksPageStorage.writeToSaveFile(sb.toString());
                } else {
                    System.out.println("It's fine. Nothing has been changed.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter the task number instead of the name");
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
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