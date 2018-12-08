public class scheduleVariance extends Finance {
    float scheduleVariance;
    private double daysPassed;
    private double projectDuration;
    private double actualCost;
    public scheduleVariance(double budget, double earnedValue, double projectDuration,double daysPassed) {
        super(budget, earnedValue, projectDuration, daysPassed);
    this.daysPassed=daysPassed;
    this.projectDuration=projectDuration;
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
        costString ="      Budget($):      Earned Value($):       Schedule Variance($):       Days passed:      Project length/Days:    ";
        costString +="\n"+"      " + getBudget() + "        " + getEarnedValue() + "                   " +getScheduleVariance() + "                      " + getDaysPassed()+"               "+projectDuration;
        costString +="\n*******************************************************************************************************";
        return costString;}
}
