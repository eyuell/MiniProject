package MiniProject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

public class ProjectManagementTool{
    private ArrayList<Project> projects;

    public ProjectManagementTool(){
        this.projects = new ArrayList<>();
    }

    public static void main(String [] args){
        System.out.println("This program works on project schedules");
        MiniProject.ProjectManagementTool start = new MiniProject.ProjectManagementTool();
        start.run();
    }

    public void printMenuOption(){
        System.out.println("=========================================");
        System.out.println("1. Register Project");
        System.out.println("2. Register Tasks and Milestones");
        System.out.println("3. Register Team members");
        System.out.println("4. Assign time to Tasks");
        System.out.println("5. Assign Manpower to Tasks");
        System.out.println("6. Register Actual Resources to tasks");
        System.out.println("7. Display Project Schedule");
        System.out.println("8. Monitor Progress");
        System.out.println("9. Monitor Time Spent");
        System.out.println("10. Monitor Participation");
        System.out.println("12. Monitor Risk");
        System.out.println("13. Edit Information");
        System.out.println("14. QUIT Program");
        System.out.println("=========================================");
        System.out.println();
    }

    public void run() {
        int optionChoice;
        final int REGISTER_PROJECT = 1;
        final int REGISTER_TASKS = 2;
        final int REGISTER_TEAM_MEMBERS = 3;
        final int ASSIGN_TIME = 4;
        final int ASSIGN_MANPOWER = 5;
        final int REGISTER_ACTUAL_DATA = 6;
        final int PRINT_PLANED_ACTUAL_SCHEDULE = 7;
        final int MONITOR_PROGRESS = 8;
        final int MONITOR_TIME_SPENT = 9;
        final int MONITOR_PARTICIPATION = 10;
        final int MONITOR_RISK = 11;
        final int EDIT_INFO = 12;
        final int QUIT = 13;

        readStoredFile();

        do {
            printMenuOption();
            System.out.print("Choose menu option ");
            optionChoice = new KeyboardInput().Int();     //keyboard input for the project

            switch (optionChoice){
                case REGISTER_PROJECT:
                    registerProject();
                    break;

                case REGISTER_TASKS:
                    registerTasks();
                    break;

                case REGISTER_TEAM_MEMBERS:
                    registerTeamMember();
                    break;

                case ASSIGN_TIME:
                    assignTime();
                    break;

                case ASSIGN_MANPOWER:
                    assignManPower();
                    break;

                case REGISTER_ACTUAL_DATA:
                    registerActualData();
                    break;

                case PRINT_PLANED_ACTUAL_SCHEDULE:
                    printPlannedAndActualSchedule();
                    break;

                case MONITOR_PROGRESS:
                    monitorProgress();
                    break;

                case MONITOR_TIME_SPENT:
                    monitorTimeSpent();
                    break;

                case MONITOR_PARTICIPATION:
                    monitorParticipation();
                    break;

                case MONITOR_RISK:
                    monitorRisk();
                    break;

                case EDIT_INFO:
                    editInfo();
                    break;

                case QUIT:
                    System.out.println("Saving updates. Do not turn off the computer! ");
                    System.out.println("***");
                    break;

                default:
                    System.out.println("Option " + optionChoice + " is not valid.");
                    System.out.println("***");
                    break;
            }
        } while (optionChoice != QUIT);

        writeToJsonFile();
    }

    public void registerProject(){
        System.out.println();
        System.out.print("Enter name of a project ");
        String projectName = new KeyboardInput().Line();

        System.out.println();
        System.out.print("Enter project ID number ");
        String projectID = new KeyboardInput().Line();
        //
        int choice;
        final int SECOND_OPTION = 2;
        System.out.println("How do you set the project Start and End dates :");
        do{
            System.out.println("    1. Set the dates now");
            System.out.println("    2. Get the dates from the tasks ");
            System.out.print("Enter option 1 or 2 ");
            choice = new KeyboardInput().positiveInt();
            if(choice > SECOND_OPTION){
                System.out.println("Incorrect option choice ");
            }
        }while (choice > SECOND_OPTION);

        LocalDate projectStartDate = null;
        LocalDate projectFinishDate = null;
        long duration = 0;
        if(choice != SECOND_OPTION){
            System.out.println("Enter the Start date of the project: ");
            projectStartDate = new DataEvaluator().readDate();

            System.out.println("Enter the Finish date of the project: ");
            projectFinishDate = new DataEvaluator().readDate();

            duration = ChronoUnit.DAYS.between(projectStartDate, projectFinishDate) + 1;
        }

        Project project = new Project(projectName, projectID, projectStartDate);
        projects.add(project);
        project.setFinishDate(projectFinishDate);
        project.setDuration(duration);
        System.out.println();
        System.out.println("Project '" + projectName + "' is registered successfully !");
        pause();
    }

