package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.commands.tasks.edit.EditCommand;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.commands.note.AddNoteCommand;
import gazeeebo.commands.note.DeleteNoteCommand;
import gazeeebo.commands.note.EditNoteCommand;
import gazeeebo.commands.note.ListNoteCommand;
import gazeeebo.commands.schedule.ScheduleDailyCommand;
import gazeeebo.commands.schedule.ScheduleMonthlyCommand;
import gazeeebo.commands.schedule.ScheduleWeeklyCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.tasks.*;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class TaskCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException, DukeException, ParseException {
        System.out.println("Welcome to your Tasks page! What would you like to do?\n");
        CalendarView calendarView = new CalendarView();
        calendarView.MonthlyView(list);
        ArrayList<Task> oldList = new ArrayList<>();
        while(!ui.fullCommand.equals("esc")) {
            ui.readCommand();
            String command = ui.fullCommand;
            String[] splitCommand = command.split(" ");
            if (splitCommand[0].equals("help")) {
                new HelpCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("list")) {
                if (command.contains("event")) {
                    new CategoryListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                } else if (command.contains("deadline")) {
                    new CategoryListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                } else if (command.contains("todo")) {
                    new CategoryListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                } else if (command.contains("fixed")) {
                    new CategoryListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                } else if (command.contains("timebound")) {
                    new CategoryListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                } else {
                    new ListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                }
            } else if (command.equals("done list")) {
                new DoneListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (command.equals("undone list")) {
                new UndoneListCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("done")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new DoneCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("delete")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new DeleteCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("deadline")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new DeadlineCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (command.contains("/after")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new DoAfterCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("event")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new EventCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("todo")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new TodoCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (command.contains("/between")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new TimeboundCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("find")) {
                new FindCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (command.contains("/require")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new FixDurationCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("reschedule")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new RescheduleCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("sort")) {
                new SortCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("scheduleDaily")) {
                new ScheduleDailyCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("scheduleWeekly")) {
                new ScheduleWeeklyCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("scheduleMonthly")) {
                new ScheduleMonthlyCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("snooze")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new SnoozeCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("tentative")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new TentativeEventCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("confirm")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new ConfirmTentativeCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].contains("undone")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new UndoneCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("undo")) {
                if (!commandStack.empty()) {
                    list = commandStack.peek();
                    System.out.println("You have undo the previous command.");
                    commandStack.pop();
                } else {
                    System.out.println("You cannot undo the previous command.");
                }
            } else if (splitCommand[0].equals("edit")) {
                oldList = new ArrayList<>();
                copyOldList(oldList,list);
                new EditCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
                commandStack.push(oldList);
            } else if (splitCommand[0].equals("addNote")) {
                new AddNoteCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("editNote")) {
                new EditNoteCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("deleteNote")) {
                new DeleteNoteCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("listNote")) {
                new ListNoteCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (command.equals("change password")) {
                new ChangePasswordCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if(splitCommand[0].equals("priority")) {
                new ChangePriority().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (command.contains("#")) {
                new TagCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (command.equals("calendar monthly view")) {
                new CalendarView().MonthlyView(list);
            } else if (command.equals("calendar annual view")) {
                new CalendarView().AnnualView(list);
            } else if (command.equals("esc")) {
                System.out.println("Back to the main page!");
            } else {
                System.out.println("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

        }
    }
    @Override
    public boolean isExit() {
        return false;
    }

    private void copyOldList(ArrayList<Task> oldList, ArrayList<Task> list) {
        for (Task task : list) {
            if (task.getClass().getName().equals("gazeeebo.tasks.Deadline")) {
                Deadline deadline = new Deadline(task.description, task.toString().split("by:")[1].trim());
                deadline.isDone = task.isDone;
                oldList.add(deadline);
            } else if (task.getClass().getName().equals("gazeeebo.tasks.Event")) {
                Event event = new Event(task.description, task.toString().split("at:")[1].trim());
                event.isDone = task.isDone;
                oldList.add(event);
            } else if (task.getClass().getName().equals("gazeeebo.tasks.Todo")) {
                Todo todo = new Todo(task.description);
                todo.isDone = task.isDone;
                oldList.add(todo);
            } else if (task.getClass().getName().equals("gazeeebo.tasks.FixedDuration")) {
                FixedDuration fixedDuration = new FixedDuration(task.description, task.toString().split("\\|")[3].trim());
                fixedDuration.isDone = task.isDone;
                oldList.add(fixedDuration);
            } else if (task.getClass().getName().equals("gazeeebo.tasks.Timebound")) {
                Timebound timebound = new Timebound(task.description, task.toString().split("\\|")[3].trim());
                timebound.isDone = task.isDone;
                oldList.add(timebound);
            }
        }
    }
}
