package Git.MiniProject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Task {
    private String name;
    private String id;
    private int typeOfTask;
    private boolean statusOfTask;
    private LocalDate plannedStart;
    private LocalDate plannedFinish;
    private long plannedDuration;
    private LocalDate actualStart;
    private LocalDate actualFinish;
    private long actualDuration;
    private ArrayList<ManpowerAllocation> plannedManpower;
    private ArrayList<TeamMemberAllocation> actualTeamMembers;
    private ArrayList<Connectivity> connectivity;

    public Task (String name, String id, int typeOfTask){
        this.name = name;
        this.id = id;
        this.typeOfTask = typeOfTask;
        this.plannedManpower = new ArrayList<>();
        this.actualTeamMembers = new ArrayList<>();
        this.connectivity = new ArrayList<>();
        setStatusOfTask(false);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getTypeOfTask() { return typeOfTask; }

    public boolean getStatusOfTask() {
        return statusOfTask;
    }

    public ArrayList<ManpowerAllocation> getPlannedManpower() { return plannedManpower; }

    public ArrayList<TeamMemberAllocation> getActualTeamMembers() { return actualTeamMembers; }

    public long getPlannedDuration() {
        return plannedDuration;
    }

    public long getActualDuration() {
        return actualDuration;
    }

    public ArrayList<Connectivity> getConnectivity() {
        return connectivity;
    }

    public LocalDate getPlannedStart() {
        return this.plannedStart;
    }

    public LocalDate getPlannedFinish() {
        return this.plannedFinish;
    }

    public LocalDate getActualStart() {
        return this.actualStart;
    }

    public LocalDate getActualFinish() {
        return this.actualFinish;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTypeOfTask(int typeOfTask) {
        this.typeOfTask = typeOfTask;
    }

    public void setStatusOfTask(boolean statusOfTask) {
        this.statusOfTask = statusOfTask;
    }

    public void setPlannedDuration(long plannedDuration) {
        this.plannedDuration = plannedDuration;
    }

    public void setActualDuration(long actualDuration) {
        this.actualDuration = actualDuration;
    }

    public void setPlannedStart(LocalDate plannedStart) { this.plannedStart = plannedStart; }

    public void setPlannedFinish(LocalDate plannedFinish) {
        this.plannedFinish = plannedFinish;
    }

    public void setActualStart(LocalDate actualStart) {
        this.actualStart = actualStart;
    }

    public void setActualFinish(LocalDate actualFinish) {
        this.actualFinish = actualFinish;
    }

    @Override
    public String toString() {
        return "Task name : " + this.getName() + ", ID (" + this.getId() + "), Duration: " + this.getPlannedDuration() + " Days, Start Date: "
                + this.getPlannedStart() + ", Finish Date: " + this.getPlannedFinish();
    }
}