    public void registerTasks(){

        Project foundProject = retrieveProject();
        String taskID;
        String milestoneID;

        if(foundProject != null){
            boolean continueAdding = true;

            while(continueAdding){
                System.out.println();
                System.out.print("Enter name of a new task (Enter * to stop adding and exit) ");
                String taskName = new KeyboardInput().Line();
                taskName = new DataEvaluator().nameLength(taskName);
                if(taskName.equals("*")){
                    continueAdding = false;
                }

                if(continueAdding){
                    taskID = readNewTaskID(foundProject);

                    int typeOfTask = typeOfTask();

                    Task newTask = new Task(taskName, taskID, typeOfTask);
                    System.out.println();
                    System.out.println("Task '" + taskName + "' (" + taskID + ") is registered successfully... ");
                    foundProject.getTasks().add(newTask);
                }
            }
            final String YES = "Y";
            System.out.print("Do you want to add Milestones to the project ? (Y/N) ");
            String choice = new KeyboardInput().Line();
            choice = new DataEvaluator().yesOrNo(choice);

            if(choice.equals(YES)){
                continueAdding = true;

                while(continueAdding){
                    System.out.println();
                    System.out.print("Enter name of a new Milestone (Enter * to stop adding and exit) ");
                    String milestoneName = new KeyboardInput().Line();
                    milestoneName = new DataEvaluator().nameLength(milestoneName);

                    if(milestoneName.equals("*")){
                        continueAdding = false;
                    }

                    if(continueAdding){
                        milestoneID = readNewMilestoneID(foundProject);
                        System.out.println("Enter the date of the Milestone ");
                        LocalDate date = new DataEvaluator().readDate();

                        Milestone newMilestone = new Milestone(milestoneName, milestoneID, date);
                        System.out.println();
                        System.out.println("Milestone '" + milestoneName + "' (" + milestoneID + ") is registered successfully... ");
                        foundProject.getMilestones().add(newMilestone);
                    }
                }
            }
        }else{
            System.out.println("A project does not exist to add tasks on ");
        }
        pause();
    }

    public void registerTeamMember(){
        Project foundProject = retrieveProject();
        if(foundProject != null){
            System.out.print("Enter the name of Team Member ");
            String name = new KeyboardInput().Line();
            while(teamMemberExists(foundProject, name)){
                System.out.println("The name " + name + " is already registered ");
                System.out.println();
                System.out.print("Do you want to continue registering by re-writing name details ? (Y/N) ");
                String choice = new KeyboardInput().Line();
                choice = new DataEvaluator().yesOrNo(choice);
                if(choice.equals("Y")){
                    System.out.print("Enter the name of Team Member ");
                    name = new KeyboardInput().Line();
                }else{
                    name = ""; // to exit the loop and not add it later.
                }
            }
            if(! name.equals("")){
                System.out.print("Enter ID number for new team member " + name + " ");
                String id = new KeyboardInput().Line();
                while (retrieveTeamMember(foundProject, id) != null){
                    System.out.println("The id " + id + " is already used by other team member.");
                    System.out.println();
                    System.out.print("Enter a new ID number ");
                    id = new KeyboardInput().Line();
                }
                String qualification = readQualification();

                foundProject.getTeamMembers().add(new TeamMember(name, id, qualification));
            }
            //name = retrieveTeamMember(name);
        }else {
            System.out.println("The project does not exist");
        }
        pause();
    }

    public void assignTime(){
        Project foundProject = retrieveProject();
        if(foundProject != null){
            ArrayList<Task> allTasks = foundProject.getTasks();
            if (allTasks != null){
                LocalDate taskStartDate;
                LocalDate taskFinishDate;
                long taskPlannedDuration;
                int numberOfTasks = allTasks.size();

                for (int i = 0 ; i < numberOfTasks; i++){
                    Task currentTask = allTasks.get(i);
                    boolean completenessCheck = completenessCheck(currentTask);
                    if(!completenessCheck){
                        int taskType = currentTask.getTypeOfTask();

                        System.out.println();
                        System.out.println("Data for task '" + currentTask.getName() + "' : ");
                        if(taskType == 1){ //stand alone
                            System.out.println("Enter the start date of the task ");
                            taskStartDate = new DataEvaluator().readDate();
                            currentTask.setPlannedStart(taskStartDate);

                            int lengthOfTask = readLengthOfTask();

                            if(lengthOfTask == 1){
                                System.out.print("Enter the planned duration of the task ");
                                taskPlannedDuration = new KeyboardInput().positiveInt();
                                currentTask.setPlannedDuration(taskPlannedDuration);
                                LocalDate finishDate = currentTask.getPlannedStart().plusDays(taskPlannedDuration);
                                currentTask.setPlannedFinish(finishDate);
                            }else if(lengthOfTask == 2){
                                System.out.println("Enter the finish date of the task ");
                                taskFinishDate = new DataEvaluator().readDate();
                                currentTask.setPlannedFinish(taskFinishDate);

                                long duration = ChronoUnit.DAYS.between(taskFinishDate, currentTask.getPlannedStart()) + 1;

                                currentTask.setPlannedDuration(duration);
                            }
                        } else if (taskType == 2){ //dependant
                            String connectivityType;
                            long connectivityDuration = 0;
                            boolean repeat;

                            do{
                                repeat = false;
                                Task foundTask;
                                System.out.println("How many connectivity does this task has with other tasks ?");
                                int numberOfConnectivity = new KeyboardInput().positiveInt();
                                for(int j = 0; j < numberOfConnectivity; j++){
                                    System.out.println("Connectivity " + (j+1) + ": ");
                                    System.out.println("On which task does the current task depend on ? ");
                                    String toBeConnectedToTaskID = readExistingTaskID(foundProject);
                                    foundTask = retrieveTask(toBeConnectedToTaskID, foundProject);

                                    connectivityType = readConnectivityType();

                                    System.out.print("Enter the duration of connectivity. (It could be negative if applicable) ");
                                    connectivityDuration = new KeyboardInput().Int();

                                    currentTask.getConnectivity().add(new Connectivity(foundTask, connectivityType, connectivityDuration));
                                }

                                LocalDate startDate = new DataEvaluator().extractConnectivityDate("start",currentTask.getConnectivity() );
                                LocalDate finishDate = new DataEvaluator().extractConnectivityDate("finish",currentTask.getConnectivity() );

                                if(startDate != null &&  finishDate != null){
                                    if(finishDate.isAfter(startDate)){

                                        long duration = ChronoUnit.DAYS.between(startDate, finishDate) +1;

                                        currentTask.setPlannedStart(startDate);
                                        currentTask.setPlannedFinish(finishDate);
                                        currentTask.setPlannedDuration(duration);
                                        repeat = false;
                                    }else {
                                        System.out.println("There is an error on connectivity that results an end date before a start date. Correct the data again");
                                        repeat = true;
                                    }
                                }else {
                                    System.out.print("Enter the planned duration of the task ");
                                    taskPlannedDuration = new KeyboardInput().positiveInt();
                                    currentTask.setPlannedDuration(taskPlannedDuration);
                                    if(startDate != null){
                                        finishDate = startDate.plusDays(taskPlannedDuration);
                                        currentTask.setPlannedFinish(finishDate);
                                    } else {
                                        startDate = finishDate.minusDays(taskPlannedDuration);
                                        currentTask.setPlannedStart(startDate);
                                    }
                                }
                            }while (repeat);
                        }
                        //completeness once again
                        completenessCheck(currentTask);
                    }
                }
                checkWithProjectTimes(foundProject);
                System.out.println("All tasks planned times are complete ");
            }else {
                System.out.println("There are no tasks in the project yet ");
            }
        } else {
            System.out.println("The project does not exist");
        }
        pause();
    }

