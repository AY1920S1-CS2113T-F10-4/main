package CAPCalculatorTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.capCalculator.CAPCommand;
import gazeeebo.commands.capCalculator.FindCAPCommand;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindCAPCommandTest {
    private static final String LINEBREAK = "------------------------------\n";
    private Ui ui = new Ui();
    private Storage storage = new Storage();
    private ArrayList<Task> list = new ArrayList<>();
    private Stack<String> commandStack = new Stack<>();
    private ArrayList<Task> deletedTask = new ArrayList<>();
    private HashMap<String, ArrayList<CAPCommand>> map = new HashMap<>();
    private Map<String, ArrayList<CAPCommand>> CAPList = new TreeMap<>(map);

    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream() {
        System.out.flush();
        System.setOut(original);
    }
    @Test
    void testFindByModuleCodeCAPCommand () {
        CAPCommand newCAP = new CAPCommand("CS1231", 4, "A");
        ArrayList<CAPCommand> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CAPCommand> list2 = new ArrayList<>();
        CAPCommand newCAP2 = new CAPCommand("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1",list);
        CAPList.put("2",list2);
        ui.fullCommand = "find CG1112";
        FindCAPCommand test = new FindCAPCommand(ui, CAPList, LINEBREAK);
        assertEquals("Sem | Module code | MC | CAP\n" + LINEBREAK
                + "2   | CG1112      | 6  | A\n"
                + LINEBREAK, output.toString());
    }

    @Test
    void testFindBySemCAPCommand () {
        CAPCommand newCAP = new CAPCommand("CS1231", 4, "A");
        ArrayList<CAPCommand> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CAPCommand> list2 = new ArrayList<>();
        CAPCommand newCAP2 = new CAPCommand("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1",list);
        CAPList.put("2",list2);
        ui.fullCommand = "find 1";
        FindCAPCommand test = new FindCAPCommand(ui, CAPList, LINEBREAK);
        assertEquals("Sem | Module code | MC | CAP\n" + LINEBREAK
                + "1   | CS1231      | 4  | A\n"
                + LINEBREAK, output.toString());
    }

    @Test
    void testFindNotInTheCapListCommand () {
        CAPCommand newCAP = new CAPCommand("CS1231", 4, "A");
        ArrayList<CAPCommand> list = new ArrayList<>();
        list.add(newCAP);
        ArrayList<CAPCommand> list2 = new ArrayList<>();
        CAPCommand newCAP2 = new CAPCommand("CG1112", 6, "A");
        list2.add(newCAP2);
        CAPList.put("1",list);
        CAPList.put("2",list2);
        ui.fullCommand = "find CS2101";
        FindCAPCommand test = new FindCAPCommand(ui, CAPList, LINEBREAK);
        assertEquals("CS2101 is not found in the list.\n", output.toString());
    }
}
