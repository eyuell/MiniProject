package ProjectScheduling;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;

public class ProjectManagementTool {
    private ArrayList<Project> projects;
    private ArrayList<Manpower> manpower;
    private ArrayList<TeamMember> teamMembers;
    public ProjectManagementTool(){

        this.projects = new ArrayList<>();
        this.manpower = new ArrayList<>();
        this.teamMembers = new ArrayList<>();
    }

    public static void main(String [] args){
        System.out.println("This program works on project schedules");
        ProjectScheduling.ProjectManagementTool start = new ProjectScheduling.ProjectManagementTool();
        start.run();
    }

    public void printMenuOption(){
        System.out.println("========================");
        System.out.println("1. Display Project Schedule");
        System.out.println("2. Monitor Progress");
        System.out.println("3. Monitor Time Spent");
        System.out.println("4. Monitor Participation");
        System.out.println("5. Monitor Risk");
        System.out.println("6. QUIT Program");
        System.out.println("========================");
        System.out.println();
    }

    public void run() {
        int optionChoice;
        final int DISPLAY_SCHEDULE = 1;
        final int MONITOR_PROGRESS = 2;
        final int MONITOR_TIME_SPENT = 3;
        final int MONITOR_PARTICIPATION = 4;
        final int MONITOR_RISK = 5;
        final int QUIT = 6;

        readStoredFile();//almost reading json file
        //readFromJsonFile()

        do {
            printMenuOption();
            System.out.print("Choose menu option ");
            optionChoice = new KeyboardInput().Int();     //keyboard input for the project

            switch (optionChoice) {

                case DISPLAY_SCHEDULE:
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

        //here, we may need to write to json file
        //writeToJsonFile()

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

        p1.addTask(t1);
        p1.addTask(t2);
        p1.addTask(t3);
        p1.addTask(t4);
        p1.addTask(t5);
        p1.addTask(t6);

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

        manpower.add(new Manpower("TBA",207,t1,LocalDate.parse("2018-11-16")));
        manpower.add(new Manpower("TBA",207,t1,LocalDate.parse("2018-12-10")));
        manpower.add(new Manpower("TBA",208,t1,LocalDate.parse("2018-12-26")));
        manpower.add(new Manpower("TBA",208,t1,LocalDate.parse("2019-01-20")));

        manpower.add(new Manpower("TBA",37,t2,LocalDate.parse("2018-11-26")));
        manpower.add(new Manpower("TBA",38,t2,LocalDate.parse("2018-12-06")));
        manpower.add(new Manpower("TBA",38,t2,LocalDate.parse("2018-12-26")));
        manpower.add(new Manpower("TBA",38,t2,LocalDate.parse("2019-01-11")));

        manpower.add(new Manpower("TBA",37,t3,LocalDate.parse("2018-11-26")));
        manpower.add(new Manpower("TBA",38,t3,LocalDate.parse("2018-12-06")));
        manpower.add(new Manpower("TBA",38,t3,LocalDate.parse("2018-12-26")));
        manpower.add(new Manpower("TBA",38,t3,LocalDate.parse("2019-01-11")));

        manpower.add(new Manpower("TBA",37,t4,LocalDate.parse("2018-11-26")));
        manpower.add(new Manpower("TBA",38,t4,LocalDate.parse("2018-12-06")));
        manpower.add(new Manpower("TBA",38,t4,LocalDate.parse("2018-12-26")));
        manpower.add(new Manpower("TBA",38,t4,LocalDate.parse("2019-01-11")));

        manpower.add(new Manpower("TBA",37,t5,LocalDate.parse("2018-11-26")));
        manpower.add(new Manpower("TBA",38,t5,LocalDate.parse("2018-12-06")));
        manpower.add(new Manpower("TBA",38,t5,LocalDate.parse("2018-12-26")));
        manpower.add(new Manpower("TBA",38,t5,LocalDate.parse("2019-01-11")));

        manpower.add(new Manpower("TBA",37,t6,LocalDate.parse("2018-11-26")));
        manpower.add(new Manpower("TBA",38,t6,LocalDate.parse("2018-12-06")));
        manpower.add(new Manpower("TBA",38,t6,LocalDate.parse("2018-12-26")));
        manpower.add(new Manpower("TBA",38,t6,LocalDate.parse("2019-01-11")));


        long numberOfDays = ChronoUnit.DAYS.between(LocalDate.parse("2018-11-26"), today);

        //the hours here are just to make different times for each team member
        teamMembers.add(new TeamMember("Armin Ghoroghi", "Software Developer", numberOfDays * 3.1, t2,today));
        teamMembers.add(new TeamMember("James Wagabaza", "Software Developer", numberOfDays * 3.2, t3,today));
        teamMembers.add(new TeamMember("Osman Osman", "Software Developer", numberOfDays * 3.3, t4,today));
        teamMembers.add(new TeamMember("Hamidreza Yaghoobzadeh", "Software Developer", numberOfDays * 3.4, t5,today));
        teamMembers.add(new TeamMember("Eyuell Hailemichael", "Software Developer", numberOfDays * 3.5, t6,today));

    }

    public boolean completenessCheck(Task task){
        int outOfThree = 0;
        boolean complete = false;

        if(task.getPlannedStart() != null){
            outOfThree++;
        }

        if(task.getPlannedDuration() != 0){
            outOfThree++;
        }

        if(task.getPlannedFinish() != null){
            outOfThree++;
        }

        if(outOfThree == 2){
            if(task.getPlannedStart() != null){
                if(task.getPlannedDuration() != 0){
                    LocalDate finish = task.getPlannedStart().plusDays(task.getPlannedDuration());
                    task.setPlannedFinish(finish);
                }else{

                    long duration = ChronoUnit.DAYS.between(task.getPlannedStart(), task.getPlannedFinish());

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

    public void printPlannedAndActualSchedule(){
        Project foundProject = retrieveProject();
        if (foundProject != null){
            if(foundProject.getTasks() != null){
                ArrayList<Task> tasks = foundProject.getTasks();
                int taskNameLength = tasks.get(0).getName().length();
                int taskIdLength = tasks.get(0).getId().length();
                int smallestIndent = taskNameLength + taskIdLength;

                for (int i = 1; i < tasks.size(); i++) {
                    taskNameLength = tasks.get(i).getName().length();
                    taskIdLength = tasks.get(i).getId().length();
                    int indent = taskNameLength + taskIdLength;
                    if(indent > smallestIndent){
                        smallestIndent = indent;
                    }
                }
                System.out.println(); //blank line
                LocalDate localDate = LocalDate.now();
                LocalDate tasksStartDate = tasksStartAndFinishDates("start",tasks);
                LocalDate tasksFinishDate = tasksStartAndFinishDates("finish",tasks);

                //project tasks total duration
                long duration = ChronoUnit.DAYS.between(tasksStartDate, tasksFinishDate);

                printEmpty(smallestIndent);
                System.out.println("        Schedule for Project " + foundProject.getName() + " (" + foundProject.getProjectID() + ")");
                printEmpty(smallestIndent);
                System.out.println("        Date: " + localDate);
                System.out.println();
                System.out.println();
                int beforeText = smallestIndent/2;
                printEmpty(beforeText);
                System.out.print("Tasks");
                int afterText = smallestIndent-beforeText - 5 + 4;
                printEmpty(afterText);
                for (long i = 0; i <= duration; i++){//the days printed
                    LocalDate day = tasksStartDate.plusDays(i);
                    System.out.print("|" + day + "|");//12 pixels per day ?
                }
                System.out.println();
                System.out.println();

                for(int i = 0; i < tasks.size();i++){
                    Task currentTask = tasks.get(i);
                    int taskIndent = smallestIndent - currentTask.getName().length() - currentTask.getId().length() + 4 - 2;

                    //planned plot
                    System.out.print(currentTask.getName()+"(" + currentTask.getId()+")");
                    printEmpty(taskIndent);
                    boolean print;
                    for (long j = 0; j <= duration; j++){// project tasks duration
                        LocalDate day = tasksStartDate.plusDays(j);
                        print = true;
                        for(long m = 0; m <= currentTask.getPlannedDuration(); m++){
                            LocalDate taskDates = currentTask.getPlannedStart().plusDays(m);
                            if(day.equals(taskDates)){
                                System.out.print("|==========|");//12 pixels per day ?
                                print = false;
                            }
                        }
                        if(print){
                            System.out.print("|          |");//12 pixels per day ?
                        }
                    }
                    System.out.println();

                    //Actual plot
                    if(currentTask.getActualStart() != null){
                        printEmpty(smallestIndent + 4);
                        for (long j = 0; j <= duration; j++){// project tasks duration
                            LocalDate day = tasksStartDate.plusDays(j);

                            LocalDate thisStart = currentTask.getActualStart();
                            LocalDate thisFinish = currentTask.getActualFinish();

                            long actualDuration = ChronoUnit.DAYS.between(thisStart, thisFinish);

                            print = true;
                            for(int m = 0; m <= actualDuration; m++){
                                LocalDate taskDates = thisStart.plusDays(m);
                                if(day.equals(taskDates)){
                                    System.out.print("|**********|");//12 pixels per day ?
                                    print = false;
                                }
                            }
                            if(print){
                                System.out.print("|          |");//12 pixels per day ?
                            }
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
                //
            }else{
                System.out.println("There are no tasks in the project");
            }

        }else {
            System.out.println("There are no projects to show");
        }
        pause();
    }

    public void printEmpty(int space){
        for(int i = 0; i < space; i++){
            System.out.print(" ");
        }
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

    public void pause (){
        System.out.println();
        System.out.println("Enter to continue ... ");
        new KeyboardInput().enter();
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

    public void monitorProgress(){
        //Armins part
        System.out.println();
        System.out.println("Armin working on this part");

        //for checking purpose (eyuell)
        double plannedSum = 0.0;
        for(int i = 0; i < manpower.size();i++){
            plannedSum = plannedSum + manpower.get(i).getHoursWorked();
        }

        double actualSum = 0.0;
        for(int i = 0; i < teamMembers.size();i++){
            actualSum = actualSum + teamMembers.get(i).getHoursWorked();
        }

        double plannedBudget = plannedSum * 225.0;
        double actualCost = actualSum * 225.0;
        LocalDate today = LocalDate.now();
        LocalDate tasksStartDate = tasksStartAndFinishDates("start",tasks);
        LocalDate tasksFinishDate = tasksStartAndFinishDates("finish",tasks);

        //project tasks total duration
        long projectDuration = ChronoUnit.DAYS.between(tasksStartDate, tasksFinishDate);
        long durationTillToday = ChronoUnit.DAYS.between(tasksStartDate, today);
        
        System.out.println("Project budjet = " + plannedBudget);
        System.out.println("Project cost = " + actualCost);
        System.out.println("Programm Excuted Progress = " + (actualCost/plannedBudget)*100.0 +" %"); //this is only monetary wise
        System.out.println("Programm Schedule Progress = " + (durationTillToday/projectDuration)*100.0 +" %"); //this is time wise
    }

    public void monitorTimeSpent(){
        //Hamids Part ?
        System.out.println();
        System.out.println("Hamid working on this part");
    }

    public void monitorParticipation(){
        //Osmans part ?
        System.out.println();
        System.out.println("Osman working on this part");
    }

    public void monitorRisk(){
        //James Part
        System.out.println();
        System.out.println("James working on this part");
    }

}
