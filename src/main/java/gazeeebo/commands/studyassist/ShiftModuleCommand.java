package gazeeebo.commands.studyassist;

import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import java.io.IOException;
import java.util.stream.Collectors;

public class ShiftModuleCommand {
    /**
     * This method allows users to shift certain module from current semester to another
     * semesters, store changes in data structure and external txt files.
     * @param studyPlan the object that holds data structure of module plan.
     * @param storage the object that deals with access,modify and save files.
     * @param ui the object htat deals with interactions between users and the system.
     * @throws IOException if user input is wrong firmat
     */
    public void execute(StudyPlannerCommand studyPlan, Storage storage, Ui ui) throws IOException,DukeException {
        try {
            if (ui.fullCommand.split(" ").length != 4) {
                throw new DukeException("Please follow the correct input format");
            }
            String ModuleCode = ui.fullCommand.split(" ")[1];
            if (ModuleCode.isEmpty()) {
                throw new DukeException("Module code could not be null");
            }
            if (studyPlan.MCMap.get(ModuleCode) == null) {
                throw new DukeException("We currently do not support this module.");
            }
            int Semester = Integer.parseInt(ui.fullCommand.split(" ")[3]) - 1;
            if (Semester >= 8 || Semester < 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            boolean flag = false;
            int semesterNumber = -1;
            for (int i = 0;i < studyPlan.StudyPlan.size() && !flag;i++) {
                if (studyPlan.StudyPlan.get(i).contains(ModuleCode)) {
                    flag = true;
                    semesterNumber = i;
                }
            }
            if (!flag) throw new DukeException("This module is not inside the study plan");
            if (semesterNumber == Semester) throw new DukeException("This module is already inside Sem " + (Semester+1) + ".");
            try {
                boolean flag2 = false;
                for (int i = 0; i < studyPlan.StudyPlan.size(); i++) {
                    if (studyPlan.StudyPlan.get(i).contains(ModuleCode)) {
                        flag2 = true;
                        studyPlan.StudyPlan.get(i).remove(studyPlan.StudyPlan.get(i).indexOf(ModuleCode));
                    }
                }
                if (!flag2) throw new DukeException("This module is not inside the study plan");
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
            studyPlan.StudyPlan.get(Semester).add(ModuleCode);
            String toStore = "";
            for (int i = 0; i < studyPlan.StudyPlan.size(); i++) {
                toStore += studyPlan.StudyPlan.get(i).stream().map(Object::toString).collect(Collectors.joining(" "));
                toStore += "\n";
            }
            storage.Storage_StudyPlan(toStore);
            System.out.println("This module " + ModuleCode + " has been successfully shifted to Sem" + (Semester + 1) + ".");
        } catch (DukeException e){
            System.out.println(e.getMessage());
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Please input correct Semester number");
        }
    }
}

