package MiniProject;

public class ScheduleVariance extends Finance {
    private float scheduleVariance;
    private double daysPassed;
    private double projectDuration;

    public ScheduleVariance(double budget, double earnedValue, double projectDuration, double daysPassed, String projectId) {
        super(budget, earnedValue, projectDuration, daysPassed, projectId);
        this.daysPassed = daysPassed;
        this.projectDuration = projectDuration;
    }

    public float getScheduleVariance(){
        this.scheduleVariance = (float) ((getEarnedValue())-(this.getPlannedValue()));
        return scheduleVariance;
    }

    public double getPlannedValue() {
        double plannedValue;
        plannedValue=getBudget() * getPlannedPercentageCompleted();
        return plannedValue;
    }

    public double getPlannedPercentageCompleted(){
        double plannedPercentageCompleted;
        plannedPercentageCompleted=(daysPassed/projectDuration);
        return plannedPercentageCompleted;
    }

    public String toString() {
        String costString;
        costString =" ID:     Budget($):      Earned Value($):       Schedule Variance($):       Days passed:      Project length/Days:    ";
        costString +="\n " + getProjectId() +"       " + getBudget() + "        " + getEarnedValue() + "                   " +getScheduleVariance() + "                      " + getDaysPassed()+"               "+projectDuration;
        costString +="\n*****************************************************************************************************************";
        return costString;}
}
