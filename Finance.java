
public class Finance {//Super
    private double budget;
    private double earnedValue;
    private int projectId;
double projectDuration;
double daysPassed;


    public Finance(double budget,double earnedValue,double projectDuration,double daysPassed) {
        this.budget = budget;
        this.earnedValue = earnedValue;
        this.daysPassed = daysPassed;
    this.projectDuration=projectDuration;
    }

    public double getBudget() {
        return this.budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getEarnedValue() {
        return earnedValue;
    }

    public void setEarnedValue(double earnedValue) {
        this.earnedValue = earnedValue;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public double getDaysPassed() {
        return daysPassed;
    }

    public void setDaysPassed(double daysPassed) {
        this.daysPassed = daysPassed;
    }

    public double getProjectDuration() {
        return projectDuration;
    }

    public void setProjectDuration(double projectDuration) {
        this.projectDuration = projectDuration;
    }
}