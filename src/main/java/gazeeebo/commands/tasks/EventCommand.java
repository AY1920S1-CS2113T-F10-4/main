package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;
import gazeeebo.exception.DukeException;

import java.io.IOException;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Stack;

public class EventCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException {
        String description;
        try {
            if (ui.fullCommand.length() == 5) {
                throw new DukeException("OOPS!!! The description of an event cannot be empty.");
            } else {
                description = ui.fullCommand.split("/at ")[0].substring(6);
                triviaManager.learnInput(ui.fullCommand,storage);
            }
            String at = ui.fullCommand.split("/at ")[1];
            Event ev = new Event(description, at);

            //CHECKING FOR SCHEDULE ANOMALIES------------------------------------------------------------------
            ArrayList<Event> clash = new ArrayList<Event>(); //to store events that clash with the incoming event
            for (Task t : list) {
                if (t.getClass().getName().equals("gazeeebo.Tasks.Event") && ((Event) t).date.equals(ev.date) &&
                        ((ev.start.isBefore(((Event) t).start) && ev.end.isAfter(((Event) t).start)) ||
                                ev.start.equals(((Event) t).start) ||
                                (ev.start.isAfter(((Event) t).start) && ev.start.isBefore(((Event) t).end)))) {
                    clash.add((Event) t);
                }
            }
            if (!clash.isEmpty()) {
                System.out.println("The following event(s) clash with your current event:");
                for (int i = 0; i < clash.size(); i++) {
                    System.out.println((i + 1) + "." + clash.get(i).listFormat());
                }
                System.out.println("");
            }
            //--------------------------------------------------------------------------------------------------

            list.add(ev);
            System.out.println("Got it. I've added this task:");
            System.out.println(ev.listFormat());
            System.out.println("Now you have " + list.size() + " tasks in the list.");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).toString() + "\n");
            }
            storage.writeToSaveFile(sb.toString());
        } catch (DukeException e) {
            System.out.println(e.getMessage());
            triviaManager.showPossibleInputs("event");
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException a) {
            Ui.showEventDateFormatError();
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

}