package MiniProject;

import com.google.gson.Gson; //to convert from object to json
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
//import com.sun.xml.internal.bind.v2.model.core.ID;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Map.Entry;

//the project system Main class
public class ProjectManagementTool {

    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String RED = "\033[0;31m";     // RED
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN

    //underline
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK

    // Background
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE


    private static final int FIRST = 0;
    private static final int DATE_SUBTRACTION_CORRECTION = 1;
    private static final double SALARY = 189.0;
    private static final double PAY = 255.0;

    //the system has projects but we work on one project.
    // only for json we use the object
    private ArrayList<Project> projects;

    //constructor
    public ProjectManagementTool(){

        this.projects = new ArrayList<>();
    }

    //getter for the projects
    public ArrayList<Project> getProjects() {
        return projects;
    }

    //Gateway to the system
    public static void main(String [] args) throws Exception {
        System.out.println();
        System.out.println(CYAN_BRIGHT + "This program works on Project Management Tools");
        ProjectManagementTool start = new ProjectManagementTool();
        start.run();
    }

    //Main Menu of the program
    public void printMenuOption(){
        System.out.println(CYAN_BRIGHT + "==============================================");
        System.out.println("1. Register Project");
        System.out.println("2. Register Tasks and Milestones");
        System.out.println("3. Register Team members");
        System.out.println("4. Assign Dates to Tasks");
        System.out.println("5. Assign Manpower to Tasks");
        System.out.println("6. Register Actual Resources to Tasks");
        System.out.println("7. Display All projects");
        System.out.println("8. Display Tasks and Milestones");
        System.out.println("9. Display Team Members");
        System.out.println("10. Display Project Schedule");
        System.out.println("11. Display Resource Histogram");
        System.out.println("12. Monitor Finances");
        System.out.println("13. Monitor Time Spent");
        System.out.println("14. Monitor Participation");
        System.out.println("15. Monitor Risk");
        System.out.println("16. Edit Information");
        System.out.println("17. QUIT Program");
        System.out.println("==============================================");
        System.out.println();
    }

    //Second switch for option16 in menu
    public void printMenuOptions(){
        System.out.println("=========================================");
        System.out.println("1. Edit Project Information");
        System.out.println("2. Edit Task Information");
        System.out.println("3. Edit Team Member Information");
        System.out.println("4. Edit Team Member Allocation");
        System.out.println("5. Return");
        System.out.println("=========================================");
        System.out.println();
    }//Armin


