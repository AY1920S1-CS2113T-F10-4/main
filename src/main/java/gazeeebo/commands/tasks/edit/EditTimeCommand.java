//@@author JasonLeeWeiHern
package gazeeebo.commands.tasks.edit;

import gazeeebo.exception.DukeException;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.tasks.FixedDuration;
import gazeeebo.tasks.Timebound;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class EditTimeCommand {
    /**
     * This method will receive the user's
     * new time and edit the old time in the list.
     *
     * @param list        task lists
     * @param ui          the object that deals with printing things to the user.
     * @param listnoIndex the index of the list
     * @throws IOException catch the read file error.
     */
    public EditTimeCommand(final ArrayList<Task> list, final Ui ui,
                           final int listnoIndex) throws IOException {

        try {
            System.out.print("Type your time:\n");
            ui.readCommand();
            String newTime = ui.fullCommand;

            String[] breakListWords = list.get(listnoIndex).toString().split("\\|");
            if (breakListWords[0].equals("D")) {
                Deadline newdeadline = new Deadline(breakListWords[2], newTime);
                System.out.print("Ok, we have edited your Deadline time."
                        + "\n\tFrom: " + list.get(listnoIndex).listFormat()
                        + "\n\tTo:   " + newdeadline.listFormat() + "\n");
                list.set(listnoIndex, newdeadline);
            } else if (breakListWords[0].equals("E")) {
                Event newEvent = new Event(breakListWords[2], newTime);
                System.out.print("Ok, we have edited your Event time."
                        + "\n\tFrom: " + list.get(listnoIndex).listFormat()
                        + "\n\tTo:   " + newEvent.listFormat() + "\n");
                list.set(listnoIndex, newEvent);
            } else if (breakListWords[0].equals("FD")) {
                FixedDuration newFD = new FixedDuration(breakListWords[2], newTime);
                System.out.print("Ok, we have edited your FixDuration time."
                        + "\n\tFrom: " + list.get(listnoIndex).listFormat()
                        + "\n\tTo:   " + newFD.listFormat() + "\n");
                list.set(listnoIndex, newFD);
            } else if (breakListWords[0].equals("P")) {
                Timebound newP = new Timebound(breakListWords[2], newTime);
                System.out.print("Ok, we have edited your Timebound time."
                        + "\n\tFrom: " + list.get(listnoIndex).listFormat()
                        + "\n\tTo:   " + newP.listFormat() + "\n");
                list.set(listnoIndex, newP);
            } else {
                throw new DukeException("That input has no time to be edited.\n");
            }
        } catch (DukeException e) {
            System.out.print(e.getMessage());
        }
    }
}
