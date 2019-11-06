//@@author jessteoxizhi

package gazeeebo.commands.places;

import gazeeebo.storage.Storage;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.Map;

public class DeletePlacesCommand {
    /**
     * Delete a place from the list of places.
     *
     * @param ui      the object that deals with printing things to the user.
     * @param storage the object that deals with storing data.
     * @param places  Map each place to a location
     * @throws IOException catch any error if read file fails
     */

    public DeletePlacesCommand(Ui ui, Storage storage, Map<String, String> places) throws IOException, ArrayIndexOutOfBoundsException {
        if (!ui.fullCommand.equals("delete") && ui.fullCommand.contains("-")) {
            String placeToDelete = ui.fullCommand.split("-")[1];
            if (places.containsKey(placeToDelete)) {
                places.remove(placeToDelete);
                System.out.println("Successfully deleted: " + placeToDelete);
            } else {
                System.out.println(placeToDelete + " is not found in the list.");
            }
        } else {
            System.out.println("Incorrect format: delete-place");
        }
        String toStore = "";
        for (String key : places.keySet()) {
            toStore = toStore.concat(key + "|" + places.get(key) + "\n");
        }
        storage.storagesPlaces(toStore);
    }
}