
public class Finance {//Super
    private double budget;
    private double earnedValue;
double projectDuration;
double daysPassed;
String projectID;

    public Finance(double budget,double earnedValue,double projectDuration,double daysPassed,String projectID) {
        this.budget = budget;
        this.earnedValue = earnedValue;
        this.daysPassed = daysPassed;
    this.projectDuration=projectDuration;
    this.projectID=projectID;
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

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }
}
