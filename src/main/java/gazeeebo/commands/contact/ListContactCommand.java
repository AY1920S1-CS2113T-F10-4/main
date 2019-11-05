package gazeeebo.commands.Contact;


import java.util.Map;

/**
 * List all the contacts in the list.
 */
public class ListContactCommand {
    private static final int HORT_LINE_SEPARATOR = 30;

    /**
     * List out all the phone numbers.
     *
     * @param contactList to Map each name to its phone number.
     * @param lineBreak   to print out a
     *                    separator to separate each line in the list.
     */
    public ListContactCommand(final Map<String, String> contactList,
                              final String lineBreak) {
        System.out.print("Name:                         "
                + "| Number:\n" + lineBreak);
        for (String key : contactList.keySet()) {
            if (!key.contains("NUS") || !key.contains("CEG")) {
                forPrint(contactList, lineBreak, key);
            }
        }
        System.out.print("\nCEG CONTACTS:\n");
        for (String key : contactList.keySet()) {
            if (key.contains("CEG")) {
                forPrint(contactList, lineBreak, key);
            }
        }
        System.out.print("\nNUS CONTACTS:\n");
        for (String key : contactList.keySet()) {
            if (key.contains("NUS")) {
                forPrint(contactList, lineBreak, key);
            }
        }
    }

    /**
     * To print the contact list.
     *
     * @param contact   Map each name to its phone number.
     * @param lineBreak print out a separator to separate each line in the list.
     * @param key       gets the key of the contact.
     */
    public void forPrint(final Map<String, String> contact,
                         final String lineBreak, final String key) {
        System.out.print(key);
        int l = HORT_LINE_SEPARATOR - key.length();
        for (int i = 0; i < l; i++) {
            System.out.print(" ");
        }
        System.out.print("| ");
        System.out.print(contact.get(key) + "\n" + lineBreak);
    }
}
