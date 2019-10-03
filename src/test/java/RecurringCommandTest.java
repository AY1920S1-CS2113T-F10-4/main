import Storage.Storage;
import Tasks.Deadline;
import Tasks.Task;
import UI.Ui;
import commands.RecurringCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RecurringCommandTest  {
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream(){
        System.out.flush();
        System.setOut(original);
    }

    @Test
    void testRecurring() throws ParseException, IOException {
        Ui ui = new Ui();
        Storage storage = new Storage();
        RecurringCommand testR = new RecurringCommand();
        ArrayList<Task> list = new ArrayList<>();
        Deadline newd = new Deadline("yearly assignment", "2019-01-01 01:01:01");
        list.add(newd);
        ui.FullCommand = "done 1";

        testR.AddRecurring(list, list.get(0).toString(),storage);
        assertEquals("\nI've automatically added this yearly task again:\n[D][\u2718]yearly assignment(by:01 Jan 2020 01:01:01)"
                + "\nNow you have " + list.size() + " tasks in the list.\n",output.toString());
    }
}