    public void assignManPower(){
        Project foundProject = retrieveProject();
        if (foundProject != null){
            if(foundProject.getTasks() != null){
                int numberOfTasks = foundProject.getTasks().size();
                for(int i = 0; i < numberOfTasks; i++){
                    System.out.println();
                    Task task = foundProject.getTasks().get(i);
                    LocalDate startDate = task.getPlannedStart();
                    long days = task.getPlannedDuration();
                    for(int j = 0; j < days; j++){
                        boolean repeat;
                        LocalDate date = startDate.plusDays((long) j); //date
                        do{
                            repeat = true;
                            System.out.println("Enter Man hour need for Task ID (" + task.getId() + ") on date " + date + ": ");
                            System.out.println("    Enter * to escape to another day ");
                            String timeInput = new KeyboardInput().Line();
                            if(timeInput.equals("*")){
                                completenessCheck(task); // this may not be needed
                                repeat = false;
                            }else{
                                double time = new DataEvaluator().positiveDouble(timeInput); //time
                                String qualification = readQualification(); //qualification

                                Manpower manPower = new Manpower(qualification);
                                task.getPlannedManpower().add(new ManpowerAllocation(manPower,time,date));
                            }
                        }while (repeat);
                    }
                }
                System.out.println();
                System.out.println("Manpower allocated successfully...");
            }else{
                System.out.println("There are no tasks in the project");
            }
        }else {
            System.out.println("There are no projects to show");
        }
        pause();
    }

    public void registerActualData(){
        LocalDate today = LocalDate.now();
        Project foundProject = retrieveProject();

        if (foundProject != null ){
            if(foundProject.getTasks() != null){
                String taskId = readExistingTaskID(foundProject);
                Task foundTask = retrieveTask(taskId,foundProject);
                if(foundTask.getActualStart() == null){ //if no start date
                    System.out.println("Actual start date of the task " + taskId + " needs to be provided first. ");
                    LocalDate startDate = new DataEvaluator().readDate();

                    while(startDate.isAfter(today)){
                        System.out.print("An actual start date cannot be after today. Enter a correct date ");
                        startDate = new DataEvaluator().readDate();
                    }
                    foundTask.setActualStart(startDate);
                }

                LocalDate possibleFinalDate = today;

                if(foundTask.getActualStart() != null){
                    if(foundTask.getActualFinish() == null){ //if no finish
                        boolean taskStatus = readActualTaskStatus();
                        foundTask.setStatusOfTask(taskStatus);
                        if(taskStatus){
                            System.out.println("Enter the actual finish date of the task ");
                            boolean beforeStart;
                            do{
                                possibleFinalDate = new DataEvaluator().readDate();
                                beforeStart = possibleFinalDate.isBefore(foundTask.getActualStart());

                                if(beforeStart){
                                    System.out.print("Actual finish date cannot be before actual start date. Enter the correct finish date ");
                                }
                            }while (beforeStart);
                        }
                        foundTask.setActualFinish(possibleFinalDate);
                        //
                    }else if(foundTask.getActualFinish().equals(today)){ // if the task is active
                        System.out.print("Do you want to update the actual finish date ? (Y/N) ");
                        String choice = new KeyboardInput().Line();
                        choice = new DataEvaluator().yesOrNo(choice);
                        if(choice.equals("Y")){ //TBD: here may be i need to check if already entered resources data is not out of final day
                            System.out.println("Enter the actual finish date ");
                            boolean beforeStart;
                            LocalDate finish;
                            do{
                                finish = new DataEvaluator().readDate();
                                beforeStart = finish.isBefore(foundTask.getActualStart());

                                if(beforeStart){
                                    System.out.print("Actual finish date cannot be before actual start date. Enter the correct finish date ");
                                }
                            }while (beforeStart);

                            foundTask.setActualFinish(finish);
                            foundTask.setStatusOfTask(true);
                        }
                    }

                    System.out.print("Do you want to register actual data for this task ? (Y/N) ");
                    String yesOrNo = new KeyboardInput().Line();
                    yesOrNo = new DataEvaluator().yesOrNo(yesOrNo);
                    if(yesOrNo.equals("Y")){
                        System.out.println("On which day do you want to register data ? ");
                        LocalDate editDate = new DataEvaluator().readDate();

                        while(editDate.isAfter(foundTask.getActualFinish()) || editDate.isBefore(foundTask.getActualStart())){
                            System.out.print("Date should be between Actual Start date ");
                            if(foundTask.getActualFinish() != null){
                                System.out.print("and finish date.");
                            }else{
                                System.out.println("and today.");
                            }
                            System.out.print(" Enter a correct date ");
                            editDate = new DataEvaluator().readDate();  //date
                        }

                        System.out.println("Which team member has participated on the task ? ");
                        TeamMember foundMember;
                        do{
                            System.out.print("Enter id ");
                            String id = new KeyboardInput().Line();
                            foundMember = retrieveTeamMember(foundProject, id);
                            if(foundMember == null){
                                System.out.println("Team member not found. Try again ");
                            }
                        }while (foundMember == null);

                        System.out.print("How long time is to be registered in decimal form ");
                        double hoursWorked = new KeyboardInput().positiveDouble();  //hours
                        foundTask.getActualTeamMembers().add(new TeamMemberAllocation(foundMember,hoursWorked,editDate));

                        updateDates(foundTask, foundTask.getPlannedStart(),foundTask.getPlannedFinish(),foundTask.getActualStart(),foundTask.getActualFinish());
                    }
                }
                //
            }else{
                System.out.println("There are no tasks in the project ");
            }
        }else{
            System.out.println("There are no projects to show ");
        }
        pause();
    }

