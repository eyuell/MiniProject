package MiniProject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Task {
    private String name;
    private String id;
    private int typeOfTask;
    private boolean statusOfTask;
    private long plannedDuration;
    private double plannedEffectiveHours; //three hours per day
    private long actualDuration;
    private double price;
    private ArrayList<Double> plannedDailyHours, actualDailyHours;
    private ArrayList<Cost> plannedCosts, actualCosts;
    private ArrayList<Connectivity> connectivity;
    private LocalDate actualStart;
    private LocalDate actualFinish;
    private LocalDate plannedFinish;
    private LocalDate plannedStart;

    public Task (String name, String id, int typeOfTask){
        this.name = name;
        this.id = id;
        this.typeOfTask = typeOfTask;
        this.actualDailyHours = new ArrayList<>();
        this.plannedDailyHours = new ArrayList<>();
        this.plannedCosts = new ArrayList<>();
        this.actualCosts = new ArrayList<>();
        this.connectivity = new ArrayList<>();
        setStatusOfTask(false);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<Cost> getActualCosts() {
        return actualCosts;
    }

    public int getTypeOfTask() {
        return typeOfTask;
    }

    public boolean getStatusOfTask() {
        return statusOfTask;
    }

    public ArrayList<Cost> getPlannedCosts() {
        return plannedCosts;
    }

    public ArrayList<Double> getActualDailyHours() {
        return actualDailyHours;
    }

    public ArrayList<Double> getPlannedDailyHours() {
        return plannedDailyHours;
    }

    public long getPlannedDuration() {
        return plannedDuration;
    }

    public long getActualDuration() {
        return actualDuration;
    }


    public double getPlannedEffectiveHours() {
        return plannedEffectiveHours;
    }

    public ArrayList<Connectivity> getConnectivity() {
        return connectivity;
    }

    public void addConnectivity(Connectivity connectivity){
        this.connectivity.add(connectivity);
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

    public void setPlannedEffectiveHours(double plannedEffectiveHours) {
        this.plannedEffectiveHours = plannedEffectiveHours;
    }

    public void setActualCosts(ArrayList<Cost> actualCosts) {
        this.actualCosts = actualCosts;
    }

    public void setActualDailyHours(ArrayList<Double> actualDailyHours) {
        this.actualDailyHours = actualDailyHours;
    }

    public void setPlannedCosts(ArrayList<Cost> plannedCosts) {
        this.plannedCosts = plannedCosts;
    }

    public void setPlannedDailyHours(ArrayList<Double> plannedDailyHours) {
        this.plannedDailyHours = plannedDailyHours;
    }

    public void setPrice(double price) { this.price = price; }

    //public void setConnectivity(ArrayList<Connectivity> connectivity) {        this.connectivity = connectivity;    }

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