    //the gate way to the menu
    public void run() throws Exception {
        int optionChoice;
        final int REGISTER_PROJECT = 1;
        final int REGISTER_TASKS_MILESTONES = 2;
        final int REGISTER_TEAM_MEMBERS = 3;
        final int ASSIGN_DATES = 4;
        final int ASSIGN_MANPOWER = 5;
        final int REGISTER_ACTUAL_DATA = 6;
        final int PRINT_ALL_PROJECTS = 7;
        final int PRINT_TASKS_MILESTONES = 8;
        final int PRINT_TEAM_MEMBERS = 9;
        final int PRINT_PLANED_ACTUAL_SCHEDULE = 10;
        final int PRINT_RESOURCE_HISTOGRAM = 11;
        final int MONITOR_FINANCES = 12;
        final int MONITOR_TIME_SPENT = 13;
        final int MONITOR_PARTICIPATION = 14;
        final int MONITOR_RISK = 15;
        final int EDIT_INFO = 16;
        final int QUIT = 17;

        readFromSystemClass(); //to read data by initiating a project with set values in the internal system
        //readFromJsonFile(); //to read data from stored json file

        //checking all tasks are complete with major timeline info
        projectCompletenessCheck();

        //menu
        do {
            printMenuOption();
            System.out.print("Choose menu option ");
            optionChoice = new KeyboardInput().Int();     //keyboard input for the project

            switch (optionChoice){
                case REGISTER_PROJECT:
                    registerProject();
                    break;

                case REGISTER_TASKS_MILESTONES:
                    registerTasksAndMilestones();
                    break;

                case REGISTER_TEAM_MEMBERS:
                    registerTeamMember();
                    break;

                case ASSIGN_DATES:
                    assignDates();
                    break;

                case ASSIGN_MANPOWER:
                    assignManPower();
                    break;

                case REGISTER_ACTUAL_DATA:
                    registerActualData();
                    break;

                case PRINT_ALL_PROJECTS:
                    printProjects();
                    break;

                case PRINT_TASKS_MILESTONES:
                    printTasksAndMilestones();
                    break;

                case PRINT_TEAM_MEMBERS:
                    printTeamMembers();
                    break;

                case PRINT_PLANED_ACTUAL_SCHEDULE:
                    printPlannedAndActualSchedule();
                    break;

                case PRINT_RESOURCE_HISTOGRAM:
                    printResourceHistogram();
                    break;

                case MONITOR_FINANCES:
                    monitorCosts();
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

        writeProjectToJsonFile(); //Writes to project data store in Json file
    }//Eyuell & Hamid

    //to register projects with error handling
    public void registerProject(){
        System.out.println();
        System.out.print("Enter name of a project ");
        String projectName = new KeyboardInput().Line();

        while (checkProjectName(projectName)){
            System.out.print("Project Name is already used. Enter another name : ");
            projectName = new KeyboardInput().Line();
        }

        System.out.println();
        String projectID;
        do {
            System.out.print("Enter project ID number ");
            projectID = new KeyboardInput().Line();
            if (projectExists(projectID)) {
                System.out.println("A project with this id already exists");
            }
        } while (projectExists((projectID)));

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
        } while (choice > SECOND_OPTION);

        LocalDate projectStartDate = null;
        LocalDate projectFinishDate = null;
        long duration = 0;

        if(choice != SECOND_OPTION){ //dates now
            System.out.println("Enter the Start date of the project: ");
            projectStartDate = new DataEvaluator().readDate();

            System.out.println("Enter the Finish date of the project: ");
            projectFinishDate = new DataEvaluator().readDate();

            duration = ChronoUnit.DAYS.between(projectStartDate, projectFinishDate) + DATE_SUBTRACTION_CORRECTION;
        }

        Project project = new Project(projectName, projectID, projectStartDate);
        projects.add(project);
        project.setFinishDate(projectFinishDate);
        project.setDuration(duration);
        System.out.println();
        System.out.println("Project '" + projectName + "' is registered successfully !");
        pause();
    }//Eyuell & Hamid

    //method to register tasks and Milestones with error handling
    public void registerTasksAndMilestones(){

        Project foundProject = projects.get(FIRST);
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
            //if there is a choice to register milestones now
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
    }//Eyuell

    //register team members with error handling
    public void registerTeamMember(){

        if(projects != null){
            Project foundProject = projects.get(FIRST);
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

        } else {
            System.out.println("The project does not exist");
        }
        pause();
    }//Eyuell

    //assign start, finish and duration of tasks
    public void assignDates(){
        int STAND_ALONE = 1;
        int DEPENDANT = 2;
        int DURATION = 1;
        int START_FINISH_DATE = 2;
        if(projects != null){
            Project foundProject = projects.get(FIRST);
            ArrayList<Task> allTasks = foundProject.getTasks();

            if (allTasks != null){
                LocalDate taskStartDate;
                LocalDate taskFinishDate;
                long taskPlannedDuration;
                int numberOfTasks = allTasks.size();

                for (int i = 0 ; i < numberOfTasks; i++){
                    Task currentTask = allTasks.get(i);
                    boolean completenessCheck = completenessCheck(currentTask);
                    if(! completenessCheck){ //if not complete
                        int taskType = currentTask.getTypeOfTask();

                        System.out.println();
                        System.out.println("Data for task '" + currentTask.getName() + "' : ");
                        if(taskType == STAND_ALONE){ //stand alone
                            System.out.println("Enter the start date of the task ");
                            taskStartDate = new DataEvaluator().readDate();
                            currentTask.setPlannedStart(taskStartDate);

                            int lengthOfTask = readLengthOfTask();

                            if(lengthOfTask == DURATION){
                                System.out.print("Enter the planned duration of the task ");
                                taskPlannedDuration = new KeyboardInput().positiveInt();
                                currentTask.setPlannedDuration(taskPlannedDuration);
                                LocalDate finishDate = currentTask.getPlannedStart().plusDays(taskPlannedDuration);
                                currentTask.setPlannedFinish(finishDate);
                            }else if(lengthOfTask == START_FINISH_DATE){
                                System.out.println("Enter the finish date of the task ");
                                taskFinishDate = new DataEvaluator().readDate();
                                currentTask.setPlannedFinish(taskFinishDate);

                                long duration = ChronoUnit.DAYS.between(currentTask.getPlannedStart(), taskFinishDate) + DATE_SUBTRACTION_CORRECTION;

                                currentTask.setPlannedDuration(duration);
                            }
                        } else if (taskType == DEPENDANT){ //dependant on other task
                            String connectivityType;
                            long connectivityDuration;
                            boolean repeat;

                            do{
                                repeat = false;
                                Task foundTask;
                                int ONE = 1;
                                System.out.println("How many connectivity does this task has with other tasks ?");
                                int numberOfConnectivity = new KeyboardInput().positiveInt();
                                if (numberOfConnectivity >= ONE){
                                    for(int j = ONE; j <= numberOfConnectivity; j++){ // if  there is connectivity, it should start from one
                                        System.out.println("Connectivity " + ( j ) + ": ");
                                        System.out.println("On which task does the current task depend on ? ");
                                        String toBeConnectedToTaskID = readExistingTaskID(foundProject);
                                        foundTask = retrieveTask(toBeConnectedToTaskID, foundProject);

                                        connectivityType = readConnectivityType();

                                        System.out.print("Enter the duration of connectivity. (It could be negative if applicable) ");
                                        connectivityDuration = new KeyboardInput().Int();

                                        currentTask.getConnectivity().add(new Connectivity(foundTask, connectivityType, connectivityDuration));
                                    }
                                }

                                LocalDate startDate = new DataEvaluator().extractConnectivityDate("start",currentTask.getConnectivity() );
                                LocalDate finishDate = new DataEvaluator().extractConnectivityDate("finish",currentTask.getConnectivity() );

                                if(startDate != null &&  finishDate != null){
                                    if(finishDate.isAfter(startDate)){

                                        long duration = ChronoUnit.DAYS.between(startDate, finishDate) + DATE_SUBTRACTION_CORRECTION;

                                        currentTask.setPlannedStart(startDate);
                                        currentTask.setPlannedFinish(finishDate);
                                        currentTask.setPlannedDuration(duration);
                                        repeat = false;
                                    }else {
                                        System.out.println("There is an error on connectivity that results an end date before a start date. Correct the data again");
                                        repeat = true;
                                    }
                                }else {

                                    int lengthOfTask = readLengthOfTask();
                                    if(lengthOfTask == DURATION){
                                        System.out.print("Enter the planned duration of the task : " + currentTask.getName() + " ");
                                        taskPlannedDuration = new KeyboardInput().positiveInt();
                                        currentTask.setPlannedDuration(taskPlannedDuration);
                                        if(startDate != null){
                                            finishDate = startDate.plusDays(taskPlannedDuration);
                                            currentTask.setPlannedFinish(finishDate);
                                        } else {
                                            startDate = finishDate.minusDays(taskPlannedDuration);
                                            currentTask.setPlannedStart(startDate);
                                        }
                                    } else if(lengthOfTask == START_FINISH_DATE){
                                        if(startDate != null){
                                            System.out.println("Enter the Finish date of the task : " + currentTask.getName() + " ");
                                            LocalDate fDate = new DataEvaluator().readDate();

                                            while (fDate.isBefore(startDate)){
                                                System.out.println("Finish date of the task should not be before a Start date. ");
                                                System.out.print("Enter the finish date correctly ");
                                                fDate = new DataEvaluator().readDate();
                                            }

                                            currentTask.setPlannedFinish(fDate);
                                            currentTask.setPlannedStart(startDate);
                                            long duration = ChronoUnit.DAYS.between(startDate, fDate) + DATE_SUBTRACTION_CORRECTION;
                                            currentTask.setPlannedDuration(duration);

                                        } else {
                                            System.out.println("Enter the Start date of the task : " + currentTask.getName() + " ");
                                            LocalDate sDate = new DataEvaluator().readDate();

                                            while (sDate.isAfter(finishDate)){
                                                System.out.println("Start date of the task should not be after a Finish date. ");
                                                System.out.print("Enter the Start date correctly ");
                                                sDate = new DataEvaluator().readDate();
                                            }

                                            currentTask.setPlannedStart(sDate);
                                            currentTask.setPlannedFinish(finishDate);
                                            long duration = ChronoUnit.DAYS.between(sDate, finishDate) + DATE_SUBTRACTION_CORRECTION;
                                            currentTask.setPlannedDuration(duration);
                                        }
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
    }//Eyuell

    public void assignManPower(){

        if (projects != null){
            Project foundProject = projects.get(FIRST);
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
                            System.out.print("    Enter * to escape to another day ");
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
    }//Eyuell

    public void registerActualData(){
        LocalDate today = LocalDate.now();

        if (projects != null ){
            Project foundProject = projects.get(FIRST);
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
    }//Eyuell


    //Print tasks or milestones
    public void printTasksAndMilestones() {
        int option;
        boolean input;

        do {
            System.out.println("What do you wish to print?");
            System.out.println("1. Tasks ");
            System.out.println("2. Milestones");
            System.out.println("3. Return");
            option = new KeyboardInput().Int();

            if (option == 1) {
                printTasks();
                input = false;

            } else if (option == 2) {
                printMileStones();
                input = false;

            } else if (option == 3) {
                //Return
                System.out.println();
                input = false;

            } else {
                System.out.println("option must be 1-3. ");
                input = true;
            }

        }while (input);
    }//Armin

    //print only tasks
    public void printTasks(){
        int option;
        boolean input;

        do {
            System.out.println("What do you wish to print?");
            System.out.println("1. All Tasks ");
            System.out.println("2. Specific Task");
            System.out.println("3. Return");
            option = new KeyboardInput().Int();

            if (option == 1) {
                printAllTasksAndMilestones();
                input = false;

            } else if (option == 2) {
                printSpecificTaskMilestones();
                input = false;

            } else if (option == 3) {
                //Return
                System.out.println();
                input = false;

            } else {
                System.out.println("option must be 1-3. ");
                input = true;
            }

        }while (input);
    }//Armin


    public void printAllTasksAndMilestones(){
        Project currentProject = projects.get(FIRST);
        ArrayList<Task> tasks = currentProject.getTasks();
        String option;
        boolean error;
        if(tasks != null) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("Name of task: " + tasks.get(i).getName());
            System.out.println("Planned start date: " + tasks.get(i).getPlannedStart());
            System.out.println("Planned finish date: " + tasks.get(i).getPlannedFinish());

            System.out.println("Actual date of start: " + tasks.get(i).getActualStart());
            System.out.println("Actual End date: " + tasks.get(i).getActualFinish());
            System.out.println("Planned duration of task is " + tasks.get(i).getPlannedDuration() + " days.");
            System.out.println("------------------------------------------------------------");
        }

        do {
            System.out.println("------------------------------------------------------------");
            System.out.println("Do you wish to print milestones?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            option = new KeyboardInput().Line();
            System.out.println("------------------------------------------------------------");

            if (option.equalsIgnoreCase("1") || option.equalsIgnoreCase("yes")) {
                printMileStones();
                error = false;

            } else if (option.equalsIgnoreCase("2") || option.equalsIgnoreCase("no")) {
                System.out.println("Milestones not printed.");
                error = false;
                pause();

            } else {
                System.out.println("Options are 1 or 2");
                System.out.println("----------------------------------------------------------------");
                error = true;
            }
        } while (error);
        }
    }//Armin


    public void printSpecificTaskMilestones() {
        Project currentProject = projects.get(FIRST);

        if (currentProject.getTasks() != null) {
            listTasks();
            String taskID;
            boolean error;
            String option;
            do {
                try {
                    System.out.println("Enter the id of a task");
                    taskID = new KeyboardInput().Line();
                    System.out.println("------------------------------------------------------------");
                    Task foundTask = retrieveTask(taskID, currentProject);

                    System.out.println("Name of task: " + foundTask.getName());
                    System.out.println("Planned start date: " + foundTask.getPlannedStart());
                    System.out.println("Planned finish date: " + foundTask.getPlannedFinish());
                    System.out.println("Actual date of start: " + foundTask.getActualStart());
                    System.out.println("Actual Enddate: " + foundTask.getActualFinish());
                    System.out.println("Planned duration of task is "+foundTask.getPlannedDuration()+" days.");
                    error = false;

                } catch (Exception e) {
                    System.out.println("Input integer, or wrong taskID was inputted.");
                    System.out.println("------------------------------------------------------------");
                    error = true;
                }
                do {
                    System.out.println("------------------------------------------------------------");
                    System.out.println("Do you wish to print milestones?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");

                    option = new KeyboardInput().Line();
                    System.out.println("------------------------------------------------------------");

                    if (option.equalsIgnoreCase("1")||option.equalsIgnoreCase("yes")) {
                        printMileStones();
                        error = false;

                    } else if (option.equalsIgnoreCase("2")||option.equalsIgnoreCase("no")) {
                        System.out.println("Milestones not printed.");
                        error = false;

                    }else{
                        System.out.println("Options are 1 or 2");
                        System.out.println("----------------------------------------------------------------");
                        error=true;
                    }
                }while (error);
            } while (error);
        }else {
            System.out.println("There are no Tasks registered");
            System.out.println();
        }
    }//Armin

    public void printMileStones() {
        int option;
        boolean input;
        do {
            System.out.println("Do you wish to print: ");
            System.out.println("1. All Milestones");
            System.out.println("2. Specific Milestone");
            System.out.println("3. Return");
            option = new KeyboardInput().Int();

            if (option == 1){
                printAllMilestones();
                input = false;

            } else if(option == 2){
                printSpecificMileStones();
                input = false;

            } else {
                System.out.println("Options are 1, 2 or 3.");
                System.out.println("----------------------------------------------------------------");
                input = true;
            }
        }while (input) ;
    }//Armin


    public void printSpecificMileStones() {
        Project currentProject = projects.get(FIRST);
        listMilestones();

        if (currentProject.getMilestones() != null) {
            boolean error;
            String milestoneID;
            do {
                try {
                    System.out.println("Enter the id of a Milestone you wish to print");
                    milestoneID = new KeyboardInput().Line();
                    System.out.println("----------------------------------------------------------------");

                    Milestone foundMilestone = retrieveMilestone(currentProject, milestoneID);
                    System.out.println("Name: " + foundMilestone.getName());
                    System.out.println("Date: " + foundMilestone.getDate());
                    System.out.println("----------------------------------------------------------------");
                    error = false;

                }catch (Exception e){
                    System.out.println("Input integer.");
                    System.out.println("----------------------------------------------------------------");
                    error = true;
                }

            }while (error);
        }else {
            System.out.println("There are no Milestones registered");
            System.out.println();
        }
        pause();
    }//Armin


    public void printAllMilestones() {
        Project currentProject = projects.get(FIRST);
        ArrayList<Milestone> milestones = currentProject.getMilestones();
        if (milestones != null) {

            for (int i = 0; i < milestones.size(); i++) {
                System.out.println("Name: "+milestones.get(i).getName());
                System.out.println("Date: "+milestones.get(i).getDate());
                System.out.println("----------------------------------------------------------------");
            }
        }else {
            System.out.println("There are no Milestones registered");
            System.out.println();
        }
        pause();
    }//Armin


    public void printProjects() {
        int option;
        boolean input;
        do {
            System.out.println("Do you wish to print: ");
            System.out.println("1. All projects");
            System.out.println("2. Specific Project");
            System.out.println("3. Return");
            option = new KeyboardInput().Int();

            if (option == 1) {
                printAllProjects();
                input = false;

            } else if (option == 2) {
                printSpecificProject();
                input = false;

            }else if (option==3){
                System.out.println();
                input = false;

            } else {
                System.out.println("Options are 1, 2 or 3.");
                System.out.println("----------------------------------------------------------------");
                input = true;
            }
        }while (input) ;
    }//Armin

    public void printAllProjects()  {
        if(projects != null){
        System.out.println("Here is a List of all Current projects: ");
        System.out.println("----------------------------------------------------------------");

        for (int i = 0; i < projects.size(); i++) {
            System.out.println("Project ID Assigned: "+projects.get(i).getProjectID());
            System.out.println("Project name: "+projects.get(i).getName());
            System.out.println("Project Start date: "+projects.get(i).getStartDate());
            System.out.println("Project End date: "+projects.get(i).getFinishDate());
            System.out.println("----------------------------------------------------------------");

        }}
        else {
            System.out.println("There are no projects registered");
            System.out.println();
        }
    }//Armin

    public void printSpecificProject(){
        String projectId="";
        boolean error= true;
        do {
            try {
                System.out.println("Enter project id");
                projectId = new MiniProject.KeyboardInput().Line();
                if (!projectExists(projectId)) {
                    System.out.println("This project id does not exist");
                }
                error = false;
            } catch (Exception e) {
                System.out.println("Error, wrong input type");
            }
        }while(error || !projectExists(projectId));
        Project foundProject = retrieveProjectByID(projectId);
        System.out.println("Project ID: " + foundProject.getProjectID());
        System.out.println("Project name: " + foundProject.getName());
        System.out.println("Project start date " + foundProject.getStartDate());
        System.out.println("Project finish date " + foundProject.getFinishDate());
        System.out.println("Project duration " + foundProject.getDuration());
        System.out.println("Project budget " + foundProject.getBudget());
    }

    public void printTeamMembers() {
        int option;
        boolean input;

        do {
            System.out.println("Do you wish to print a specific or All team members? ");
            System.out.println("1. All team members");
            System.out.println("2. Specific team member");
            System.out.println("3. Return");
            System.out.println("---------------------------------");
            option = new KeyboardInput().Int();

            if (option == 1) {
                printAllTeamMembers();
                input = false;

            } else if (option == 2) {
                printSpecificTeamMember();
                input = false;

            } else if(option==3){
                System.out.println();
                input=false;

            }else{
                System.out.println("Options are 1, 2 or 3.");
                System.out.println("----------------------------------------------------------------");
                input=true;
            }
        }while (input);
    }//Armin


    public void printSpecificTeamMember() {
        Project currentProject = projects.get(FIRST);
        listTeamMembers();
        String teamID;

        if (currentProject.getTeamMembers() != null) {
            boolean error;
            for (int i = 0; i < currentProject.getTeamMembers().size(); i++) {
                do {
                    error = false;
                    try {
                        System.out.println("Enter the id of a team member: ");
                        teamID = new KeyboardInput().Line();
                        System.out.println("----------------------------------------------------------------");

                        TeamMember foundTeamMember = retrieveTeamMember(currentProject, teamID);
                        System.out.println("Name: " + foundTeamMember.getName());
                        System.out.println("Profession: " + foundTeamMember.getQualification());
                        System.out.println("Hourly salary: " + foundTeamMember.getHourlyRate());
                        System.out.println("----------------------------------------------------------------");
                        pause();

                    } catch (Exception e) {
                        System.out.println("Input not a number or member does not exist with that ID");
                        System.out.println("----------------------------------------------------------------");

                        error=true;
                    }
                } while (error);
            }
        }else {
            System.out.println("There are no Team Members registered");
            System.out.println();
        }
        pause();

    }//Armin


    public void printAllTeamMembers() {
        Project currentProject = projects.get(FIRST);
        if(currentProject.getTeamMembers() != null){
            System.out.println("Here is a list of all Team members currently registered: ");
            System.out.println("----------------------------------------------------------------");
            for (int i = 0; i < currentProject.getTeamMembers().size(); i++) {
                System.out.println("Name: " + currentProject.getTeamMembers().get(i).getName());
                System.out.println("Profession: " + currentProject.getTeamMembers().get(i).getQualification());
                System.out.println("----------------------------------------------------------------");
            }
        } else {
            System.out.println("There are no Team Members registered");
            System.out.println();
        }
    }//OSMAN


    public void printPlannedAndActualSchedule(){
        int INDENT_ADJUSTMENT1 = 4 - 2;
        int INDENT_ADJUSTMENT2 = 5;
        int INDENT_ADJUSTMENT3 = 4;
        int INDENT_ADJUSTMENT4 = 4 - 16;
        int QUARTER = 4;
        int MIN_INDENT = 20;

        if (projects != null){
            Project foundProject = projects.get(FIRST);
            if(foundProject.getTasks() != null){
                ArrayList<Task> tasks = foundProject.getTasks();
                int taskNameLength = tasks.get(FIRST).getName().length();
                int taskIdLength = tasks.get(FIRST).getId().length();
                int smallestIndent = taskNameLength + taskIdLength;

                ArrayList<String> listOfNames = new ArrayList<>();
                for (int i = 1; i < tasks.size(); i++){//the first index already considered above
                    String indent = tasks.get(i).getName() + tasks.get(i).getId();
                    listOfNames.add(indent);//indent from tasks
                }

                ArrayList<Milestone> milestones = foundProject.getMilestones();
                if(milestones != null){
                    for(int i = 0; i < milestones.size();i++){
                        String indent = milestones.get(i).getName() + milestones.get(i).getId();
                        listOfNames.add(indent); //indent from milestones
                    }
                }

                for (int i = 0; i < listOfNames.size(); i++) {
                    int indent = listOfNames.get(i).length();
                    if(indent > smallestIndent){
                        smallestIndent = indent;
                    }
                }

                if( smallestIndent < MIN_INDENT){
                    smallestIndent = MIN_INDENT;
                }

                System.out.println(); //blank line
                LocalDate localDate = LocalDate.now();
                LocalDate tasksStartDate = tasksStartAndFinishDates("start",tasks);
                LocalDate tasksFinishDate = tasksStartAndFinishDates("finish",tasks);

                long duration = ChronoUnit.DAYS.between(tasksStartDate, tasksFinishDate) + DATE_SUBTRACTION_CORRECTION;
                //
                for(int i = 0;i < (smallestIndent + INDENT_ADJUSTMENT2); i++){//horizontal line1
                    System.out.print(WHITE_BACKGROUND + "_" + CYAN_BRIGHT);
                }
                for (long i = 0; i < duration; i++){//horizontal line2
                    System.out.print(WHITE_BACKGROUND + "____________" + CYAN_BRIGHT);
                }
                System.out.println();
                printEmpty(smallestIndent);
                System.out.println("        " + foundProject.getName() + " (" + foundProject.getProjectID() + ")");
                printEmpty(smallestIndent);
                System.out.println("        Date: " + localDate);
                System.out.println();
                System.out.println();
                int beforeText = smallestIndent / QUARTER;
                printEmpty(beforeText);
                System.out.print("Tasks/Milestones");
                int afterText = smallestIndent - beforeText + INDENT_ADJUSTMENT4;
                printEmpty(afterText);
                System.out.print("|");
                for (long i = 0; i < duration; i++){//the days printed
                    LocalDate day = tasksStartDate.plusDays(i);
                    System.out.print("|" + day + "|");//12 pixels per day ?
                }
                System.out.println();
                for(int i= 0;i<(smallestIndent + INDENT_ADJUSTMENT2);i++){//horizontal line1
                    System.out.print(WHITE_BACKGROUND + "_" + CYAN_BRIGHT);
                }
                for (long i = 0; i < duration; i++){//horizontal line2
                    System.out.print(WHITE_BACKGROUND + "____________" + CYAN_BRIGHT);
                }
                System.out.println();
                System.out.println();
                System.out.println();

                for(int i = 0; i < tasks.size();i++){
                    Task currentTask = tasks.get(i);
                    int taskIndent = smallestIndent - currentTask.getName().length() - currentTask.getId().length() + INDENT_ADJUSTMENT1;

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
                                System.out.print(ANSI_BLUE_BACKGROUND + "|==========|" + CYAN_BRIGHT);//12 pixels per day ?
                                print = false;
                            }
                        }
                        if(print){
                            System.out.print("     .      ");//12 pixels per day ?
                        }
                    }
                    System.out.println();

                    //Actual plot
                    if(currentTask.getActualStart() != null){
                        printEmpty(smallestIndent + INDENT_ADJUSTMENT3);
                        System.out.print("|");
                        for (long j = 0; j < duration; j++){// project tasks duration
                            LocalDate day = tasksStartDate.plusDays(j);

                            LocalDate thisStart = currentTask.getActualStart();
                            LocalDate thisFinish = currentTask.getActualFinish();

                            long actualDuration = ChronoUnit.DAYS.between(thisStart, thisFinish) + DATE_SUBTRACTION_CORRECTION;

                            print = true;
                            for(int m = 0; m < actualDuration; m++){
                                LocalDate taskDates = thisStart.plusDays(m);
                                if(day.equals(taskDates)){
                                    System.out.print(ANSI_RED_BACKGROUND + "|**********|" + CYAN_BRIGHT);//12 pixels per day ?
                                    print = false;
                                }
                            }
                            if(print){
                                System.out.print("     .      ");//12 pixels per day ?
                            }
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
                //Milestones
                ArrayList<Milestone> milestones1 = foundProject.getMilestones();
                if(milestones1 != null){
                    for(int i = 0; i < milestones1.size();i++){
                        Milestone currentMilestone = milestones1.get(i);
                        int MilestoneIndent = smallestIndent - currentMilestone.getName().length() - currentMilestone.getId().length() + INDENT_ADJUSTMENT1;
                        System.out.print(currentMilestone.getName()+"(" + currentMilestone.getId()+")");
                        printEmpty(MilestoneIndent);
                        System.out.print("|");
                        boolean print;
                        for (int j = 0; j < duration; j++){// project tasks duration
                            LocalDate day = tasksStartDate.plusDays(j);
                            print = true;
                            LocalDate milestoneDate = currentMilestone.getDate();
                            if(day.equals(milestoneDate)){
                                System.out.print(PURPLE_BACKGROUND + "|##########|" + CYAN_BRIGHT);//12 pixels per day ?
                                print = false;

                            }
                            if(print){
                                System.out.print("            ");//12 pixels per day ?
                            }
                        }
                        System.out.println();
                        System.out.println();
                    }
                }

                for(int i= 0;i<(smallestIndent + INDENT_ADJUSTMENT2);i++){//horizontal line1
                    System.out.print(WHITE_BACKGROUND + "_" + CYAN_BRIGHT);
                }
                for (long i = 0; i < duration; i++){//horizontal line2
                    System.out.print(WHITE_BACKGROUND + "____________" + CYAN_BRIGHT);
                }
                //Legend
                System.out.println();
                System.out.println("                 Legend:");
                System.out.println("                     " + ANSI_BLUE_BACKGROUND + "|==========|" + CYAN_BRIGHT + " : Planned Tasks" + CYAN_BRIGHT);
                System.out.println();
                System.out.println("                     " + ANSI_RED_BACKGROUND+ "|**********|" + CYAN_BRIGHT + " : Actual Tasks" + CYAN_BRIGHT);

                if(milestones1 != null){
                    System.out.println();
                    System.out.println("                     " + PURPLE_BACKGROUND + "|##########|" + CYAN_BRIGHT + " : Milestones");
                }
                //
            }else{
                System.out.println("There are no tasks in the project");
            }
        }else {
            System.out.println("There are no projects to show");
        }
        pause();
    }//Eyuell


    public void printResourceHistogram() throws Exception{
        int WEEK_NUMBERS = 52;

        int weekNum;
        HashMap<String, Double> planned = new HashMap<>();
        HashMap<String, Double> actual = new HashMap<>();

        Set<Entry<String, Double>> hashSetPlanned = planned.entrySet();
        Set<Entry<String, Double>> hashSetActual = actual.entrySet();

        if(projects != null){
            Project currentProject = projects.get(FIRST);
            ArrayList<Task> tasks = currentProject.getTasks();
            if(tasks != null){
                for(int i = WEEK_NUMBERS; i > FIRST; i--){

                    for(Task currentTask : tasks){
                        ArrayList<TeamMemberAllocation> allocations = currentTask.getActualTeamMembers();
                        if(allocations != null){
                            for(TeamMemberAllocation currentAllocation : allocations){

                                weekNum = weekNumber(currentAllocation.getDate());

                                String key = weekString(currentAllocation.getDate());

                                if(weekNum == i){
                                    double previousValue;
                                    if(actual.get(key) == null){
                                        previousValue = 0.0;
                                    } else {
                                        previousValue = actual.get(key);
                                    }

                                    double newValue = previousValue + currentAllocation.getWorkHours();
                                    actual.put(key,newValue);
                                }
                            }
                        }

                        ArrayList<ManpowerAllocation> manpower = currentTask.getPlannedManpower();
                        if(manpower != null){
                            for(ManpowerAllocation manpowerAllocation : manpower){

                                weekNum = weekNumber(manpowerAllocation.getDate());

                                String key = weekString(manpowerAllocation.getDate());

                                if(weekNum == i){
                                    double previousValue;
                                    if(planned.get(key) == null){
                                        previousValue = 0.0;
                                    } else {
                                        previousValue = planned.get(key);
                                    }

                                    double newValue = previousValue + manpowerAllocation.getWorkHours();
                                    planned.put(key,newValue);
                                }
                            }
                        }
                    }
                }
            }

            ArrayList<String> planedKeys = new ArrayList<>();
            for(Entry entry: hashSetPlanned ) {
                planedKeys.add(entry.getKey().toString());
            }

            Collections.sort(planedKeys);

            ArrayList<String> actualKeys = new ArrayList<>();
            for(Entry entry: hashSetActual ) {
                actualKeys.add(entry.getKey().toString());
            }

            Collections.sort(actualKeys);

            if(planedKeys.size() > 0){
                System.out.println();
                System.out.println();
                System.out.println("                    Histogram for " + currentProject.getName() + " project");
                System.out.println();
                System.out.println();
                System.out.println("            Year/Month");
                System.out.println("                ʌ");
                System.out.println("                │");
                System.out.println("                │");
            }

            double maxValue = 0.0;
            for(int i = FIRST; i < planedKeys.size(); i++){

                double plannedValue = planned.get(planedKeys.get(i));
                if(plannedValue > maxValue){
                    maxValue = plannedValue;
                }
            }

            for(int i = FIRST; i < actualKeys.size(); i++){
                double actualValue = actual.get(actualKeys.get(i));
                if(actualValue > maxValue){
                    maxValue = actualValue;
                }
            }

            int INDENT = 16;
            int HALVING = 2;
            int SCALE = 5;
            for(int i = FIRST; i < planedKeys.size(); i++){
                System.out.println("                │");
                System.out.println("                │");
                int before = (INDENT - planedKeys.get(i).length())/HALVING;
                int after = INDENT - planedKeys.get(i).length() - before;
                printEmpty(before);
                String key = planedKeys.get(i);
                System.out.print(key);
                printEmpty(after);
                System.out.print("│");

                double plannedValue = planned.get(key);
                System.out.print(ANSI_BLUE_BACKGROUND);
                for(int j = FIRST; j <= (int)(plannedValue / SCALE); j++){
                    System.out.print("   ");
                }
                System.out.println(CYAN_BRIGHT + " " + new DataEvaluator().roundDouble(plannedValue,1) + " hours");

                if(actual.get(key) != null){
                    double actualValue = actual.get(key);
                    System.out.print("                │");
                    System.out.print(ANSI_RED_BACKGROUND);
                    for(int j = FIRST; j <= (int)(actualValue / SCALE); j++){
                        System.out.print("   ");
                    }
                    System.out.println(CYAN_BRIGHT + " " + new DataEvaluator().roundDouble(actualValue,1) + " hours");
                }
                System.out.print(CYAN_BRIGHT);

            }

            int MODULAR = 10;
            if(planedKeys.size() > FIRST){
                System.out.println(CYAN_BRIGHT + "                │");
                System.out.print("                 ");
                for (int i = FIRST; i <= (int)(maxValue / SCALE); i++){
                    if(i % MODULAR == FIRST){
                        System.out.print("─.─");
                    } else {
                        System.out.print("───");
                    }
                }

                System.out.println("──────>");
                System.out.print("                 ");
                int value;
                for (int i = FIRST; i <= (int)(maxValue / SCALE); i++){
                    if(i % MODULAR == FIRST){
                        value = i * SCALE;
                        System.out.print(" " + value);
                    } else {
                        System.out.print("   ");
                    }
                }

                System.out.print("  Hours");
                System.out.println();
                System.out.println();
                System.out.println("            " + WHITE_UNDERLINED + "LEGEND:" + CYAN_BRIGHT);
                System.out.println("                 " + ANSI_BLUE_BACKGROUND + "        " + CYAN_BRIGHT + " Planned Hours");
                System.out.println("                 " + ANSI_RED_BACKGROUND + "        " + CYAN_BRIGHT + " Actual Hours ");

            }
        }
        pause();
    }//OSMAN

    public int weekNumber(LocalDate date) throws Exception{

        String input = date.toString();
        String format = "yyyy-MM-dd";

        SimpleDateFormat df = new SimpleDateFormat(format);
        Date dateInput = df.parse(input);

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateInput);

        return cal.get(Calendar.WEEK_OF_YEAR);
    }//OSMAN

    public String weekString (LocalDate date) throws Exception{
        Calendar c = Calendar.getInstance(  );    // today
        String input = date.toString();
        String format = "yyyy-MM-dd";

        SimpleDateFormat df = new SimpleDateFormat(format);
        Date dateInput = df.parse(input);
        c.setTime(dateInput);

        int week = c.get(Calendar.WEEK_OF_YEAR);
        int month = c.get(Calendar.WEEK_OF_MONTH);
        int yearInt = c.get(Calendar.YEAR);

        if (week == 1 && month >= 3){//if in the last week of december
            yearInt = yearInt + 1;
        }

        String year = String.valueOf(yearInt);
        year = year.substring(2);

        String week2;
        if(week < 10){
            week2 = "0" + week;
        } else {
            week2 = String.valueOf(week);
        }

        return "" + year + "/" + week2;
    }//OSMAN

    public void monitorCosts() throws IOException, InterruptedException {//Armin
        final int ALL_COSTS = 1;
        final int SCHEDULE_VARIANCE = 2;
        final int COST_VARIANCE = 3;
        final int EARNED_VALUE= 4;
        final int RETURN = 5;
        int option;
        do {
            System.out.println();
            System.out.println("========= COSTS =========");
            System.out.println();
            System.out.println("WHAT DO YOU WISH TO DISPLAY?");
            System.out.println("SELECT FROM THE FOLLOWING OPTIONS");
            System.out.println();

            System.out.println(" 1. ALL COSTS");
            System.out.println(" 2. SCHEDULE VARIANCE");
            System.out.println(" 3. COST VARIANCE");
            System.out.println(" 4. EARNED VALUE");
            System.out.println(" 5. RETURN");
            System.out.println();
            System.out.print("Type the option number: ");

            option = new KeyboardInput().Int();
            System.out.println("-------------------------------------------------");
            // that the user types after
            // typing the integer option.

            switch (option) {
                case ALL_COSTS:
                    printAllCosts();
                    break;

                case SCHEDULE_VARIANCE:
                    monitorScheduleVariance();
                    break;

                case COST_VARIANCE:
                    monitorCostVariance();
                    break;

                case EARNED_VALUE:
                    monitorEarnedValue();
                    break;

                case RETURN:

                    break;

                default:
                    System.out.println("Option " + option + " is not valid.");
                    System.out.println();
                    break;
            }
        } while (option!=RETURN);
    }//Armin

    public void printAllCosts(){

        if(projects != null){
            Project foundProject = projects.get(FIRST);
            System.out.println();

            double percentageDone;
            int option;

            System.out.println("          ========ALL COSTS=========");
            System.out.println("");
            System.out.println("HOW MANY PERCENT OF THE PROJECT IS COMPLETED?");
            System.out.println("       ==INPUT IS BETWEEN 0 AND 1==         ");
            System.out.println(RED+"         OBS! 1 IS 100% 0 IS 0% ! ");
            System.out.println(CYAN_BRIGHT);

            percentageDone = new KeyboardInput().Double();{
                System.out.println("__________________________________________");

                if(percentageDone>1){
                    percentageDone=1;
                }
            }

            double plannedSum = totalPlannedHours(foundProject);
            double actualSum = totalActualHours(foundProject);

            double plannedBudget = Math.round((plannedSum * PAY)*100)/100.0;
            double actualCost = Math.round((actualSum * SALARY)*100)/100.0;

            LocalDate today = LocalDate.now();
            LocalDate tasksStartDate = tasksStartAndFinishDates("start",foundProject.getTasks());
            LocalDate tasksFinishDate = tasksStartAndFinishDates("finish",foundProject.getTasks());

            //project tasks total duration
            double projectDuration = ChronoUnit.DAYS.between(tasksStartDate, tasksFinishDate) + DATE_SUBTRACTION_CORRECTION;
            double durationTillToday = ChronoUnit.DAYS.between(tasksStartDate, today) + DATE_SUBTRACTION_CORRECTION;

            double ExecutedProgress = percentageDone;
            double scheduleProgress = durationTillToday/projectDuration;;
            double earnedValue = (Math.round((plannedBudget *percentageDone ))*100)/100.0;

            double deadline=projects.get(FIRST).getDuration()-durationTillToday;
            double projectLength=projects.get(FIRST).getDuration();
            String projectID=projects.get(FIRST).getProjectID();
            System.out.println("__________________________________________");
            System.out.println("Project budget(SEK)         : "+plannedBudget);
            System.out.println("__________________________________________");

            System.out.println("Project cost(SEK)           : " + actualCost);
            System.out.println("__________________________________________");

            System.out.println("Program Executed Progress   : " + Math.round(((ExecutedProgress) * 100.0) * 100) / 100.0 + " %"); //this is only monetary wise
            System.out.println("__________________________________________");

            System.out.println("Program Time Based Progress : " + Math.round(((scheduleProgress) * 100.0) * 100) / 100.0 + " %");
            System.out.println("__________________________________________");

            System.out.println("Days passed since start     : "+ durationTillToday+" days");
            System.out.println("__________________________________________");

            System.out.println("Project length              : "+projectLength+" days");
            System.out.println("__________________________________________");

            System.out.println("Days until Deadline         : "+ deadline+ " days");
            System.out.println("__________________________________________");

            System.out.println(" ");
            SystemStore Costs = new MiniProject.SystemStore();
            System.out.println("__________________________________________");

            System.out.println(Costs.registerCostVariance(plannedBudget, earnedValue, plannedSum, actualSum, actualCost, foundProject.getProjectID()));
            System.out.println(Costs.registerScheduleVariance(plannedBudget, earnedValue, plannedSum, actualSum, foundProject.getProjectID() ));
            System.out.println(CYAN_BRIGHT);

            System.out.println("PROJECT ID: "+projectID);
            System.out.println("");
            System.out.println("========EARNED VALUE=========");
            System.out.println("_____________________________");
            System.out.println("        "+earnedValue+" SEK  ");
            System.out.println("_____________________________");
            System.out.println(CYAN_BRIGHT);
            pause();

        }
    }//Armin//COST

    public void monitorEarnedValue() {//Armin
        if (projects != null) {
            Project foundProject = projects.get(FIRST);
            System.out.println();

            double percentageDone;
            int option;

            System.out.println("        ========EARNED VALUE=========");
            System.out.println("");
            System.out.println("HOW MANY PERCENT OF THE PROJECT IS COMPLETED?");
            System.out.println("       ==INPUT IS BETWEEN 0 AND 1==         ");
            System.out.println(RED+"         OBS! 1 IS 100% 0 IS 0% ! ");
            System.out.println(CYAN_BRIGHT);

            percentageDone = new KeyboardInput().Double();{
                System.out.println("__________________________________________");

                if(percentageDone>1){
                    percentageDone=1;
                }
            }

            double plannedSum = totalPlannedHours(foundProject);
            double actualSum = totalActualHours(foundProject);

            double plannedBudget = Math.round((plannedSum * PAY)*100)/100.0;
            double actualCost = Math.round((actualSum * SALARY)*100)/100.0;

            String projectID=projects.get(FIRST).getProjectID();

            LocalDate today = LocalDate.now();
            LocalDate tasksStartDate = tasksStartAndFinishDates("start", foundProject.getTasks());
            LocalDate tasksFinishDate = tasksStartAndFinishDates("finish", foundProject.getTasks());

            //project tasks total duration
            double projectDuration = ChronoUnit.DAYS.between(tasksStartDate, tasksFinishDate) + DATE_SUBTRACTION_CORRECTION;
            double durationTillToday = ChronoUnit.DAYS.between(tasksStartDate, today) + DATE_SUBTRACTION_CORRECTION;

            double ExecutedProgress = percentageDone;
            double scheduleProgress = durationTillToday/projectDuration;;
            double earnedValue = (Math.round((plannedBudget *percentageDone ))*100)/100.0;

            double deadline=projects.get(FIRST).getDuration()-durationTillToday;
            double projectLength=projects.get(FIRST).getDuration();
            System.out.println("__________________________________________");
            System.out.println("Project budget              : " + plannedBudget+"SEK");

            System.out.println("__________________________________________");
            System.out.println("Project cost                : " + actualCost+"SEK");

            System.out.println("__________________________________________");
            System.out.println("Program Executed Progress   : " + Math.round(((ExecutedProgress) * 100.0) * 100) / 100.0 + " %"); //this is only monetary wise

            System.out.println("__________________________________________");
            System.out.println("Program Time Based Progress : " + Math.round(((scheduleProgress) * 100.0) * 100) / 100.0 + " %");
            System.out.println("__________________________________________");

            System.out.println("Days passed since start     : "+ durationTillToday+" days");
            System.out.println("__________________________________________");

            System.out.println("Project length              : "+projectLength+" days");
            System.out.println("__________________________________________");

            System.out.println("Days until Deadline         : "+ deadline+ " days");
            System.out.println("__________________________________________");
            System.out.println(" ");

            System.out.println("PROJECT ID: "+projectID);
            System.out.println("");
            System.out.println("========EARNED VALUE=========");
            System.out.println("_____________________________");
            System.out.println("        "+earnedValue+" SEK  ");
            System.out.println("_____________________________");

            pause();
        }
    }//Armin

    public void monitorScheduleVariance() throws IOException, InterruptedException {

        if(projects != null){
            Project foundProject = projects.get(FIRST);
            System.out.println();

            double percentageDone;
            int option;

            System.out.println("     ========SCHEDULE VARIANCE=========");
            System.out.println("");
            System.out.println("HOW MANY PERCENT OF THE PROJECT IS COMPLETED?");
            System.out.println("       ==INPUT IS BETWEEN 0 AND 1==         ");
            System.out.println(RED+"         OBS! 1 IS 100% 0 IS 0% ! ");
            System.out.println(CYAN_BRIGHT);

            percentageDone = new KeyboardInput().Double();{
                System.out.println("__________________________________________");

                if(percentageDone>1){
                    percentageDone=1;
                }
            }

            double plannedSum = totalPlannedHours(foundProject);
            double actualSum = totalActualHours(foundProject);

            double plannedBudget = Math.round((plannedSum * PAY)*100)/100.0;
            double actualCost = Math.round((actualSum * SALARY)*100)/100.0;

            LocalDate today = LocalDate.now();
            LocalDate tasksStartDate = tasksStartAndFinishDates("start",foundProject.getTasks());
            LocalDate tasksFinishDate = tasksStartAndFinishDates("finish",foundProject.getTasks());

            //project tasks total duration
            double projectDuration = ChronoUnit.DAYS.between(tasksStartDate, tasksFinishDate) + DATE_SUBTRACTION_CORRECTION;
            double durationTillToday = ChronoUnit.DAYS.between(tasksStartDate, today) + DATE_SUBTRACTION_CORRECTION;
            double ExecutedProgress = percentageDone;
            double scheduleProgress = durationTillToday/projectDuration;;
            double earnedValue = (Math.round((plannedBudget *percentageDone ))*100)/100.0;

            double deadline=projects.get(FIRST).getDuration()-durationTillToday;
            double projectLength=projects.get(FIRST).getDuration();
            String projectID=projects.get(FIRST).getProjectID();
            System.out.println("__________________________________________");
            System.out.println("Project budget(SEK)         : " + plannedBudget);

            System.out.println("__________________________________________");
            System.out.println("Project cost(SEK)           : " + actualCost);

            System.out.println("__________________________________________");
            System.out.println("Program Executed Progress   : " + Math.round(((ExecutedProgress) * 100.0) * 100) / 100.0 + " %"); //this is only monetary wise

            System.out.println("__________________________________________");
            System.out.println("Program Time Based Progress : " + Math.round(((scheduleProgress) * 100.0) * 100) / 100.0 + " %");

            System.out.println("__________________________________________");
            System.out.println("Days passed since start     : "+ durationTillToday+" days");

            System.out.println("__________________________________________");
            System.out.println("Project length              : "+projectLength+" days");

            System.out.println("__________________________________________");
            System.out.println("Days until Deadline         : "+ deadline+ " days");

            System.out.println("__________________________________________");
            System.out.println(" ");


            SystemStore Costs = new MiniProject.SystemStore();
            System.out.println(Costs.registerScheduleVariance(plannedBudget, earnedValue, plannedSum, actualSum, foundProject.getProjectID() ));
            System.out.println(CYAN_BRIGHT);
            pause();
        }
    }//Armin

    public void monitorCostVariance(){//COST
        if(projects != null){
            Project foundProject = projects.get(FIRST);
            System.out.println();

            double percentageDone;
            int option;

            System.out.println("       ========COST VARIANCE=========");
            System.out.println(" ");
            System.out.println("HOW MANY PERCENT OF THE PROJECT IS COMPLETED?");
            System.out.println("       ==INPUT IS BETWEEN 0 AND 1==         ");
            System.out.println(RED+"         OBS! 1 IS 100% 0 IS 0% ! ");
            System.out.println(CYAN_BRIGHT);

            percentageDone = new KeyboardInput().Double();{
                System.out.println("__________________________________________");

                if(percentageDone>1){
                    percentageDone=1;
                }
            }

            double plannedSum = totalPlannedHours(foundProject);
            double actualSum = totalActualHours(foundProject);

            double plannedBudget = Math.round((plannedSum * PAY)*100)/100.0;
            double actualCost = Math.round((actualSum * SALARY)*100)/100.0;

            LocalDate today = LocalDate.now();
            LocalDate tasksStartDate = tasksStartAndFinishDates("start",foundProject.getTasks());
            LocalDate tasksFinishDate = tasksStartAndFinishDates("finish",foundProject.getTasks());


            //project tasks total duration
            double projectDuration = ChronoUnit.DAYS.between(tasksStartDate, tasksFinishDate) + DATE_SUBTRACTION_CORRECTION;
            double durationTillToday = ChronoUnit.DAYS.between(tasksStartDate, today) + DATE_SUBTRACTION_CORRECTION;
            double ExecutedProgress = percentageDone;
            double scheduleProgress = durationTillToday/projectDuration;;
            double earnedValue = (Math.round((plannedBudget *percentageDone ))*100)/100.0;

            double deadline=projects.get(FIRST).getDuration()-durationTillToday;
            double projectLength=projects.get(FIRST).getDuration();
            String projectID=projects.get(FIRST).getProjectID();

            System.out.println("__________________________________________");
            System.out.println("Project budget(SEK)         : " + plannedBudget);

            System.out.println("__________________________________________");
            System.out.println("Project cost(SEK)           : " + actualCost);

            System.out.println("__________________________________________");
            System.out.println("Program Executed Progress   : " + Math.round(((ExecutedProgress) * 100.0) * 100) / 100.0 + " %"); //this is only monetary wise

            System.out.println("__________________________________________");
            System.out.println("Program Time Based Progress : " + Math.round(((scheduleProgress) * 100.0) * 100) / 100.0 + " %");

            System.out.println("__________________________________________");
            System.out.println("Days passed since start     : "+ durationTillToday+" days");

            System.out.println("__________________________________________");
            System.out.println("Project length              : "+projectLength+" days");

            System.out.println("__________________________________________");
            System.out.println("Days until Deadline         : "+ deadline+ " days");

            System.out.println("__________________________________________");
            System.out.println(" ");

            SystemStore Costs = new MiniProject.SystemStore();
            System.out.println(Costs.registerCostVariance(plannedBudget, earnedValue, plannedSum, actualSum, actualCost, foundProject.getProjectID()).toString());

            pause();
        }
    }//Armin




    public void monitorTimeSpent() {
        Project currentProject = projects.get(FIRST);
        double DIGIT_LIMITER = 10.0;
        if (currentProject != null) {
            String choice = "";
            boolean error = true;
            do {
                try {
                    System.out.println("Do you want to see the Time Spent of :");
                    System.out.println("    1. All team members");
                    System.out.println("    2. Specific team member");
                    System.out.print(" Enter onr of the two options (1 or 2) ?");
                    choice = new KeyboardInput().Line();
                    error = false;
                } catch (Exception e) {
                    System.out.println("Error , wrong input type");
                }
            } while ((!choice.equalsIgnoreCase("1") && !choice.equalsIgnoreCase("2")) || error );
            if (choice.equalsIgnoreCase("1")) {
                ArrayList<Task> tasks = currentProject.getTasks();
                Map<String, Double> memberIdHours = new HashMap<String,Double>();

                if (tasks != null) {
                    for (Task oneTask : tasks) {
                        ArrayList<TeamMemberAllocation> allocations = oneTask.getActualTeamMembers();
                        if (allocations != null) {
                            for (TeamMemberAllocation currentAllocation : allocations) {
                                String thisMemberId = currentAllocation.getTeamMember().getId();
                                double thisWorkedHours = currentAllocation.getWorkHours();
                                if(memberIdHours.containsKey(thisMemberId)) {
                                    double newHoursWorked = memberIdHours.get(thisMemberId) + thisWorkedHours;
                                    memberIdHours.put(thisMemberId,newHoursWorked);
                                } else {
                                    memberIdHours.put(thisMemberId,thisWorkedHours);
                                }
                            }
                        }
                    } for(Map.Entry<String, Double> entry: memberIdHours.entrySet()) {
                        String memberName = retrieveTeamMember(currentProject, entry.getKey()).getName();
                        System.out.println( memberName + " has worked " + Math.round(entry.getValue() * DIGIT_LIMITER) / DIGIT_LIMITER + " hours on the project ");
                    }
                } else {
                    System.out.println("There are no tasks registered");
                }
            } else if(choice.equalsIgnoreCase("2")) {
                boolean error2 = true;
                String memberId = "";
                do {
                    try {
                        System.out.print("Enter the id of team member ");
                        memberId = new KeyboardInput().Line();
                        error2 = false;
                    } catch (Exception ex) {
                        System.out.println("Error , wrong input type");
                    }
                    if (!teamMemberIDExists(currentProject, memberId)) {
                        System.out.println("Team member does not exist or wrong id.");
                    }
                } while (error2  || !teamMemberIDExists(currentProject, memberId));

                double totalHours = 0;
                ArrayList<Task> tasks = currentProject.getTasks();
                if (tasks != null) {
                    for (Task oneTask : tasks) {
                        ArrayList<TeamMemberAllocation> allocations = oneTask.getActualTeamMembers();
                        if (allocations != null) {
                            for (TeamMemberAllocation currentAllocation : allocations) {
                                if (currentAllocation.getTeamMember().getId().equals(memberId)) {
                                    totalHours += currentAllocation.getWorkHours();
                                }
                            }
                        }
                    }
                    System.out.println(retrieveTeamMember(currentProject,memberId).getName() + " has worked " + Math.round(totalHours * DIGIT_LIMITER) / DIGIT_LIMITER + " hours in total");
                } else {
                    System.out.println("There are no tasks registered");
                }
            }
        }else{
            System.out.println("There are no projects registered");
        }
        pause();
    }//Hamid


    public void monitorParticipation(){
        double ZERO = 0.0;
        HashMap<String, Double> participation = new HashMap<>();

        Set<Entry<String, Double>> hashSet = participation.entrySet();

        if(projects != null){
            Project currentProject = projects.get(FIRST);
            listTeamMembers();
            System.out.print("Enter the id of a team member ");
            String teamID = new KeyboardInput().Line();

            while (! teamMemberIDExists(currentProject, teamID)) {
                System.out.print("Team member does not exist or wrong ID. Enter correct ID again ");
                teamID = new KeyboardInput().Line();
            }
            String teamMemberName = "";
            double totalHours;
            ArrayList<Task> tasks = currentProject.getTasks();
            if(tasks != null){
                for(Task currentTask : tasks){
                    totalHours = ZERO;
                    ArrayList<TeamMemberAllocation> allocations = currentTask.getActualTeamMembers();
                    if(allocations != null){
                        for(TeamMemberAllocation currentAllocation : allocations){
                            if(currentAllocation.getTeamMember().getId().equals(teamID)){
                                teamMemberName = currentAllocation.getTeamMember().getName();
                                totalHours = totalHours + currentAllocation.getWorkHours();
                            }
                        }
                    }
                    if(totalHours != ZERO){
                        participation.put(currentTask.getName(), totalHours);
                    }
                }
            }
            if(! participation.isEmpty()){
                System.out.println(teamMemberName + " has participated on: ");
            }
            for(Entry entry: hashSet ) {
                System.out.println("    " + entry.getKey()+" for "+ new DataEvaluator().roundDouble((double)entry.getValue(),1) + " hours");
            }
        }
        pause();
    }//OSMAN

    public void monitorRisk(){

        if(projects != null){ // a project is needed to talk about risk
            new RiskMatrix(projects.get(FIRST)).runRisk();
        }else{
            System.out.println("There is no registered project");
            pause();
        }
    }//James

    //Switch for team member, project and task edits
    public void editInfo(){
        int option;

        final int PROJECT = 1;
        final int TASKS = 2;
        final int TEAM_MEMBERS = 3;
        final int ALLOCATIONS = 4;
        final int RETURN = 5;


        do {
            printMenuOptions();
            System.out.print(" Type the option number: ");

            option = new KeyboardInput().Int();
            // that the user types after
            // typing the integer option.

            switch (option) {
                case TASKS:
                    editTasks();
                    break;

                case ALLOCATIONS:
                    editAllocations();
                    break;

                case TEAM_MEMBERS:
                    editTeamMember();
                    break;

                case PROJECT:
                    editProject();
                    break;

                case RETURN:
                    // returnToMenu();
                    break;

                default:
                    System.out.println("Option " + option + " is not valid.");
                    System.out.println();
                    break;
            }
        } while (option!=RETURN);
    }//Armin

    public void editProject() {//HAMID
        String id="";
        int option = 0;
        int OPTIONS = 5;
        boolean error = true ;

        do {
            try {
                System.out.print("What is the id of the project that you want to edit ?");
                id = new KeyboardInput().Line();
                error = false;
            }catch(Exception e){
                System.out.println("Error , wrong input type");
            }
            if (!projectExists(id)) {
                System.out.println("A project with this id doesn't exists");
            }
        } while (!projectExists(id) || error );

        error = true;
        do {
            try {
                option = choiceOfProjectEdit();
                error= false;
            } catch (Exception e) {
                System.out.println("Error, wrong input type");
            }
        }while((option > OPTIONS ) || error);
        Project project = retrieveProjectByID(id);
        error = true;
        if (option == 1) {
            String newName;
            System.out.println("The name of this project is:- " + project.getName());
            do {
                try {
                    System.out.print("What is the new name that you want to put? ");
                    newName = new KeyboardInput().Line();
                    project.setName(newName);
                    System.out.println("Project name is successfully updated to: " + project.getName());
                    error = false;
                } catch (Exception e) {
                    System.out.println("Error, wrong input type");
                }
            }while(error);
        }
        error = true;
        if (option == 2) {
            String newId = "";
            do {
                try {
                    System.out.print("What is the new project id that you want put? ");
                    newId = new KeyboardInput().Line();
                    error = false;
                    if (projectExists(newId)) {
                        System.out.println("This id already exists");
                    } else {
                        project.setProjectID(newId);
                        System.out.println("Project id is successfully updated to: " + project.getProjectID());
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Error, wrong input type");
                }
            }while(projectExists(newId) || error );
        }
        error = true;
        if (option == 3) {
            double newBudget;
            do {
                try {
                    System.out.println("The current budget of the project is: " + project.getBudget() + " SEK");
                    System.out.println("What is the new budget ?");
                    newBudget = new MiniProject.KeyboardInput().positiveDouble();
                    project.setBudget(newBudget);
                    System.out.println("Project budget is successfully updated to: " + project.getBudget() + " SEK");
                    error = false;
                } catch (Exception e) {
                    System.out.println("Error, wrong input type");
                }
            }while(error);
        }

        if (option == 4) {
            editProjectDuration();
        }

        if (option == 5) {
            System.out.println("Exiting edit...");
        }
        pause();
    }//HAMID


    public int choiceOfProjectEdit(){
        int choice;
        int OPTIONS_MAX = 5;
        System.out.println();
        System.out.println("Options of Editing Project");
        System.out.println("    1. Name");
        System.out.println("    2. ID ");
        System.out.println("    3. Budget");
        System.out.println("    4. Dates & Duration");
        System.out.println("    5. Exit");
        System.out.println();
        System.out.print("Enter option number : ");
        choice = new KeyboardInput().positiveNonZeroInt();

        while(choice > OPTIONS_MAX){
            System.out.println();
            System.out.println("Option number not correctly entered.");
            System.out.print("Enter option number again: ");
            choice = new KeyboardInput().positiveNonZeroInt();
        }

        return choice;
    }//James

    public void editTaskName(){
        Project currentProject = projects.get(FIRST);
        listTasks();

        if (currentProject!=null) {
            boolean error;
            String taskID;
            Task task;
            do {
                try {
                    System.out.print("Enter the id of task you wish to rename: ");
                    taskID = new KeyboardInput().Line();
                    System.out.println("----------------------------------------------------------------");
                    task = retrieveTask(taskID, currentProject);

                    if(task.getId()!=null)
                        System.out.println(" The name of task is currently: " + task.getName());
                    System.out.println("----------------------------------------------------------------");
                    System.out.print("Enter new Name: ");

                    String name = new KeyboardInput().Line();
                    task.setName(name);
                    System.out.println("The name is now set to: " + task.getName() + ".");
                    System.out.println("---------------------------------------------------------------");
                    error = false;

                } catch (Exception e) {
                    System.out.println("ID must be inputted as a number.");
                    System.out.println("---------------------------------------------------------------");
                    error=true;
                }

            } while (error);
        }
        pause();
    }//Armin

    public void editTeamMember(){
        boolean error = true;
        Integer option = 0;
        do {
            try{
                System.out.println("What do you wish to edit?");
                System.out.println("1. Name ");
                System.out.println("2. ID");
                System.out.println("3. Qualification");
                System.out.println("4. Remove");

                option= new KeyboardInput().positiveNonZeroInt();
                error = false;

            }catch (Exception e) {
                System.out.println("Error, wrong input type");
            }
        }while(option > 3 || error);
        if(option==1){
            updateTeamMemberName();
        }
        else if(option==2){
            editTeamMemberID();
        }
        else if(option==3){
            editQualification();
        }else if(option==4){
            removeTeamMember();
        }
        pause();
    }//Armin


    public void editTeamMemberID(){
        Project currentProject = projects.get(FIRST);

        System.out.println("----------------------------------------------------------------");
        System.out.println("OBS! editing ID to an existing ID will override them.");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Type in id of the Team member you want to edit:");

        String teamMemberID = new KeyboardInput().Line();

        teamMemberIDExists(currentProject, teamMemberID);
        while(!teamMemberIDExists(currentProject, teamMemberID)) {
            System.out.print("TeamMember does not exist or wrong ID. Enter correct ID again ");
            teamMemberID = new KeyboardInput().Line();
            System.out.println("----------------------------------------------------------------");
        }

        TeamMember teamMember = retrieveTeamMember(currentProject,teamMemberID );
        System.out.println("Your team member ID is currently : "+teamMember.getId());
        System.out.println("----------------------------------------------------------------");
        System.out.println("Enter new ID: ");

        String newID = new KeyboardInput().Line();
        teamMember.setId(newID);

        System.out.println("----------------------------------------------------------------");
        System.out.println("You have now changed your ID to "+ teamMember.getId());
    }//Armin


    public void editTasks(){

        int option;
        boolean input;

        do {
            System.out.println("What do you wish to edit?");
            System.out.println("1. Name ");
            System.out.println("2. ID");
            System.out.println("3. Dates");
            System.out.println("4. Task Type");
            System.out.println("5. Task Connectivity");
            System.out.println("6. Remove Task");
            System.out.println("7. Return");
            option = new KeyboardInput().Int();

            if (option == 1) {
                editTaskName();
                input = false;

            } else if (option == 2) {
                editTaskID();//OSMAN
                input = false;

            } else if (option == 3) {
                editTaskDates();//James
                input = false;

            } else if (option == 4) {
                editTaskType();//Hamid
                input = false;

            } else if (option == 5) {
                editTaskConnectivity();//Hamid
                input = false;

            } else if (option == 6) {
                removeTask();
                input = false;

            } else if (option == 7) {
                System.out.println(" ");
                input = false;

            } else {
                System.out.println("Option " + option + " is not valid.");
                System.out.println(" ");
                input = true;
            }

        }while (input) ;
    }//Armin


    public boolean tasksIDExists(Project project, String ID){//OSMAN
        if(project != null){
            for(int i = 0; i < project.getTasks().size(); i++){
                if(project.getTasks().get(i).getId().equals(ID)){
                    return true;
                }
            }
        }
        return false;
    }

    public void editTaskID() {//OSMAN
        Project currentProject = projects.get(FIRST);
        listTasks();
        System.out.print("Type in id of the task you want to edit ");
        String taskID = new KeyboardInput().Line();

        tasksIDExists(currentProject, taskID);
        while(!tasksIDExists(currentProject, taskID)) {
            System.out.print("Task does not exist or wrong ID. Enter correct ID again ");
            taskID = new KeyboardInput().Line();
        }

        Task task = retrieveTask(taskID,currentProject );
        System.out.print("Enter new ID: ");

        String newID = new KeyboardInput().Line();
        task.setId(newID);
    }


    public void editProjectDuration() {// OSMAN
        Project currentProject = projects.get(FIRST);

        LocalDate projectStartDate = currentProject.getStartDate(); //if not changed under to keep the existing
        LocalDate projectFinishDate = currentProject.getFinishDate(); //if not changed under to keep the existing
        long duration;
        int option;

        do {
            System.out.println();
            System.out.println("What do you want to change");
            System.out.println("1. Project Start Date");
            System.out.println("2. Project End Date");
            System.out.println("3. Both");
            System.out.print("Enter 1, 2 or 3 ");
            option = new KeyboardInput().Int();
        } while (option != 1 && option != 2 && option != 3);

        if (option == 1) {
            System.out.println("Enter the NEW Start date of the project");
            projectStartDate = new DataEvaluator().readDate();

            duration = ChronoUnit.DAYS.between(projectStartDate, projectFinishDate) + DATE_SUBTRACTION_CORRECTION;
            currentProject.setStartDate(projectStartDate);

            currentProject.setDuration(duration);
            checkWithProjectTimes(currentProject);

        } else if (option == 2) {
            System.out.println("Enter NEW Finish Date");
            projectFinishDate = new DataEvaluator().readDate();

            duration = ChronoUnit.DAYS.between(projectStartDate, projectFinishDate) + DATE_SUBTRACTION_CORRECTION;
            currentProject.setFinishDate(projectFinishDate);

            currentProject.setDuration(duration);
            checkWithProjectTimes(currentProject);
        } else { //the remaining option is 3

            System.out.println("Enter the NEW Start date of the project: ");
            projectStartDate = new DataEvaluator().readDate();

            System.out.println("Enter the NEW Finish date of the project: ");
            projectFinishDate = new DataEvaluator().readDate();

            duration = ChronoUnit.DAYS.between(projectStartDate, projectFinishDate) + DATE_SUBTRACTION_CORRECTION;

            currentProject.setStartDate(projectStartDate);
            currentProject.setFinishDate(projectFinishDate);

            currentProject.setDuration(duration);
            checkWithProjectTimes(currentProject);

        }
        pause();
    }//OSMAN

    public void editTaskDates(){
        Project currentProject = projects.get(FIRST);
        String taskId = readExistingTaskID(currentProject);
        Task foundTask = retrieveTask(taskId, currentProject);

        if(foundTask.getTypeOfTask() == 1){
            System.out.println("Enter the start date of the task ");
            LocalDate taskStartDate = new DataEvaluator().readDate();
            foundTask.setPlannedStart(taskStartDate);

            int lengthOfTask = readLengthOfTask();
            if(lengthOfTask == 1){
                System.out.print("Enter the planned duration of the task ");
                long taskPlannedDuration = new KeyboardInput().positiveInt();
                foundTask.setPlannedDuration(taskPlannedDuration);
                LocalDate finishDate = foundTask.getPlannedStart().plusDays(taskPlannedDuration);
                foundTask.setPlannedFinish(finishDate);
            }else if(lengthOfTask == 2){
                System.out.println("Enter the finish date of the task ");
                LocalDate taskFinishDate = new DataEvaluator().readDate();
                foundTask.setPlannedFinish(taskFinishDate);

                long duration = ChronoUnit.DAYS.between(foundTask.getPlannedStart(), taskFinishDate) + DATE_SUBTRACTION_CORRECTION;

                foundTask.setPlannedDuration(duration);
            }
        } else if (foundTask.getTypeOfTask() == 2){
            if(foundTask.getConnectivity().size() == 0){
                System.out.println("How many connectivity does this task has with other tasks ?");
                int numberOfConnectivity = new KeyboardInput().positiveNonZeroInt();
                if (numberOfConnectivity >= 1){
                    for(int j = 1; j <= numberOfConnectivity; j++){ // if  there is connectivity, it should start from one
                        System.out.println("Connectivity " + ( j ) + ": ");
                        System.out.println("On which task does the current task depend on ? ");
                        String toBeConnectedToTaskID = readExistingTaskID(currentProject);
                        Task toConnectWith = retrieveTask(toBeConnectedToTaskID, currentProject);

                        String connectivityType = readConnectivityType();

                        System.out.print("Enter the duration of connectivity. (It could be negative if applicable) ");
                        long connectivityDuration = new KeyboardInput().Int();

                        foundTask.getConnectivity().add(new Connectivity(toConnectWith, connectivityType, connectivityDuration));
                    }
                }
            }

            LocalDate startDate = new DataEvaluator().extractConnectivityDate("start",foundTask.getConnectivity() );
            LocalDate finishDate = new DataEvaluator().extractConnectivityDate("finish",foundTask.getConnectivity() );

            if(startDate != null &&  finishDate != null){
                if(finishDate.isAfter(startDate)){

                    long duration = ChronoUnit.DAYS.between(startDate, finishDate) + DATE_SUBTRACTION_CORRECTION;

                    foundTask.setPlannedStart(startDate);
                    foundTask.setPlannedFinish(finishDate);
                    foundTask.setPlannedDuration(duration);

                } else {
                    System.out.println("There is an error on connectivity that results an end date before a start date. Correct the data again");
                }
            } else {

                int lengthOfTask = readLengthOfTask();
                if(lengthOfTask == 1){
                    System.out.print("Enter the planned duration of the task : " + foundTask.getName() + " ");
                    long taskPlannedDuration = new KeyboardInput().positiveInt();
                    foundTask.setPlannedDuration(taskPlannedDuration);
                    if(startDate != null){
                        foundTask.setPlannedStart(startDate);
                        finishDate = startDate.plusDays(taskPlannedDuration);
                        foundTask.setPlannedFinish(finishDate);
                    } else {
                        startDate = finishDate.minusDays(taskPlannedDuration);
                        foundTask.setPlannedStart(startDate);
                        foundTask.setPlannedFinish(finishDate);
                    }
                } else if(lengthOfTask == 2){
                    if(startDate != null){
                        System.out.println("Enter the Finish date of the task : " + foundTask.getName() + " ");
                        LocalDate fDate = new DataEvaluator().readDate();

                        while (fDate.isBefore(startDate)){
                            System.out.println("Finish date of the task should not be before a Start date. ");
                            System.out.print("Enter the finish date correctly ");
                            fDate = new DataEvaluator().readDate();
                        }

                        foundTask.setPlannedFinish(fDate);
                        foundTask.setPlannedStart(startDate);
                        long duration = ChronoUnit.DAYS.between(startDate, fDate) + DATE_SUBTRACTION_CORRECTION;
                        foundTask.setPlannedDuration(duration);

                    } else {
                        System.out.println("Enter the Start date of the task : " + foundTask.getName() + " ");
                        LocalDate sDate = new DataEvaluator().readDate();

                        while (sDate.isAfter(finishDate)){
                            System.out.println("Start date of the task should not be after a Finish date. ");
                            System.out.print("Enter the Start date correctly ");
                            sDate = new DataEvaluator().readDate();
                        }

                        foundTask.setPlannedStart(sDate);
                        foundTask.setPlannedFinish(finishDate);
                        long duration = ChronoUnit.DAYS.between(sDate, finishDate) + DATE_SUBTRACTION_CORRECTION;
                        foundTask.setPlannedDuration(duration);
                    }
                }
            }
            checkWithProjectTimes(currentProject);
        }
        pause();
    }//James
    
    public void editTaskType() {
        String taskId ="";
        Task foundTask;
        boolean error = true;
        Project currentProject = projects.get(FIRST);
        do {
            try {
                System.out.println("Enter the id of the task ");
                taskId = new KeyboardInput().Line();
                if (!tasksIDExists(currentProject, taskId)) {
                    System.out.println("This id does not exist");
                }
                error = false;
            } catch (Exception e) {
                System.out.println("Error, wrong input type");
            }
        }while(!tasksIDExists(currentProject, taskId) || error);
        foundTask = retrieveTask(taskId,currentProject);
        System.out.println(foundTask.getName() + "'s type is " + foundTask.getTypeOfTask());
        System.out.println();
        System.out.print("Type info will be overwritten, ");
        int typeOfTask = typeOfTask();
        foundTask.setTypeOfTask(typeOfTask);
        System.out.println("Task Type is successfully updated!");
        pause();
    }//Hamid

    public void editTaskConnectivity() {
        Project currentProject = projects.get(FIRST);
        ArrayList<Connectivity> connectivities;
        String taskId1;
        Task foundTask1;
        Task foundTask2;
        Connectivity foundConnectivity = null;
        int option = 0;

        System.out.println("Enter task id ");
        taskId1 = new KeyboardInput().Line();
        foundTask1 = retrieveTask(taskId1, currentProject);
        connectivities = foundTask1.getConnectivity();

        if(connectivities.size() > 0){
            System.out.println("The existing connectivities are: ");
            for (int i = 0; i < connectivities.size(); i++) {
                System.out.println("    Connectivity " + (i + 1) + " has connection with task id : " +
                        connectivities.get(i).getLinkedTo().getId() + " with type : " + connectivities.get(i).getStartOrFinish() +
                        " and " + connectivities.get(i).getConnectivityDuration() + " Days of gap.");
            }
            System.out.println("Which connectivity do you want to update? (the info will be overwritten)");
            int connectivityNumber = new KeyboardInput().positiveNonZeroInt();
            while (connectivityNumber > connectivities.size()){
                System.out.println("Inter the connectivity number from above correctly :");
                connectivityNumber = new KeyboardInput().positiveNonZeroInt();
            }
            foundConnectivity = connectivities.get(connectivityNumber - 1);//index

            do {
                System.out.println("What do you want to edit, enter number");
                System.out.println("1. Remove connectivity");
                System.out.println("2. Update whole info of Connectivity");
                System.out.println("3. Update the connectivity type");
                System.out.println("4. Change the connectivity duration");
                option = new KeyboardInput().positiveNonZeroInt();
            }while(option > 4);


            if(option == 1) {
                connectivities.remove(foundConnectivity);
                System.out.println("This connectivity is removed");

            }else if (option == 2) {
                System.out.println("On which task does the current task depend on ? ");
                String toBeConnectedToTaskID = readExistingTaskID(currentProject);
                foundTask2 = retrieveTask(toBeConnectedToTaskID, currentProject);

                String connectivityType = readConnectivityType();

                System.out.print("Enter the duration of connectivity in Days. (It could be negative if applicable) ");
                long connectivityDuration = new KeyboardInput().Int();

                foundConnectivity.setLinkedTo(foundTask2);
                foundConnectivity.setStartOrFinish(connectivityType);
                foundConnectivity.setConnectivityDuration(connectivityDuration);
            } else if(option == 3) {
                System.out.println("The connectivity type is " + foundConnectivity.getStartOrFinish());
                String connectivityType = readConnectivityType();
                foundConnectivity.setStartOrFinish(connectivityType);

            }else if(option == 4) {
                System.out.println("The duration of connectivity is " + foundConnectivity.getConnectivityDuration());
                System.out.println("Enter new duration");
                long newDuration = new KeyboardInput().Int();
                foundConnectivity.setConnectivityDuration(newDuration);
            }

        } else {
            System.out.println("There is no connectivity registered on the task ");
        }
        pause();
    }//Hamid

    public void editAllocations(){
        System.out.println("From the following two allocations: ");
        System.out.println("    1. Manpower Allocation (Planned Man-hour)");
        System.out.println("    2. Team Member Allocation (Actual Man-hour)");
        System.out.println("    3. Exit editing ");
        System.out.print("Which one are you interested to edit on ? (1 or 2) ");
        int choice = new KeyboardInput().positiveNonZeroInt();
        while(choice > 3){
            System.out.print("Choose 1, 2, or 3. Enter again. ");
            choice = new KeyboardInput().positiveNonZeroInt();
        }

        switch (choice){
            case 1:
                editManpowerAllocation();
                break;
            case 2:
                editTeamMemberAllocation();
                break;
            default:
                System.out.println("Exiting...");
                break;
        }
        pause();
    }//Hamid

    public void editTeamMemberAllocation() {
        Project currentProject = projects.get(FIRST);
        boolean possibility = false;
        String taskId;
        String memberId;
        Task foundTask;
        TeamMember foundMember;
        TeamMemberAllocation foundAllocation = null;
        int option;
        ArrayList<TeamMemberAllocation> allocations;

        taskId = readExistingTaskID(currentProject);
        System.out.println("On which date?");
        LocalDate date = new DataEvaluator().readDate();

        memberId = readExistingTeamMemberID(currentProject);

        foundTask = retrieveTask(taskId, currentProject);
        foundMember = retrieveTeamMember(currentProject, memberId);
        allocations = foundTask.getActualTeamMembers();

        for (TeamMemberAllocation allocate :allocations){
            if(allocate.getDate().isEqual(date) && allocate.getTeamMember().getId().equals(memberId)) {
                possibility = true;
                foundAllocation = allocate;
            }
        }

        if(possibility){
            System.out.println("What do you want to change? ");
            System.out.println("    1. Remove the team member from this task on this day.");
            System.out.println("    2. Change how many hours the team member has worked on this task.");
            System.out.println("    3. Change the date that the team member worked on this task.");
            System.out.print("Enter choice number ");
            option = new KeyboardInput().positiveNonZeroInt();

            while (option > 3){
                System.out.println("Enter the correct choice number (1, 2, or 3) ");
                option = new KeyboardInput().positiveNonZeroInt();
            }

            if(option == 1) {
                foundTask.getActualTeamMembers().remove(foundAllocation);
                System.out.println();
                System.out.println("Successfully removed.");
            } else if(option == 2) {
                System.out.println(foundMember.getName() +" has worked " + foundAllocation.getWorkHours() + " hours on " + foundTask.getName() + " On date " + date);
                System.out.print("Enter the new work hours for this task, on this day ");
                double newWorkedHours = new KeyboardInput().positiveDouble();
                foundAllocation.setWorkHours(newWorkedHours);
                System.out.println();
                System.out.println("Successfully updated.");
            }else if(option == 3) {
                System.out.println(foundMember.getName() + " has worked on " + foundAllocation.getDate()+ " date");
                System.out.println("What is the new date that you want to register ?");
                LocalDate editDate = new DataEvaluator().readDate();

                while(editDate.isAfter(foundTask.getActualFinish()) || editDate.isBefore(foundTask.getActualStart())){
                    System.out.print("Date should be between Actual Start and Finish dates ");
                    System.out.print(" Enter a correct date ");
                    editDate = new DataEvaluator().readDate();
                }

                foundAllocation.setDate(editDate);
                System.out.println();
                System.out.println("Successfully updated.");
            }
        } else {
            System.out.println("Allocation is not found");
        }
    }//Hamid & OSMAN

    public String readExistingTeamMemberID(Project project){
        String memberID;
        boolean repeatLoop;
        listTeamMembers();
        do{
            repeatLoop = false;
            System.out.print("Enter ID of existing Team Member from the above list ");
            memberID = new KeyboardInput().Line();
            if (retrieveTeamMember(project, memberID) == null){
                System.out.println();
                System.out.println("Member ID is not yet registered.");
                repeatLoop = true;
            }
        } while(repeatLoop);

        return memberID;
    }//OSMAN
    
    public void editManpowerAllocation() {
        Project currentProject = projects.get(FIRST);
        boolean possibility = false;
        String taskId;
        Task foundTask;
        ManpowerAllocation foundAllocation = null;
        int option;
        ArrayList<ManpowerAllocation> allocations;

        taskId = readExistingTaskID(currentProject);
        System.out.println("On which date?");
        LocalDate date = new DataEvaluator().readDate();

        String qualification = readQualification();

        foundTask = retrieveTask(taskId, currentProject);

        allocations = foundTask.getPlannedManpower();

        for (ManpowerAllocation allocate :allocations){
            if(allocate.getDate().isEqual(date) && allocate.getManpower().getQualification().equals(qualification)) {
                possibility = true;
                foundAllocation = allocate;
            }
        }

        if(possibility){
            System.out.println("What do you want to change? ");
            System.out.println("    1. Remove the manpower allocation from this task on this day.");
            System.out.println("    2. Change how many hours a manpower is required on this task on this day.");
            System.out.println("    3. Change the date that the manpower is planned for this task.");
            System.out.print("Enter choice number ");
            option = new KeyboardInput().positiveNonZeroInt();

            while (option > 3){
                System.out.println("Enter the correct choice number (1, 2, or 3) ");
                option = new KeyboardInput().positiveNonZeroInt();
            }

            if(option == 1) {
                foundTask.getPlannedManpower().remove(foundAllocation);
                System.out.println();
                System.out.println("Successfully removed");
            } else if(option == 2) {
                System.out.println(qualification +" is planned to be executed for " + foundAllocation.getWorkHours() + " hours for date " + date);
                System.out.print("Enter the new work hours for this task, on this day ");
                double newWorkedHours = new KeyboardInput().positiveDouble();
                foundAllocation.setWorkHours(newWorkedHours);
                System.out.println();
                System.out.println("Successfully updated.");
            }else if(option == 3) {
                System.out.println(qualification + " is planned to be executed on date " + foundAllocation.getDate());
                System.out.println("What is the new date that you want to register for?");
                LocalDate editDate = new DataEvaluator().readDate();

                while(editDate.isAfter(foundTask.getPlannedFinish()) || editDate.isBefore(foundTask.getPlannedStart())){
                    System.out.print("Date should be between Planned Start and Finish dates ");
                    System.out.print(" Enter a correct date ");
                    editDate = new DataEvaluator().readDate();
                }

                foundAllocation.setDate(editDate);
                System.out.println();
                System.out.println("Successfully updated.");
            }
        } else {
            System.out.println("Manpower Allocation is not found");
        }
    }//OSMAN

    public void editQualification() {
        Project currentProject = projects.get(FIRST);
        String thisId = "";
        boolean error = true;
        do {
            try {
                System.out.println("Enter the id of the team member ");
                thisId = new MiniProject.KeyboardInput().Line();
                error = false;
            } catch (Exception e) {
                System.out.println("Error, wrong input type");
            }
            if (!teamMemberIDExists(currentProject, thisId)) {
                System.out.println("This id does not exist");
            }
        }while(error || !teamMemberIDExists(currentProject,thisId));
        TeamMember foundMember = retrieveTeamMember(currentProject,thisId);
        System.out.println("This member's qualification is " + foundMember.getQualification());
        System.out.println("Qualification info is about to be overwritten");
        System.out.println();
        String qualification = readQualification();
        foundMember.setQualification(qualification);
        System.out.println();
        System.out.println("Qualification info is updated");
    }


    public void removeTeamMember(){
        Project currentProject = projects.get(FIRST);
        if (currentProject!=null) {

            boolean error=true;
            String memberID;

            System.out.println("_______________________");
            System.out.println("  REMOVE TEAM MEMBER");
            System.out.println("_______________________");
            System.out.println("");
            listTeamMembers();

            System.out.println("----------------------------------------------------------------");
            System.out.println(RED + "OBS! Once removed the member must be recreated. You can not undo this action." + CYAN_BRIGHT);

            do {
                try {
                    System.out.println("Enter the id of a team member you wish to remove");
                    memberID = new KeyboardInput().Line();
                    System.out.println("----------------------------------------------------------------");

                    currentProject = projects.get(FIRST);
                    TeamMember member = retrieveTeamMember(currentProject, memberID);

                    if (memberID.equals(member.getId())) {
                        teamMemberIDExists(currentProject, memberID);

                        currentProject.getTeamMembers().remove(member);

                        System.out.println("The team member you have removed is " + member.getName());
                        System.out.println("----------------------------------------------------------------");

                        System.out.println(ANSI_BLUE_BACKGROUND+BLACK_BOLD+"Successfully removed.");
                        System.out.println(RESET+CYAN_BRIGHT);
                        error=false;

                    }

                } catch (Exception e) {
                    System.out.println(RED + "Input must be a number" + CYAN_BRIGHT);
                    error = true;
                }
            } while (error);
        }
    }//Armin


    public void removeTask(){
        Project currentProject = projects.get(FIRST);
        if (currentProject!=null) {

            boolean error = true;
            String taskID;

            System.out.println("_______________________");
            System.out.println("      REMOVE TASK");
            System.out.println("_______________________");
            System.out.println("");
            listTasks();

            System.out.println("----------------------------------------------------------------");
            System.out.println(RED + "OBS! Once removed the task must be recreated. You can not undo this action." + CYAN_BRIGHT);

            do {
                try {
                    System.out.println("Enter the id of the task you wish to remove");
                    taskID = new KeyboardInput().Line();
                    System.out.println("----------------------------------------------------------------");

                    currentProject = projects.get(FIRST);
                    Task task = retrieveTask(taskID, currentProject);
                    if (taskID.equals(task.getId())) {

                        System.out.println("The task you are removing is " + task.getName());
                        System.out.println("----------------------------------------------------------------");

                        currentProject.getTasks().remove(task);

                        System.out.println(ANSI_BLUE_BACKGROUND + BLACK_BOLD + "Successfully removed.");
                        System.out.println(RESET + CYAN_BRIGHT);
                        error = false;
                    }
                } catch (Exception e) {
                    System.out.println(RED + "Input must be a number" + CYAN_BRIGHT);
                    error = true;
                }
            } while (error);

        }pause();
    }//Armin

    public void updateTeamMemberName() {

        listTeamMembers();
        Project currentProject = projects.get(FIRST);
        System.out.println("Enter the id of a team member");
        String memberID = new KeyboardInput().Line();
        System.out.println("----------------------------------------------------------------");

        while (!teamMemberIDExists(currentProject, memberID)) {
            System.out.print("Team member does not exist or wrong ID. Enter correct ID again ");
            memberID = new KeyboardInput().Line();
            System.out.println("----------------------------------------------------------------");
        }

        TeamMember member = retrieveTeamMember(currentProject, memberID);
        System.out.println("The team Member name is currently "+member.getName()+".");
        System.out.println("----------------------------------------------------------------");

        System.out.println("Enter new Name: ");
        String name = new KeyboardInput().Line();
        member.setName(name);

        System.out.println("Name changed to "+ name);
        pause();
    }//Armin

    public boolean teamMemberExists(Project project, String name){
        if(project != null){
            for(int i = 0; i < project.getTeamMembers().size(); i++){
                if(project.getTeamMembers().get(i).getName().equals(name)){
                    return true;
                }
            }
        }
        return false;
    }//Eyuell


    public boolean projectExists (String id){

        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getProjectID().equals(id)) {
                return true;
            }
        }
        return false;
    }//Hamid

    public boolean teamMemberIDExists(Project project, String ID){
        if(project != null){
            for(int i = 0; i < project.getTeamMembers().size(); i++){
                if(project.getTeamMembers().get(i).getId().equals(ID)){
                    return true;
                }
            }
        }
        return false;
    }//Osman


    public Milestone retrieveMilestone(Project project, String id) {
        if(project != null){
            ArrayList<Milestone> milestones = project.getMilestones();

            if(milestones != null){
                for(int i = 0; i < milestones.size(); i++){
                    if(milestones.get(i).getId().equals(id)){
                        return milestones.get(i);
                    }
                }
            } else {
                System.out.println("There are no registered Milestones");
            }
        }
        return null;
    }//Armin

    public TeamMember retrieveTeamMember(Project project, String id) {
        if(project != null){
            ArrayList<TeamMember> teamMembers = project.getTeamMembers();

            if(teamMembers != null){
                for(int i = 0; i < teamMembers.size(); i++){
                    if(teamMembers.get(i).getId().equals(id)){
                        return teamMembers.get(i);
                    }
                }
            } else {
                System.out.println("There are no registered team members ");
            }
        }
        return null;
    }//Osman

    public int typeOfTask(){
        int STAND_ALONE = 1;
        int DEPENDANT = 2;
        int taskChoice;
        System.out.println("Choose task type:");
        System.out.println("    1. Stand Alone task independent of other tasks "); //start date & (duration or finish date)
        System.out.println("    2. Dependent task on other tasks "); //Connected to task, type of connection, duration of connection
        do {
            System.out.print("Which task type option? 1 or 2 ? ");
            taskChoice = new KeyboardInput().Int();
        } while ((taskChoice != STAND_ALONE && taskChoice != DEPENDANT));

        return taskChoice;
    }//Eyuell

    public void updateDates(Task task, LocalDate plannedStart, LocalDate plannedFinish, LocalDate actualStart, LocalDate actualFinish){
        task.setPlannedStart(plannedStart);
        task.setPlannedFinish(plannedFinish);
        task.setActualStart(actualStart);
        task.setActualFinish(actualFinish);
    }//Eyuell

    //checks if task dates are within project dates
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

        long duration = ChronoUnit.DAYS.between(project.getStartDate(), project.getFinishDate()) + DATE_SUBTRACTION_CORRECTION;
        project.setDuration(duration);
    }//Eyuell

    public int readLengthOfTask(){
        int BY_DURATION = 1;
        int BY_FINISH_DATE = 2;
        int taskChoice;
        System.out.println("How is the length of the task defined by: ");
        System.out.println("    1. Duration ");
        System.out.println("    2. Start or Finish date ");
        do{
            System.out.print("Which option? 1 or 2 ? ");
            taskChoice = new KeyboardInput().Int();
        } while ((taskChoice != BY_DURATION && taskChoice != BY_FINISH_DATE));

        return taskChoice;
    }//Eyuell

    public String readConnectivityType(){
        System.out.print("Which type of connectivity does the task has? Is it SS, FS, SF, or FF ? ");
        String connectivityType = new KeyboardInput().Line();
        return new DataEvaluator().connectivityType(connectivityType);
    }//Eyuell

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
    }//Eyuell

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
        } while(repeatLoop);

        return milestoneID;
    }//Eyuell

    public String readExistingTaskID(Project project){
        String taskID;
        boolean repeatLoop;
        listTasks();
        do{
            repeatLoop = false;
            System.out.print("Enter ID of existing task ");
            taskID = new KeyboardInput().Line();
            if (retrieveTask(taskID,project) == null){
                System.out.println();
                System.out.println("Task ID is not yet registered.");
                repeatLoop = true;
            }
        } while(repeatLoop);

        return taskID;
    }//Eyuell

    public Task retrieveTask(String taskID, Project project){

        int EMPTY = 0;
        if (project.getTasks().size() != EMPTY){
            ArrayList<Task> tasksCopy = project.getTasks();
            for (int i = 0; i < tasksCopy.size(); i++){
                if (tasksCopy.get(i).getId().equals(taskID)){
                    return tasksCopy.get(i);
                }
            }
        }else{
            System.out.println("Currently, there are no tasks registered yet");
        }
        System.out.println();
        return null;
    }//Eyuell

    public Task checkTaskExistence(String taskID, Project project){ //this one do not print a message if not found

        int EMPTY = 0;
        if (project.getTasks().size() != EMPTY){
            ArrayList<Task> tasksCopy = project.getTasks();
            for (int i = 0; i < tasksCopy.size(); i++){
                if (tasksCopy.get(i).getId().equals(taskID)){
                    return tasksCopy.get(i);
                }
            }
        }
        System.out.println();
        return null;
    }//Eyuell

    public Milestone checkMilestoneExistence(String milestoneID, Project project){
        Milestone foundMilestone = null;
        int EMPTY = 0;
        ArrayList<Milestone> milestones = project.getMilestones();
        if (milestones.size() != EMPTY){
            for (int i = 0; i < milestones.size(); i++){
                if (milestones.get(i).getId().equals(milestoneID)){
                    foundMilestone = milestones.get(i);
                }
            }
        }
        System.out.println();
        return foundMilestone;
    }//Eyuell

    public Project retrieveProjectByID(String projectID) {
        for (int i = 0; i < projects.size(); i++) {
            if (projectID.equals(projects.get(i).getProjectID())) {
                return projects.get(i);
            }
        }
        return null;
    }//Hamid

    public boolean checkProjectName(String name){
        if(projects != null){
            for(Project project : projects){
                if(project.getName().equals(name)){
                    return true;
                }
            }
        }
        return false;
    }//Eyuell

    //to list team members with ID and Name
    public void listTeamMembers(){
        ArrayList<TeamMember> teamMembers = projects.get(FIRST).getTeamMembers();
        if(teamMembers != null){
            System.out.println("     List of Team members  ");
            for (TeamMember member: teamMembers) {
                System.out.println("ID: " + member.getId() + "  Name: " + member.getName());
            }
        }
        System.out.println();
    }//Eyuell

    //to list Tasks with ID and Name
    public void listTasks(){
        ArrayList<Task> tasks = projects.get(FIRST).getTasks();
        if(tasks != null){
            System.out.println("     List of Tasks  ");
            for (Task task: tasks) {
                System.out.println("ID: " + task.getId() + "  Name: " + task.getName());
            }
        }
        System.out.println();
    }//Eyuell

    //to list Milestones with ID and Name
    public void listMilestones(){
        ArrayList<Milestone> milestones = projects.get(FIRST).getMilestones();
        if(milestones != null){
            System.out.println("     List of Milestones  ");
            for (Milestone milestone: milestones) {
                System.out.println("ID: " + milestone.getId() + "  Name: " + milestone.getName());
            }
        }
        System.out.println();
    }//Eyuell

    //check if all info of task is complete
    public boolean completenessCheck(Task task){
        int outOfThree = 0;
        int DEFAULT_VALUE = 0;
        int TWO = 2;
        int THREE = 3;
        boolean complete = false;
        LocalDate today = LocalDate.now();

        if(task.getPlannedStart() != null){
            outOfThree++;
        }

        if(task.getPlannedDuration() != DEFAULT_VALUE){
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
            task.setActualDuration(ChronoUnit.DAYS.between(task.getActualStart(), task.getActualFinish()) + DATE_SUBTRACTION_CORRECTION);
        }

        if(outOfThree == TWO){
            if(task.getPlannedStart() != null){
                if(task.getPlannedDuration() != DEFAULT_VALUE){
                    LocalDate finish = task.getPlannedStart().plusDays(task.getPlannedDuration());
                    task.setPlannedFinish(finish);
                }else{

                    long duration = ChronoUnit.DAYS.between(task.getPlannedStart(), task.getPlannedFinish()) + DATE_SUBTRACTION_CORRECTION;

                    task.setPlannedDuration(duration);
                }
            }else {
                LocalDate start = task.getPlannedFinish().minusDays(task.getPlannedDuration());
                task.setPlannedStart(start);
            }
            complete = true;
        }

        if (outOfThree == THREE){
            complete = true;
        }
        return complete;
    }//Eyuell

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
                //System.out.println("Total planned hours = " + totalHours);
            }else{
                System.out.println("There are no tasks in the project");
            }
        }else {
            System.out.println("There are no projects to show");
        }
        return totalHours;
    }//Eyuell

    public double plannedHoursTillDate(LocalDate date){
        double totalHours = 0.0;
        if (projects != null){
            Project foundProject = projects.get(FIRST);
            if(foundProject.getTasks() != null){
                int numberOfTasks = foundProject.getTasks().size();
                for(int i = 0; i < numberOfTasks; i++){
                    Task task = foundProject.getTasks().get(i);
                    ArrayList<ManpowerAllocation> allocations = task.getPlannedManpower();
                    for(int j = 0; j < allocations.size(); j++){
                        if(! allocations.get(j).getDate().isAfter(date)){
                            totalHours = totalHours + allocations.get(j).getWorkHours();
                        }
                    }
                }
            }else{
                System.out.println("There are no tasks in the project");
            }
        }else {
            System.out.println("There are no projects to show");
        }
        return totalHours;
    }//Eyuell

    public double totalActualHours(Project foundProject){
        double totalHours = 0.0;
        if (foundProject != null){
            if(foundProject.getTasks() != null){
                int numberOfTasks = foundProject.getTasks().size();
                for(int i = 0; i < numberOfTasks; i++){
                    Task task = foundProject.getTasks().get(i);
                    ArrayList<TeamMemberAllocation> teamMembers = task.getActualTeamMembers();
                    for(int j = 0; j < teamMembers.size(); j++){
                        totalHours = (totalHours + teamMembers.get(j).getWorkHours());
                    }
                }
                System.out.println("Total Actual hours = " + (float)totalHours);
            }else{
                System.out.println("There are no tasks in the project");
            }
        }else {
            System.out.println("There are no projects to show");
        }
        return totalHours;
    }//Eyuell

    public double actualHoursTillDate(LocalDate date){
        double totalHours = 0.0;
        if (projects != null){
            Project foundProject = projects.get(FIRST);
            if(foundProject.getTasks() != null){
                int numberOfTasks = foundProject.getTasks().size();
                for(int i = 0; i < numberOfTasks; i++){
                    Task task = foundProject.getTasks().get(i);
                    ArrayList<TeamMemberAllocation> teamMembers = task.getActualTeamMembers();
                    for(int j = 0; j < teamMembers.size(); j++){
                        if(! teamMembers.get(j).getDate().isAfter(date)){
                            totalHours = totalHours + teamMembers.get(j).getWorkHours();
                        }
                    }
                }

            }else{
                System.out.println("There are no tasks in the project");
            }
        }else {
            System.out.println("There are no projects to show");
        }
        return totalHours;
    }//Eyuell

    public double actualCost(LocalDate date){

        /*Actual Cost (AC)
        Also known as Actual Cost of Work Performed (ACWP),
        Actual Cost is the actual to-date cost of the task.
        AC = Actual Cost of the Task*/

        double actualSum = actualHoursTillDate(date);

        return  Math.round(actualSum * SALARY * 100.0)/100.0;
    }//Hamid

    public LocalDate choiceOfDate(){

        ArrayList<Task> tasks = projects.get(FIRST).getTasks();

        LocalDate today = LocalDate.now();
        LocalDate tasksStartDate = tasksStartAndFinishDates("start", tasks);
        LocalDate tasksFinishDate = tasksStartAndFinishDates("finish", tasks);

        System.out.println();
        System.out.print("Enter the date of interest ");
        LocalDate dateOfInterest = new DataEvaluator().readDate();

        while(! dateOfInterest.isAfter(tasksStartDate) && ! dateOfInterest.isAfter(today) && ! dateOfInterest.isBefore(tasksFinishDate)){
            System.out.println("Choice of date should be between " + tasksStartDate + " and " + tasksFinishDate);
            System.out.print("Enter the info again ");
            dateOfInterest = new DataEvaluator().readDate();
        }
        return dateOfInterest;
    }//Eyuell

    public void printEmpty(int space){
        for(int i = 0; i < space; i++){
            System.out.print(" ");
        }
    }//Eyuell

    public void pause (){
        System.out.println();
        System.out.println("Enter to continue... ");
        new KeyboardInput().enter();
    }//Eyuell

    public String readQualification(){
        final int SOFT_DEV = 1;
        final int GAME_DEV = 2;
        final int SOFT_ENG = 3;
        final int REQ_ENG = 4;
        final int TEST_ENG = 5;
        final int NETW_ADMIN = 6;
        final int AVAILABLE_CHOICES = 6;

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
        }while(choice > AVAILABLE_CHOICES);

        switch (choice){
            case SOFT_DEV:
                result = "Software Developer";
                break;
            case GAME_DEV:
                result = "Video Game Developer";
                break;
            case SOFT_ENG:
                result = "Software Engineer";
                break;
            case REQ_ENG:
                result = "Requirements Engineer";
                break;
            case TEST_ENG:
                result = "Test Engineer";
                break;
            case NETW_ADMIN:
                result = "Network Administrator";
                break;
            default: //otherwise
                System.out.println("You may want to choose the correct number !");
                break;
        }
        return result;
    }//Eyuell

