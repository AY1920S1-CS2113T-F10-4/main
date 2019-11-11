//@@author JasonLeeWeiHern

package captest;

import gazeeebo.storage.TriviaStorage;
import gazeeebo.triviamanager.TriviaManager;
import gazeeebo.ui.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.parser.CapCommandParser;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class MainCapCalculatorTest {
    private Ui ui = new Ui();
    private Storage storage = new Storage();
    private ArrayList<Task> list = new ArrayList<>();
    private Stack<ArrayList<Task>> commandStack = new Stack<>();
    private ArrayList<Task> deletedTask = new ArrayList<>();

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
    void testMainEscCommand() throws IOException, ParseException, DukeException {
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        String moduleCode = "";
        String grade = "";
        int moduleCredit = 0;
        CapCommandParser test = new CapCommandParser(moduleCode, moduleCredit, grade);
        ByteArrayInputStream in = new ByteArrayInputStream("esc".getBytes());
        System.setIn(in);
        test.execute(list, ui, storage, commandStack, deletedTask, triviaManager);
        Assertions.assertEquals("Welcome to your CAP Calculator page! What would you like to do?\n\n"
                + "__________________________________________________________\n"
                + "1. Add module: add semester number,"
                + "module's code, module's credit, module's grade\n"
                + "2. Find module: find moduleCode\n"
                + "3. Delete a module: delete module\n"
                + "4. See your CAP list: list all/semester number\n"
                + "5. List of commands for CAP page: commands\n"
                + "6. Help page: help\n"
                + "7. Exit CAP page: esc\n"
                + "__________________________________________________________\n"
                + "\nGoing back to Main Menu...\n"
                + "Content Page:\n"
                + "------------------ \n"
                + "1. help\n"
                + "2. contacts\n"
                + "3. expenses\n"
                + "4. places\n"
                + "5. tasks\n"
                + "6. cap\n"
                + "7. spec\n"
                + "8. moduleplanner\n"
                + "9. notes\n"
                + "10. change password\n"
                + "To exit: bye\n", output.toString()
        );
    }

    @Test
    void testMainNumCommand() throws IOException, ParseException, DukeException {
        TriviaStorage triviaStorage = new TriviaStorage();
        TriviaManager triviaManager = new TriviaManager(triviaStorage);
        String moduleCode = "";
        String grade = "";
        int moduleCredit = 0;
        CapCommandParser test = new CapCommandParser(moduleCode, moduleCredit, grade);
        ByteArrayInputStream in = new ByteArrayInputStream("7".getBytes());
        System.setIn(in);
        test.execute(list, ui, storage, commandStack, deletedTask, triviaManager);
        Assertions.assertEquals("Welcome to your CAP Calculator page! What would you like to do?\n\n"
                + "__________________________________________________________\n"
                + "1. Add module: add semester number,"
                + "module's code, module's credit, module's grade\n"
                + "2. Find module: find moduleCode\n"
                + "3. Delete a module: delete module\n"
                + "4. See your CAP list: list all/semester number\n"
                + "5. List of commands for CAP page: commands\n"
                + "6. Help page: help\n"
                + "7. Exit CAP page: esc\n"
                + "__________________________________________________________\n"
                + "\nGoing back to Main Menu...\n"
                + "Content Page:\n"
                + "------------------ \n"
                + "1. help\n"
                + "2. contacts\n"
                + "3. expenses\n"
                + "4. places\n"
                + "5. tasks\n"
                + "6. cap\n"
                + "7. spec\n"
                + "8. moduleplanner\n"
                + "9. notes\n"
                + "10. change password\n"
                + "To exit: bye\n", output.toString()
        );
    }
}