    public void printPlannedAndActualSchedule(){
        Project foundProject = retrieveProject();
        if (foundProject != null){
            if(foundProject.getTasks() != null){
                ArrayList<Task> tasks = foundProject.getTasks();
                int taskNameLength = tasks.get(0).getName().length();
                int taskIdLength = tasks.get(0).getId().length();
                int smallestIndent = taskNameLength + taskIdLength;

                ArrayList<String> listOfNames = new ArrayList<>();
                for (int i = 1; i < tasks.size(); i++) {
                    String indent = tasks.get(i).getName() + tasks.get(i).getId();
                    listOfNames.add(indent);
                }

                ArrayList<Milestone> milestones = foundProject.getMilestones();
                if(milestones != null){
                    for(int i = 0; i < milestones.size();i++){
                        String indent = milestones.get(i).getName() + milestones.get(i).getId();
                        listOfNames.add(indent);
                    }
                }

                for (int i = 0; i < listOfNames.size(); i++) {
                    int indent = listOfNames.get(i).length();
                    if(indent > smallestIndent){
                        smallestIndent = indent;
                    }
                }

                if( smallestIndent < 20){
                    smallestIndent = 20;
                }

                System.out.println(); //blank line
                LocalDate localDate = LocalDate.now();
                LocalDate tasksStartDate = tasksStartAndFinishDates("start",tasks);
                LocalDate tasksFinishDate = tasksStartAndFinishDates("finish",tasks);

                long duration = ChronoUnit.DAYS.between(tasksStartDate, tasksFinishDate) + 1;
                //
                for(int i = 0;i < (smallestIndent + 5); i++){//horizontal line1
                    System.out.print("_");
                }
                for (long i = 0; i < duration; i++){//horizontal line2
                    System.out.print("____________");
                }
                System.out.println();
                printEmpty(smallestIndent);
                System.out.println("        ProjectManagementTool for Project " + foundProject.getName() + " (" + foundProject.getProjectID() + ")");
                printEmpty(smallestIndent);
                System.out.println("        Date: " + localDate);
                System.out.println();
                System.out.println();
                int beforeText = smallestIndent/4;
                printEmpty(beforeText);
                System.out.print("Tasks/Milestones");
                int afterText = smallestIndent-beforeText - 16 + 4;
                printEmpty(afterText);
                System.out.print("|");
                for (long i = 0; i < duration; i++){//the days printed
                    LocalDate day = tasksStartDate.plusDays(i);
                    System.out.print("|" + day + "|");//12 pixels per day ?
                }
                System.out.println();
                for(int i= 0;i<(smallestIndent + 5);i++){//horizontal line1
                    System.out.print("_");
                }
                for (long i = 0; i < duration; i++){//horizontal line2
                    System.out.print("____________");
                }
                System.out.println();
                System.out.println();
                System.out.println();

                for(int i = 0; i < tasks.size();i++){
                    Task currentTask = tasks.get(i);
                    int taskIndent = smallestIndent - currentTask.getName().length() - currentTask.getId().length() + 4 - 2;

                    //planned plot
                    System.out.print(currentTask.getName()+"(" + currentTask.getId()+")");
                    printEmpty(taskIndent);
                    System.out.print("|");
                    boolean print;
                    for (long j = 0; j < duration; j++){// project tasks duration
                        LocalDate day = tasksStartDate.plusDays(j);
                        print = true;
                        for(long m = 0; m < currentTask.getPlannedDuration(); m++){
                            LocalDate taskDates = currentTask.getPlannedStart().plusDays(m);
                            if(day.equals(taskDates)){
                                System.out.print("|==========|");//12 pixels per day ?
                                print = false;
                            }
                        }
                        if(print){
                            System.out.print("            ");//12 pixels per day ?
                        }
                    }
                    System.out.println();

                    //Actual plot
                    if(currentTask.getActualStart() != null){
                        printEmpty(smallestIndent + 4);
                        System.out.print("|");
                        for (long j = 0; j < duration; j++){// project tasks duration
                            LocalDate day = tasksStartDate.plusDays(j);

                            LocalDate thisStart = currentTask.getActualStart();
                            LocalDate thisFinish = currentTask.getActualFinish();

                            long actualDuration = ChronoUnit.DAYS.between(thisStart, thisFinish) + 1;

                            print = true;
                            for(int m = 0; m < actualDuration; m++){
                                LocalDate taskDates = thisStart.plusDays(m);
                                if(day.equals(taskDates)){
                                    System.out.print("|**********|");//12 pixels per day ?
                                    print = false;
                                }
                            }
                            if(print){
                                System.out.print("            ");//12 pixels per day ?
                            }
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
                //Milestones
                ArrayList<Milestone> milestones1 = foundProject.getMilestones();
                for(int i = 0; i < milestones1.size();i++){
                    Milestone currentMilestone = milestones1.get(i);
                    int MilestoneIndent = smallestIndent - currentMilestone.getName().length() - currentMilestone.getId().length() + 4 - 2;
                    System.out.print(currentMilestone.getName()+"(" + currentMilestone.getId()+")");
                    printEmpty(MilestoneIndent);
                    System.out.print("|");
                    boolean print;
                    for (int j = 0; j < duration; j++){// project tasks duration
                        LocalDate day = tasksStartDate.plusDays(j);
                        print = true;
                        LocalDate milestoneDate = currentMilestone.getDate();
                        if(day.equals(milestoneDate)){
                            System.out.print("|##########|");//12 pixels per day ?
                            print = false;

                        }
                        if(print){
                            System.out.print("            ");//12 pixels per day ?
                        }
                    }
                    System.out.println();
                    System.out.println();
                }
                for(int i= 0;i<(smallestIndent + 5);i++){//horizontal line1
                    System.out.print("_");
                }
                for (long i = 0; i < duration; i++){//horizontal line2
                    System.out.print("____________");
                }
                //Legend
                System.out.println();
                System.out.println("                 Legend:");
                System.out.println("                     |==========| : Planned Tasks");
                System.out.println();
                System.out.println("                     |**********| : Actual Tasks");
                System.out.println();
                System.out.println("                     |##########| : Milestones");
                //
            }else{
                System.out.println("There are no tasks in the project");
            }
        }else {
            System.out.println("There are no projects to show");
        }
        pause();
    }

    public void monitorProgress(){
        Project foundProject = retrieveProject();
        if(foundProject != null){
            System.out.println();
            double plannedSum = totalPlannedHours(foundProject);
            double actualSum = totalActualHours(foundProject);

            double plannedBudget = Math.round((plannedSum * 225.0)*100)/100.0;
            double actualCost = Math.round((actualSum * 225.0)*100)/100.0;

            LocalDate today = LocalDate.now();
            LocalDate tasksStartDate = tasksStartAndFinishDates("start",foundProject.getTasks());
            LocalDate tasksFinishDate = tasksStartAndFinishDates("finish",foundProject.getTasks());

            //project tasks total duration
            double projectDuration = ChronoUnit.DAYS.between(tasksStartDate, tasksFinishDate) + 1;
            double durationTillToday = ChronoUnit.DAYS.between(tasksStartDate, today) + 1;
            double ExecutedProgress = actualCost/plannedBudget;
            double scheduleProgress = durationTillToday/projectDuration;
            double earnedValue = (Math.round((plannedBudget * scheduleProgress))*100)/100.0;

            System.out.println("Project budget = " + plannedBudget);
            System.out.println("Project cost = " + actualCost);
            System.out.println("Earned Value = " + earnedValue);
            System.out.println("Program Executed Progress = " + Math.round(((ExecutedProgress)*100.0)*100)/100.0 +" %"); //this is only monetary wise
            System.out.println("Program ProjectManagementTool Progress = " + Math.round(((scheduleProgress)*100.0)*100)/100.0 +" %"); //this is time wise

            SystemStore drake = new MiniProject.SystemStore();
            drake.registerScheduleVariance(plannedBudget, earnedValue, plannedSum, actualSum, foundProject.getProjectID() );
            drake.registerCostVariance(plannedBudget, earnedValue, plannedSum, actualSum, actualCost, foundProject.getProjectID());
            System.out.println();
            drake.printAllFinances();
        }
        pause();
    }

    public void monitorTimeSpent(){
        System.out.println("To Be coded later");
    }

    public void monitorParticipation(){
        System.out.println("To Be coded later");
    }

    public void monitorRisk(){
        new RiskMatrix().runRisk();
    }

    public void editInfo(){
        System.out.println("To Be coded later");
    }

    //

    public boolean teamMemberExists(Project project, String name){

        for(int i = 0; i < project.getTeamMembers().size(); i++){
            if(project.getTeamMembers().get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public TeamMember retrieveTeamMember(Project project, String id){
        ArrayList<TeamMember> teamMembers = project.getTeamMembers();

        if(teamMembers != null){
            for(int i = 0; i < teamMembers.size(); i++){
                if(teamMembers.get(i).getId().equals(id)){
                    return teamMembers.get(i);
                }
            }
        }else{
            System.out.println("There are no registered team members ");
        }
        return null;
    }

    public int typeOfTask(){
        int taskChoice;
        System.out.println("Choose task type:");
        System.out.println("    1. Stand Alone task independent of other tasks "); //start date & (duration or finish date)
        System.out.println("    2. Dependent task on other tasks "); //Connected to task, type of connection, duration of connection
        do{
            System.out.print("Which task type option? 1 or 2 ? ");
            taskChoice = new KeyboardInput().Int();
        }while ((taskChoice != 1 && taskChoice != 2));

        return taskChoice;
    }

    public  void updateDates(Task task, LocalDate ps, LocalDate pf, LocalDate as, LocalDate af){
        task.setPlannedStart(ps);
        task.setPlannedFinish(pf);
        task.setActualStart(as);
        task.setActualFinish(af);
        //
    }

    public void checkWithProjectTimes(Project project){
        LocalDate projectStart = project.getStartDate();
        LocalDate projectFinish = project.getFinishDate();

        LocalDate projectTasksStartDate = tasksStartAndFinishDates("start", project.getTasks());
        LocalDate projectTasksFinishDate = tasksStartAndFinishDates("finish", project.getTasks());

        if(projectStart != null){
            if(projectTasksStartDate.isBefore(projectStart)){
                System.out.println();
                System.out.println("Warning !!! there exists a task that starts before the project start date. You may need to update a data!");
            }
        }

        if(projectFinish != null){
            System.out.println();
            if(projectTasksFinishDate.isAfter(projectFinish)){
                System.out.println("Warning !!! there exists a task that finishes after the project finish date. You may need to update a data!");
            }
        }

        if(projectStart == null){
            project.setStartDate(projectTasksStartDate);
        }

        if(projectFinish == null){
            project.setFinishDate(projectTasksFinishDate);
        }

        long duration = ChronoUnit.DAYS.between(project.getStartDate(), project.getFinishDate()) + 1;
        project.setDuration(duration);
    }

    public int readLengthOfTask(){
        int taskChoice;
        System.out.println("How is the length of the task defined by: ");
        System.out.println("    1. Duration ");
        System.out.println("    2. Finish date ");
        do{
            System.out.print("Which option? 1 or 2 ? ");
            taskChoice = new KeyboardInput().Int();
        }while ((taskChoice != 1 && taskChoice != 2));

        return taskChoice;
    }

    public String readConnectivityType(){
        System.out.print("Which type of connectivity does the task has? Is it SS, FS, SF, or FF ? ");
        String connectivityType = new KeyboardInput().Line();
        return new DataEvaluator().connectivityType(connectivityType);
    }

    public String readNewTaskID(Project project){
        String taskID;
        boolean repeatLoop;
        do{
            repeatLoop = false;
            System.out.print("Enter ID of the new task ");
            taskID = new KeyboardInput().Line();
            if (checkTaskExistence(taskID,project) != null){
                System.out.println();
                System.out.println("Task ID is already registered to another Task. Try again");
                repeatLoop = true;
            }
        }while(repeatLoop);

        return taskID;
    }

    public String readNewMilestoneID(Project project){
        String milestoneID;
        boolean repeatLoop;
        do{
            repeatLoop = false;
            System.out.print("Enter ID of the new Milestone ");
            milestoneID = new KeyboardInput().Line();
            if (checkMilestoneExistence(milestoneID, project) != null){
                System.out.println();
                System.out.println("Task ID is already registered to another Task. Try again");
                repeatLoop = true;
            }
        }while(repeatLoop);

        return milestoneID;
    }

    public String readExistingTaskID(Project project){
        String taskID;
        boolean repeatLoop;
        do{
            repeatLoop = false;
            System.out.print("Enter ID of existing task ");
            taskID = new KeyboardInput().Line();
            if (retrieveTask(taskID,project) == null){
                System.out.println();
                System.out.println("Task ID is not yet registered.");
                repeatLoop = true;
            }
        }while(repeatLoop);

        return taskID;
    }

    public Task retrieveTask(String taskID, Project project){
        Task foundTask = null;
        if (project.getTasks().size() != 0){
            ArrayList<Task> tasksCopy = project.getTasks();
            for (int i = 0; i < tasksCopy.size(); i++){
                if (tasksCopy.get(i).getId().equals(taskID)){
                    foundTask = tasksCopy.get(i);
                }
            }
        }else{
            System.out.println("Currently, there are no tasks registered yet");
        }
        System.out.println();
        return foundTask;
    }

    public Task checkTaskExistence(String taskID, Project project){
        Task foundTask = null;
        if (project.getTasks().size() != 0){
            ArrayList<Task> tasksCopy = project.getTasks();
            for (int i = 0; i < tasksCopy.size(); i++){
                if (tasksCopy.get(i).getId().equals(taskID)){
                    foundTask = tasksCopy.get(i);
                }
            }
        }
        System.out.println();
        return foundTask;
    }

    public Milestone checkMilestoneExistence(String milestoneID, Project project){
        Milestone foundMilestone = null;
        ArrayList<Milestone> milestones = project.getMilestones();
        if (milestones.size() != 0){
            ArrayList<Milestone> milestonesCopy = milestones;
            for (int i = 0; i < milestonesCopy.size(); i++){
                if (milestonesCopy.get(i).getId().equals(milestoneID)){
                    foundMilestone = milestonesCopy.get(i);
                }
            }
        }
        System.out.println();
        return foundMilestone;
    }

    public Project retrieveProject(){
        boolean continueLooping;
        Project foundProject = null;
        String projectID;
        if(projects != null){
            System.out.println();
            System.out.println("The following are the projects currently registered:");
            System.out.println("Project ID:               Project Name: " );

            for(int x = 0; x < projects.size(); x++){
                System.out.println("    " + projects.get(x).getProjectID()+ "                      " + projects.get(x).getName());
            }

            System.out.println();
            System.out.print("Enter a project ID number from above ");

            do{
                continueLooping = false;
                projectID = new KeyboardInput().Line();

                for(int i = 0; i < projects.size(); i++){
                    if (projectID.equals(projects.get(i).getProjectID())){
                        foundProject = projects.get(i);
                    }else {
                        continueLooping = true;
                    }
                }

                if(continueLooping){
                    System.out.println();
                    System.out.println("Incorrect choice, try again !");
                    System.out.print("Enter a correct project ID number");
                }
            } while (continueLooping);
        }
        return foundProject;
    }

    public boolean completenessCheck(Task task){
        int outOfThree = 0;
        boolean complete = false;
        LocalDate today = LocalDate.now();

        if(task.getPlannedStart() != null){
            outOfThree++;
        }

        if(task.getPlannedDuration() != 0){
            outOfThree++;
        }

        if(task.getPlannedFinish() != null){
            outOfThree++;
        }

        if(task.getActualStart() != null){
            if(!task.getStatusOfTask()){
                task.setActualFinish(today);
            }
        }

        if(task.getActualFinish() != null){
            task.setActualDuration(ChronoUnit.DAYS.between(task.getActualStart(), task.getActualFinish()) + 1);
        }

        if(outOfThree == 2){
            if(task.getPlannedStart() != null){
                if(task.getPlannedDuration() != 0){
                    LocalDate finish = task.getPlannedStart().plusDays(task.getPlannedDuration());
                    task.setPlannedFinish(finish);
                }else{

                    long duration = ChronoUnit.DAYS.between(task.getPlannedStart(), task.getPlannedFinish()) + 1;

                    task.setPlannedDuration(duration);
                }
            }else {
                LocalDate start = task.getPlannedFinish().minusDays(task.getPlannedDuration());
                task.setPlannedStart(start);
            }
            complete = true;
        }

        if (outOfThree == 3){
            complete = true;
        }
        return complete;
    }

    public double totalPlannedHours(Project foundProject){
        double totalHours = 0.0;
        if (foundProject != null){
            if(foundProject.getTasks() != null){
                int numberOfTasks = foundProject.getTasks().size();
                for(int i = 0; i < numberOfTasks; i++){
                    Task task = foundProject.getTasks().get(i);
                    ArrayList<ManpowerAllocation> allocations = task.getPlannedManpower();
                    for(int j = 0; j < allocations.size(); j++){
                        totalHours = totalHours + allocations.get(j).getWorkHours();
                    }
                }
                System.out.println("Total planned hours = " + totalHours);
            }else{
                System.out.println("There are no tasks in the project");
            }
        }else {
            System.out.println("There are no projects to show");
        }
        return totalHours;
    }

    public double totalActualHours(Project foundProject){
        double totalHours = 0.0;
        if (foundProject != null){
            if(foundProject.getTasks() != null){
                int numberOfTasks = foundProject.getTasks().size();
                for(int i = 0; i < numberOfTasks; i++){
                    Task task = foundProject.getTasks().get(i);
                    ArrayList<TeamMemberAllocation> teamMembers = task.getActualTeamMembers();
                    for(int j = 0; j < teamMembers.size(); j++){
                        totalHours = totalHours + teamMembers.get(j).getWorkHours();
                    }
                }
                System.out.println("Total Actual hours = " + totalHours);
            }else{
                System.out.println("There are no tasks in the project");
            }
        }else {
            System.out.println("There are no projects to show");
        }
        return totalHours;
    }

    public void printEmpty(int space){
        for(int i = 0; i < space; i++){
            System.out.print(" ");
        }
    }

    public void pause (){
        System.out.println();
        System.out.println("Enter to continue ... ");
        new KeyboardInput().enter();
    }

    public String readQualification(){
        int choice;
        String result = "";
        do {
            System.out.println("List of qualifications:");
            System.out.println("    1. Software Developer");
            System.out.println("    2. Video Game Developer");
            System.out.println("    3. Software Engineer");
            System.out.println("    4. Requirements Engineer");
            System.out.println("    5. Test Engineer");
            System.out.println("    6. Network Administrator");
            System.out.println();
            System.out.print("Enter qualification number ");
            choice = new KeyboardInput().positiveInt();
        }while(choice > 6);

        switch (choice){
            case 1:
                result = "Software Developer";
                break;
            case 2:
                result = "Video Game Developer";
                break;
            case 3:
                result = "Software Engineer";
                break;
            case 4:
                result = "Requirements Engineer";
                break;
            case 5:
                result = "Test Engineer";
                break;
            case 6:
                result = "Network Administrator";
                break;
            default:
                System.out.println("You may want to choose the correct number !");
                break;
        }
        return result;
    }

    public void readStoredFile(){
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.parse("2018-11-15");
        Project p1 = new Project("Project Management Tool Development","1", startDate);
        projects.add(p1);

        Task t1 = new Task("General Tasks","1",1);
        Task t2 = new Task("Cost Related","2",1);
        Task t3 = new Task("Risk Matrix","3",1);
        Task t4 = new Task("Participation on tasks","4",1);
        Task t5 = new Task("Time spent on project","5",1);
        Task t6 = new Task("Project schedule","6",1);

        Milestone milestone1 = new Milestone("Executing on Console","1",LocalDate.parse("2018-12-10"));
        Milestone milestone2 = new Milestone("Executing using json","2",LocalDate.parse("2018-12-21"));
        Milestone milestone3 = new Milestone("Executing graphically","3",LocalDate.parse("2019-01-07"));

        p1.getMilestones().add(milestone1);
        p1.getMilestones().add(milestone2);
        p1.getMilestones().add(milestone3);

        p1.getTasks().add(t1);
        p1.getTasks().add(t2);
        p1.getTasks().add(t3);
        p1.getTasks().add(t4);
        p1.getTasks().add(t5);
        p1.getTasks().add(t6);

        t1.setPlannedStart(LocalDate.parse("2018-11-16"));
        t1.setPlannedFinish(LocalDate.parse("2019-01-20"));

        t2.setPlannedStart(LocalDate.parse("2018-11-26"));
        t2.setPlannedFinish(LocalDate.parse("2019-01-11"));

        t3.setPlannedStart(LocalDate.parse("2018-11-26"));
        t3.setPlannedFinish(LocalDate.parse("2019-01-11"));

        t4.setPlannedStart(LocalDate.parse("2018-11-26"));
        t4.setPlannedFinish(LocalDate.parse("2019-01-11"));

        t5.setPlannedStart(LocalDate.parse("2018-11-26"));
        t5.setPlannedFinish(LocalDate.parse("2019-01-11"));

        t6.setPlannedStart(LocalDate.parse("2018-11-26"));
        t6.setPlannedFinish(LocalDate.parse("2019-01-11"));

        //
        t1.setActualStart(LocalDate.parse("2018-11-16"));
        t1.setActualFinish(today);

        t2.setActualStart(LocalDate.parse("2018-11-26"));
        t2.setActualFinish(today);

        t3.setActualStart(LocalDate.parse("2018-11-26"));
        t3.setActualFinish(today);

        t4.setActualStart(LocalDate.parse("2018-11-26"));
        t4.setActualFinish(today);

        t5.setActualStart(LocalDate.parse("2018-11-26"));
        t5.setActualFinish(today);

        t6.setActualStart(LocalDate.parse("2018-11-26"));
        t6.setActualFinish(today);

        completenessCheck(t1);
        completenessCheck(t2);
        completenessCheck(t3);
        completenessCheck(t4);
        completenessCheck(t5);
        completenessCheck(t6);

        t1.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),207,LocalDate.parse("2018-11-16")));
        t1.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),207,LocalDate.parse("2018-12-10")));
        t1.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),208,LocalDate.parse("2018-12-26")));
        t1.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),208,LocalDate.parse("2019-01-20")));

        t2.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),37,LocalDate.parse("2018-11-26")));
        t2.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2018-12-06")));
        t2.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2018-12-26")));
        t2.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2019-01-11")));

        t3.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),37,LocalDate.parse("2018-11-26")));
        t3.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2018-12-06")));
        t3.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2018-12-26")));
        t3.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2019-01-11")));

        t4.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),37,LocalDate.parse("2018-11-26")));
        t4.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2018-12-06")));
        t4.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2018-12-26")));
        t4.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2019-01-11")));

        t5.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),37,LocalDate.parse("2018-11-26")));
        t5.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2018-12-06")));
        t5.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2018-12-26")));
        t5.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2019-01-11")));

        t6.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),37,LocalDate.parse("2018-11-26")));
        t6.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2018-12-06")));
        t6.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2018-12-26")));
        t6.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),38,LocalDate.parse("2019-01-11")));

        TeamMember team1 = new TeamMember("Armin Ghoroghi","1","Software Developer");
        TeamMember team2 = new TeamMember("James Wagabaza","2","Software Developer");
        TeamMember team3 = new TeamMember("Osman Osman","3","Software Developer");
        TeamMember team4 = new TeamMember("Hamidreza Yaghoobzadeh","4","Software Developer");
        TeamMember team5 = new TeamMember("Eyuell Hailemichael","5","Software Developer");

        p1.getTeamMembers().add(team1);
        p1.getTeamMembers().add(team2);
        p1.getTeamMembers().add(team3);
        p1.getTeamMembers().add(team4);
        p1.getTeamMembers().add(team5);

        long numberOfDays = ChronoUnit.DAYS.between(LocalDate.parse("2018-11-26"), today) + 1;

        //the hours here are just to make different times for each team member
        t2.getActualTeamMembers().add(new TeamMemberAllocation(team1,numberOfDays * 3.1,today));
        t3.getActualTeamMembers().add(new TeamMemberAllocation(team2,numberOfDays * 3.2,today));
        t4.getActualTeamMembers().add(new TeamMemberAllocation(team3,numberOfDays * 3.3,today));
        t5.getActualTeamMembers().add(new TeamMemberAllocation(team4,numberOfDays * 3.4,today));
        t6.getActualTeamMembers().add(new TeamMemberAllocation(team5,numberOfDays * 3.5,today));
    }

    public LocalDate tasksStartAndFinishDates (String startOrFinish, ArrayList<Task> tasks){
        int numberOfTasks = tasks.size();
        ArrayList<LocalDate> starts = new ArrayList<>();
        ArrayList<LocalDate> finishes = new ArrayList<>();
        LocalDate plannedStartDate, actualStartDate;
        LocalDate plannedFinishDate, actualFinishDate;
        LocalDate result = null;

        for (int i = 0; i < numberOfTasks; i++){
            Task tas = tasks.get(i);

            plannedStartDate = tas.getPlannedStart();
            plannedFinishDate = tas.getPlannedFinish();

            if(tas.getActualStart() != null){
                actualStartDate = tas.getActualStart();
                starts.add(actualStartDate);
            }

            if(tas.getActualFinish() != null){
                actualFinishDate = tas.getActualFinish();
                finishes.add(actualFinishDate);
            }

            starts.add(plannedStartDate);
            finishes.add(plannedFinishDate);
        }

        if(startOrFinish.equals("start") && starts.size() > 0){
            Collections.sort(starts);
            result = starts.get(0);
        }else if(startOrFinish.equals("finish") && finishes.size() > 0){
            int numberOfFinishes = finishes.size();
            Collections.sort(finishes);
            result = finishes.get(numberOfFinishes - 1);
        }
        return result;
    }

    public boolean checkTasksPlannedCompleteness(ArrayList<Task> existingTasks){
        for(Task task:existingTasks){
            if(!completenessCheck(task)){
                return false;
            }
        }
        return true;
    }

    public boolean readActualTaskStatus(){
        boolean repeat;
        int option;
        System.out.println("What is the status of the task ?");
        do{
            repeat = false;
            System.out.println("    1. Completed ");
            System.out.println("    2. Active ");
            System.out.println("Enter status option");
            option = new KeyboardInput().positiveInt();
            if(option > 2){
                repeat = true;
            }
        } while (repeat);
        if (option == 1){
            return true;
        } else{
            return false;
        }
    }

    public void writeToJsonFile(){

        try {
            FileWriter file = new FileWriter("MiniProject/MiniProject.json");
            Gson gsonStructured = new GsonBuilder().setPrettyPrinting().create();

            file.write(gsonStructured.toJson(projects));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}