package MiniProject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

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
        ProjectManagementTool start = new ProjectManagementTool();
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

        readStoredFile();

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

    }

    public void readStoredFile(){
        //create project
        LocalDate startDate = LocalDate.parse("2018-01-01");
        Project p1 = new Project("Project Management Tool Development","1", startDate);
        projects.add(p1);

        Task t1 = new Task("code1","1",1);
        Task t2 = new Task("code2","2",2);
        Task t3 = new Task("code3","3",2);
        Task t4 = new Task("code4","4",2);

        p1.addTask(t1);
        p1.addTask(t2);
        p1.addTask(t3);
        p1.addTask(t4);

        t1.setPlannedStart(LocalDate.parse("2018-02-02"));
        t1.setPlannedDuration(6);

        t1.setActualStart(LocalDate.parse("2018-02-04"));
        t1.setActualFinish(LocalDate.parse("2018-02-11"));

        t2.setPlannedStart(LocalDate.parse("2018-02-07"));
        t2.setPlannedDuration(5);

        t2.setActualStart(LocalDate.parse("2018-02-15"));
        t2.setActualFinish(LocalDate.parse("2018-03-15"));

        t3.setPlannedStart(LocalDate.parse("2018-02-04"));
        t3.setPlannedDuration(7);

        t4.setPlannedStart(LocalDate.parse("2018-02-12"));
        t4.setPlannedDuration(5);

        completenessCheck(t1);
        completenessCheck(t2);
        completenessCheck(t3);
        completenessCheck(t4);
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
        //Eyuells part
        System.out.println();
        System.out.println("Eyuell working on this part");
    }

    public void monitorProgress(){
        //Armins part
        System.out.println();
        System.out.println("Armin working on this part");
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