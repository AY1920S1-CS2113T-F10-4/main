package gazeeebo.commands.expenses;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map;
import java.util.TreeMap;


/**
 * Allows user to call commands to record and manage their expenses.
 */

public class ExpenseCommand extends Command {

    /**
     * This method is allows user to call commands to add expenses, find expenses on a certain date, delete a chosen expense,
     * see the expense list and exit the expense page.
     *
     * @param list list of all tasks
     * @param ui the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @throws IOException Catch error if the read file fails
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException {
        HashMap<LocalDate, ArrayList<String>> map = storage.Expenses(); //Read the file
        Map<LocalDate, ArrayList<String>> expenses = new TreeMap<LocalDate, ArrayList<String>>(map);

        System.out.print("Welcome to your expenses record! What would you like to do?\n\n");
        System.out.println("__________________________________________________________");
        System.out.println("1. Add expenses command: add");
        System.out.println("2. Find expenses on a certain date: find date");
        System.out.println("3. Delete a certain expense: delete expense");
        System.out.println("4. See your expense list: expense list");
        System.out.println("5. Exit Expense page: esc");
        System.out.println("__________________________________________________________");

        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.contains("add")) {
                new AddExpenseCommand(ui, storage, expenses);
            } else if (ui.fullCommand.contains("find")) {
                new FindExpenseCommand(ui, expenses);
            } else if (ui.fullCommand.contains("delete")) {
                new DeleteExpenseCommand(ui, storage, expenses);
            } else if (ui.fullCommand.equals("list")) {
                new ExpenseListCommand(ui, expenses);
            }
            ui.readCommand();
        }
        System.out.println("Go back to Main Menu");
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
