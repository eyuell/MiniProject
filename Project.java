package MiniProject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Project {
    private String name;
    private String projectID;
    private LocalDate startDate;
    private LocalDate finishDate;
    private long duration;
    private ArrayList<Milestone> milestones;
    private ArrayList<Task> tasks;
    private ArrayList<TeamMember> teamMembers;
    private ArrayList<Risk> risks;

    public Project (String name, String projectID, LocalDate startDate){
        this.name = name;
        this.projectID = projectID;
        this.startDate = startDate;
        this.milestones = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.teamMembers = new ArrayList<>();
        this.risks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getProjectID() {
        return projectID;
    }

    public long getDuration() {
        return duration;
    }

    public LocalDate getFinishDate() { return finishDate; }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public LocalDate getStartDate() { return startDate; }

    public ArrayList<TeamMember> getTeamMembers() { return teamMembers; }

    public ArrayList<Risk> getRisks() { return risks; }

    public ArrayList<Milestone> getMilestones() { return milestones; }

    public void setName(String name) {
        this.name = name;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public void setFinishDate(LocalDate finishDate) { this.finishDate = finishDate; }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}