    public LocalDate tasksStartAndFinishDates (String startOrFinish, ArrayList<Task> tasks){
        int ZERO = 0;
        int ONE = 1;
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

            if(plannedStartDate != null){
                starts.add(plannedStartDate);
            }

            if(plannedFinishDate != null){
                finishes.add(plannedFinishDate);
            }
        }

        if(startOrFinish.equals("start") && starts.size() > ZERO){
            Collections.sort(starts);
            result = starts.get(ZERO);
        }else if(startOrFinish.equals("finish") && finishes.size() > ZERO){
            int numberOfFinishes = finishes.size();
            if(numberOfFinishes > 0){
                Collections.sort(finishes);
                result = finishes.get(numberOfFinishes - ONE);
            }
        }
        return result;
    }//Eyuell

    public boolean readActualTaskStatus(){
        int COMPLETED = 1;
        int ACTIVE = 2;
        boolean repeat;
        int option;
        System.out.println("What is the status of the task ?");
        do{
            repeat = false;
            System.out.println("    1. Completed ");
            System.out.println("    2. Active ");
            System.out.println("Enter status option");
            option = new KeyboardInput().positiveInt();
            if(option > ACTIVE){
                repeat = true;
            }
        } while (repeat);
        if (option == COMPLETED){
            return true;
        } else{
            return false;
        }
    }//Eyuell

    //After tasks are complete, the project dates are defined
    public void projectCompletenessCheck(){
        if(projects != null){
            for(int m = 0; m < projects.size(); m++){
                for(int i = 0; i < projects.get(m).getTasks().size(); i++){
                    completenessCheck(projects.get(m).getTasks().get(i));
                }

                Project currentProject = projects.get(m);

                if(currentProject.getStartDate() == null){
                    if(currentProject.getTasks() != null){
                        LocalDate start = tasksStartAndFinishDates("start",currentProject.getTasks());
                        currentProject.setStartDate(start);
                    }
                }

                if(currentProject.getFinishDate() == null){

                    LocalDate finish = tasksStartAndFinishDates("finish",currentProject.getTasks());
                    currentProject.setFinishDate(finish);

                    if(currentProject.getStartDate() != null){
                        long duration = ChronoUnit.DAYS.between(currentProject.getStartDate(), finish) + DATE_SUBTRACTION_CORRECTION;
                        currentProject.setDuration(duration);
                    }
                }
            }
        }
    }//Eyuell

    public void writeProjectToJsonFile(){

        try {
            FileWriter file = new FileWriter("MiniProject/MiniProject.json");

            Gson gsonStructured = new GsonBuilder().setPrettyPrinting().create();
            file.write(gsonStructured.toJson(projects));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }//Eyuell

    public void readFromJsonFile()throws Exception {
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader("MiniProject/MiniProject.json"));
        projects = gson.fromJson(br, new TypeToken<ArrayList<Project>>(){}.getType());
    }//Eyuell

    public void readFromSystemClass(){
        double BUDGET = 404175.0; //SEK budget (1585 * 255)
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.parse("2018-11-15");
        Project mgmtTool = new Project("Project Management Tool Development","1", startDate);
        mgmtTool.setBudget(BUDGET);
        projects.add(mgmtTool);

        Task general = new Task("General Tasks","1",1);
        Task cost = new Task("Cost Related","2",1);
        Task risk = new Task("Risk Matrix","3",1);
        Task part = new Task("Participation on tasks","4",1);
        Task spent = new Task("Time spent on project","5",1);
        Task schedule = new Task("Project schedule","6",1);

        Milestone milestone1 = new Milestone("Executing on Console","1",LocalDate.parse("2018-12-10"));
        Milestone milestone2 = new Milestone("Executing using json","2",LocalDate.parse("2018-12-21"));
        Milestone milestone3 = new Milestone("Executing graphically","3",LocalDate.parse("2019-01-07"));

        mgmtTool.getMilestones().add(milestone1);
        mgmtTool.getMilestones().add(milestone2);
        mgmtTool.getMilestones().add(milestone3);

        mgmtTool.getTasks().add(general);
        mgmtTool.getTasks().add(cost);
        mgmtTool.getTasks().add(risk);
        mgmtTool.getTasks().add(part);
        mgmtTool.getTasks().add(spent);
        mgmtTool.getTasks().add(schedule);

        general.setPlannedStart(LocalDate.parse("2018-11-16"));
        general.setPlannedFinish(LocalDate.parse("2019-01-20"));

        cost.setPlannedStart(LocalDate.parse("2018-11-26"));
        cost.setPlannedFinish(LocalDate.parse("2019-01-11"));

        risk.setPlannedStart(LocalDate.parse("2018-11-26"));
        risk.setPlannedFinish(LocalDate.parse("2019-01-11"));

        part.setPlannedStart(LocalDate.parse("2018-11-26"));
        part.setPlannedFinish(LocalDate.parse("2019-01-11"));

        spent.setPlannedStart(LocalDate.parse("2018-11-26"));
        spent.setPlannedFinish(LocalDate.parse("2019-01-11"));

        schedule.setPlannedStart(LocalDate.parse("2018-11-26"));
        schedule.setPlannedFinish(LocalDate.parse("2019-01-11"));

        //
        general.setActualStart(LocalDate.parse("2018-11-16"));
        general.setActualFinish(today);

        cost.setActualStart(LocalDate.parse("2018-11-26"));
        cost.setActualFinish(today);

        risk.setActualStart(LocalDate.parse("2018-11-26"));
        risk.setActualFinish(today);

        part.setActualStart(LocalDate.parse("2018-11-26"));
        part.setActualFinish(today);

        spent.setActualStart(LocalDate.parse("2018-11-26"));
        spent.setActualFinish(today);

        schedule.setActualStart(LocalDate.parse("2018-11-26"));
        schedule.setActualFinish(today);

        completenessCheck(general);
        completenessCheck(cost);
        completenessCheck(risk);
        completenessCheck(part);
        completenessCheck(spent);
        completenessCheck(schedule);

        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-16")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-17")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-18")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-19")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-20")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-21")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-22")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-23")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-24")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-25")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-26")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-27")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-28")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-29")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-11-30")));

        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-01")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-02")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-03")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-04")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-05")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-06")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-07")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-08")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-09")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-10")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-11")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-12")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-13")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-14")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-15")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-16")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-17")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-18")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-19")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-20")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-21")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-22")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-23")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-24")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-25")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-26")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-27")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-28")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-29")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-30")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2018-12-31")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2019-01-01")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-02")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-03")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-04")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-05")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-06")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-07")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-08")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-09")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-10")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-11")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-12")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-13")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-14")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-15")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-16")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-17")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),20,LocalDate.parse("2019-01-18")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2019-01-19")));
        general.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),10,LocalDate.parse("2019-01-20")));


        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-26")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-27")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-28")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-29")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-30")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-01")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-02")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-03")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-04")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-05")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-06")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-07")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-08")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-09")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-10")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-11")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-12")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-13")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-14")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-15")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-16")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-17")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-18")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-19")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-20")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-21")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-22")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-23")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-24")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-25")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-26")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-27")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-28")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-29")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-30")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-31")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2019-01-01")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-02")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-03")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-04")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-05")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-06")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-07")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-08")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-09")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-10")));
        cost.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-11")));

        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-26")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-27")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-28")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-29")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-30")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-01")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-02")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-03")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-04")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-05")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-06")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-07")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-08")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-09")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-10")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-11")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-12")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-13")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-14")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-15")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-16")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-17")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-18")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-19")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-20")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-21")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-22")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-23")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-24")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-25")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-26")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-27")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-28")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-29")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-30")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-31")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2019-01-01")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-02")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-03")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-04")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-05")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-06")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-07")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-08")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-09")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-10")));
        risk.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-11")));

        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-26")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-27")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-28")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-29")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-30")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-01")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-02")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-03")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-04")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-05")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-06")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-07")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-08")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-09")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-10")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-11")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-12")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-13")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-14")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-15")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-16")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-17")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-18")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-19")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-20")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-21")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-22")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-23")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-24")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-25")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-26")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-27")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-28")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-29")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-30")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-31")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2019-01-01")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-02")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-03")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-04")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-05")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-06")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-07")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-08")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-09")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-10")));
        part.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-11")));


        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-26")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-27")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-28")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-29")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-30")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-01")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-02")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-03")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-04")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-05")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-06")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-07")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-08")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-09")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-10")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-11")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-12")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-13")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-14")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-15")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-16")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-17")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-18")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-19")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-20")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-21")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-22")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-23")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-24")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-25")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-26")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-27")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-28")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-29")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-30")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-31")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2019-01-01")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-02")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-03")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-04")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-05")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-06")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-07")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-08")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-09")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-10")));
        spent.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-11")));

        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-26")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-27")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-28")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-29")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-11-30")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-01")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-02")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-03")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-04")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-05")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-06")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-07")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-08")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-09")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-10")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-11")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-12")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-13")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-14")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-15")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-16")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-17")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-18")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-19")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-20")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-21")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-22")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-23")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-24")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-25")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-26")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-27")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-28")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-29")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-30")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2018-12-31")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),3,LocalDate.parse("2019-01-01")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-02")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-03")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-04")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-05")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-06")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-07")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-08")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-09")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-10")));
        schedule.getPlannedManpower().add(new ManpowerAllocation(new Manpower("TBA"),4,LocalDate.parse("2019-01-11")));

        TeamMember armin = new TeamMember("Armin Ghoroghi","1","Software Developer");
        TeamMember james = new TeamMember("James Wagabaza","2","Software Developer");
        TeamMember osman = new TeamMember("Osman Osman","3","Software Developer");
        TeamMember hamid = new TeamMember("Hamidreza Yaghoobzadeh","4","Software Developer");
        TeamMember eyuell = new TeamMember("Eyuell Hailemichael","5","Software Developer");

        mgmtTool.getTeamMembers().add(armin);
        mgmtTool.getTeamMembers().add(james);
        mgmtTool.getTeamMembers().add(osman);
        mgmtTool.getTeamMembers().add(hamid);
        mgmtTool.getTeamMembers().add(eyuell);

        long numberOfDays = ChronoUnit.DAYS.between(LocalDate.parse("2018-11-26"), today) + 1;

        //the hours here are just to make different times for each team member
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-11-26")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-11-27")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-11-28")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-11-29")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-11-30")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-12-01")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-12-02")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-12-03")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-12-04")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-12-05")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-12-06")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-12-07")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-12-08")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-12-09")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1, LocalDate.parse("2018-12-10")));

        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-11-26")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-11-27")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-11-28")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-11-29")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-11-30")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-12-01")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-12-02")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-12-03")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-12-04")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-12-05")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-12-06")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-12-07")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-12-08")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-12-09")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1, LocalDate.parse("2018-12-10")));

        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-11-26")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-11-27")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-11-28")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-11-29")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-11-30")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-12-01")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-12-02")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-12-03")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-12-04")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-12-05")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-12-06")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-12-07")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-12-08")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-12-09")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1, LocalDate.parse("2018-12-10")));

        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-11-26")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-11-27")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-11-28")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-11-29")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-11-30")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-12-01")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-12-02")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-12-03")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-12-04")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-12-05")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-12-06")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-12-07")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-12-08")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-12-09")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1, LocalDate.parse("2018-12-10")));

        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-16")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-17")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-18")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-19")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-20")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-21")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-22")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-23")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-24")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-25")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-26")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-27")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-28")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-29")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-11-30")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-12-01")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-12-02")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-12-03")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-12-04")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-12-05")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-12-06")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-12-07")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-12-08")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-12-09")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1, LocalDate.parse("2018-12-10")));

        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-11-26")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-11-27")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-11-28")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-11-29")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-11-30")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-01")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-02")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-03")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-04")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-05")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-06")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-07")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-08")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-09")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-10")));

        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-11-26")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-11-27")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-11-28")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-11-29")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-11-30")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-01")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-02")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-03")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-04")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-05")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-06")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-07")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-08")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-09")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-10")));

        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-11-26")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-11-27")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-11-28")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-11-29")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-11-30")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-01")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-02")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-03")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-04")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-05")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-06")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-07")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-08")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-09")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-10")));

        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-11-26")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-11-27")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-11-28")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-11-29")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-11-30")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-01")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-02")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-03")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-04")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-05")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-06")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-07")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-08")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-09")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-10")));

        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-11-26")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-11-27")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-11-28")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-11-29")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-11-30")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-01")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-02")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-03")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-04")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-05")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-06")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-07")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-08")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-09")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-10")));

        mgmtTool.getRisks().add(new Risk("1","Lack of Trust", "Predicted",0.75,7));
        mgmtTool.getRisks().add(new Risk("2","Conflict and tension", "Predicted",0.8,9));
        mgmtTool.getRisks().add(new Risk("3","Lack of Commitment", "Predicted",0.9,9));
        mgmtTool.getRisks().add(new Risk("4","Weak Information sharing", "Predicted",0.5,7));
        mgmtTool.getRisks().add(new Risk("5","Misalignment with team-Goal", "Predicted",0.75,6));
        mgmtTool.getRisks().add(new Risk("6","Lack of Team spirit", "Predicted",0.6,8));
        mgmtTool.getRisks().add(new Risk("7","Lack of Organisation", "Predicted",0.55,5.5));
        mgmtTool.getRisks().add(new Risk("8","Being out of schedule", "Predicted",0.4,5));
        mgmtTool.getRisks().add(new Risk("9","Lack of Knowledge", "Predicted",0.65,6));

        mgmtTool.getRisks().get(0).setRiskStatus("Managed");
        mgmtTool.getRisks().get(1).setRiskStatus("Disappeared");
        mgmtTool.getRisks().get(2).setRiskStatus("Disappeared");
        mgmtTool.getRisks().get(3).setRiskStatus("Disappeared");
        mgmtTool.getRisks().get(4).setRiskStatus("Effort Made");
        mgmtTool.getRisks().get(5).setRiskStatus("Disappeared");
        mgmtTool.getRisks().get(6).setRiskStatus("Managed");
        mgmtTool.getRisks().get(7).setRiskStatus("Managed");
        mgmtTool.getRisks().get(8).setRiskStatus("Effort Made");

        //additional actual team data
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-11")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-12")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-13")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-14")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-15")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-16")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-17")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-18")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-19")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-20")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-21")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-22")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-23")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-24")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-25")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-26")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-27")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-28")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-29")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-30")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2018-12-31")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2019-01-01")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(armin, 1.5, LocalDate.parse("2019-01-02")));

        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-11")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-12")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-13")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-14")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-15")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-16")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-17")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-18")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-19")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-20")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-21")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-22")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-23")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-24")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-25")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-26")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-27")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-28")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-29")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-30")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2018-12-31")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2019-01-01")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(james, 1.5, LocalDate.parse("2019-01-02")));

        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-11")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-12")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-13")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-14")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-15")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-16")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-17")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-18")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-19")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-20")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-21")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-22")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-23")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-24")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-25")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-26")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-27")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-28")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-29")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-30")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2018-12-31")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2019-01-01")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(osman, 1.5, LocalDate.parse("2019-01-02")));

        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-11")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-12")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-13")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-14")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-15")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-16")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-17")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-18")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-19")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-20")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-21")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-22")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-23")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-24")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-25")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-26")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-27")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-28")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-29")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-30")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2018-12-31")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2019-01-01")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 1.5, LocalDate.parse("2019-01-02")));

        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-11")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-12")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-13")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-14")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-15")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-16")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-17")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-18")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-19")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-20")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-21")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-22")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-23")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-24")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-25")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-26")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-27")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-28")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-29")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-30")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2018-12-31")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2019-01-01")));
        general.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 1.5, LocalDate.parse("2019-01-02")));

        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-11")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-12")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-13")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-14")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-15")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-16")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-17")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-18")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-19")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-20")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-21")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-22")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-23")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-24")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-25")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-26")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-27")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-28")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-29")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-30")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2018-12-31")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2019-01-01")));
        cost.getActualTeamMembers().add(new TeamMemberAllocation(armin, 3.1, LocalDate.parse("2019-01-02")));

        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-11")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-12")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-13")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-14")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-15")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-16")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-17")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-18")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-19")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-20")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-21")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-22")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-23")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-24")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-25")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-26")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-27")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-28")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-29")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-30")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2018-12-31")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2019-01-01")));
        risk.getActualTeamMembers().add(new TeamMemberAllocation(james, 3.2, LocalDate.parse("2019-01-02")));

        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-11")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-12")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-13")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-14")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-15")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-16")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-17")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-18")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-19")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-20")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-21")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-22")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-23")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-24")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-25")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-26")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-27")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-28")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-29")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-30")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2018-12-31")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2019-01-01")));
        part.getActualTeamMembers().add(new TeamMemberAllocation(osman, 3.3, LocalDate.parse("2019-01-02")));

        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-11")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-12")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-13")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-14")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-15")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-16")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-17")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-18")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-19")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-20")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-21")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-22")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-23")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-24")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-25")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-26")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-27")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-28")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-29")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-30")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2018-12-31")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2019-01-01")));
        spent.getActualTeamMembers().add(new TeamMemberAllocation(hamid, 3.4, LocalDate.parse("2019-01-02")));

        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-11")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-12")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-13")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-14")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-15")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-16")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-17")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-18")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-19")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-20")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-21")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-22")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-23")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-24")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-25")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-26")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-27")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-28")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-29")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-30")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2018-12-31")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2019-01-01")));
        schedule.getActualTeamMembers().add(new TeamMemberAllocation(eyuell, 3.5, LocalDate.parse("2019-01-02")));

    }//Eyuell & James

}
