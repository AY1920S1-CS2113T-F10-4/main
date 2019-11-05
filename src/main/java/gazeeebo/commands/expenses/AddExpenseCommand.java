package gazeeebo.commands.expenses;

import gazeeebo.storage.Storage;
import gazeeebo.UI.Ui;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class AddExpenseCommand {

    /**
     * This method adds the expense from the expense list and expenses.
     *
     * @param ui       the object that deals with printing things to the user.
     * @param storage  the object that deals with storing data,
     *                 in this case storing data in the expenses map
     * @param expenses the map that maps each expenses to its date
     * @throws IOException catch any error if read file fails
     */
    public AddExpenseCommand(final Ui ui,
                             final Storage storage,
                             final Map<LocalDate, ArrayList<String>> expenses)
            throws IOException {

        System.out.println("What did you buy:");
        ui.readCommand();
        String item = ui.fullCommand;

        System.out.println("How much did you spend:");
        ui.readCommand();
        String price = ui.fullCommand;

        String itemAndPrice = item + ", " + price;

        System.out.println("Date of purchase:");
        ui.readCommand();
        String date = ui.fullCommand;

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfPurchase = LocalDate.parse(date, fmt);
        //Storing expenses value as an array to the date keys.
        //If the item were bought on the same date as a previous item,
        //add the item in the array under the same key.
        ArrayList<String> itemAndPriceList = new ArrayList<>();
        boolean isEqual = false;
        for (LocalDate key : expenses.keySet()) {
            if (dateOfPurchase.equals(key)) { //if date equal
                expenses.get(key).add(itemAndPrice);
                isEqual = true;
            }
        }
        if (!isEqual) {
            itemAndPriceList.add(itemAndPrice);
            expenses.put(dateOfPurchase,
                    itemAndPriceList);
        }
        System.out.println("Successfully added: "
                + "\n" + itemAndPrice
                + ", bought on " + dateOfPurchase);

        /*Stores the updated expenses map after deletion of expenses*/
        String toStore = "";
        for (LocalDate key : expenses.keySet()) {
            if (expenses.get(key).size() > 1) {
                for (int i = 0; i < expenses.get(key).size(); i++) {
                    toStore = toStore.concat(key + "|"
                            + expenses.get(key).get(i) + "\n");
                }
            } else if (expenses.get(key).size() == 1) {

                toStore = toStore.concat(key + "|"
                        + expenses.get(key).get(0) + "\n");
            }
        }
        storage.Storages_Expenses(toStore);
    }
}
