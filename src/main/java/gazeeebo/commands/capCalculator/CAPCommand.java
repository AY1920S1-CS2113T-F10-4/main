package gazeeebo.commands.capCalculator;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Deals with the user in the main CAP page.
 */
public class CAPCommand extends Command {
    /** module name of the module. */
    public String moduleCode;
    /** Modular Credit of the module. */
    public int moduleCredit;
    /** Alphabetical score for the module. */
    public String grade;

    /**
     * Constructor for CAPCommand.
     * @param moduleCode name of the module
     * @param moduleCredit about of Modular Credit of the module
     * @param grade Alphabetical score attained
     */
    public CAPCommand(final String moduleCode,
                      final int moduleCredit, final String grade) {
        this.moduleCode = moduleCode;
        this.moduleCredit = moduleCredit;
        this.grade = grade;
    }

    /** Decodes the command input in the CAP page. */
    @Override
    public void execute(final ArrayList<Task> list,
                        final  Ui ui, final Storage storage,
                        final Stack<ArrayList<Task>> commandStack,
                        final ArrayList<Task> deletedTask,
                        final TriviaManager triviaManager)
            throws DukeException, ParseException,
            IOException, NullPointerException {
        String helpCAP = "__________________________________________________________\n"
                + "1. Add module: add\n"
                + "2. Find module: find moduleCode/semNumber\n"
                + "3. Delete a module: delete module\n"
                + "4. See your CAP list: list\n"
                + "5. Help Command: help\n"
                + "6. Exit CAP page: esc\n"
                + "__________________________________________________________\n\n";
        System.out.print("Welcome to your CAP Calculator page! "
                + "What would you like to do?\n\n");
        System.out.print(helpCAP);
        HashMap<String, ArrayList<CAPCommand>> map
                = storage.readFromCAPFile(); //Read the file
        Map<String, ArrayList<CAPCommand>> caplist = new TreeMap<>(map);
        String lineBreak = "------------------------------\n";
        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            double cap = new CalculateCAPCommand().calculateCAP(caplist);
            if (ui.fullCommand.equals("add")) {
                new AddCAPCommand(ui, caplist);
            } else if (ui.fullCommand.equals("list")) {
                new ListCAPCommand(ui, caplist, lineBreak);
            } else if (ui.fullCommand.split(" ")[0].equals("find")
                    && !ui.fullCommand.equals("find")) {
                new FindCAPCommand(ui, caplist, lineBreak);
            } else if (ui.fullCommand.split(" ")[0].equals("delete")
                    && !ui.fullCommand.equals("delete")) {
                new DeleteCAPCommand(ui, caplist);
            } else if (ui.fullCommand.equals("help")) {
                System.out.println(helpCAP);
            } else {
                System.out.println("Command not Found:\n" + helpCAP);
            }
            String toStore = "";
            for (String key : caplist.keySet()) {
                for (int i = 0; i < caplist.get(key).size(); i++) {
                    toStore = toStore.concat(key + "|"
                            + caplist.get(key).get(i).moduleCode
                            + "|" + caplist.get(key).get(i).moduleCredit
                            + "|" + caplist.get(key).get(i).grade + "\n");

                }
            }
            storage.writeToCAPFile(toStore);
            System.out.println("What do you want to do next ?");
            ui.readCommand();
        }
        System.out.print("Going back to Main Menu\n");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
