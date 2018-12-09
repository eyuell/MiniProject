public class scheduleVariance extends Finance {
    float scheduleVariance;
    private double actualCost;

    public scheduleVariance(double budget, double earnedValue, double projectDuration,double daysPassed,String projectID) {
        super(budget, earnedValue, projectDuration, daysPassed,projectID);
    this.scheduleVariance= scheduleVariance;
    }

    public float getScheduleVariance(){
        this.scheduleVariance= (float) ((getEarnedValue())-(this.getPlannedValue()));
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
        costString +="\n "+projectID+"       " + getBudget() + "        " + getEarnedValue() + "                   " +getScheduleVariance() + "                      " + getDaysPassed()+"               "+projectDuration;
        costString +="\n*****************************************************************************************************************";
        return costString;}
}
