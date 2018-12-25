
public class Finance {//Super
    private double budget;
    private double earnedValue;
    private String projectId;
    private double projectDuration;
    private double daysPassed;

    public Finance(double budget, double earnedValue, double projectDuration, double daysPassed, String projectId) {
        this.budget = budget;
        this.earnedValue = earnedValue;
        this.daysPassed = daysPassed;
        this.projectDuration = projectDuration;
        this.projectId = projectId;
    }

    public double getBudget() {
        return this.budget;
    }

    public double getEarnedValue() {
        return earnedValue;
    }

    public String getProjectId() {
        return projectId;
    }

    public double getDaysPassed() {
        return daysPassed;
    }

    public double getProjectDuration() {
        return projectDuration;
    }

    public void setEarnedValue(double earnedValue) {
        this.earnedValue = earnedValue;
    }
    
      public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setProjectDuration(double projectDuration) {
        this.projectDuration = projectDuration;
    }

    public void setDaysPassed(double daysPassed) {
        this.daysPassed = daysPassed;
    }
}
}
