package MiniProject;
public class CostVariance extends Finance {
    private double actualCost;

    public CostVariance(double budget, double earnedValue, double projectDuration, double daysPassed, double actualCost, String projectId) {
        super(budget, earnedValue, projectDuration, daysPassed, projectId);
        this.actualCost = actualCost;
    }

    public double getCostVariance() {
        double costVariance;
        costVariance = getEarnedValue() - this.actualCost;
        return costVariance;
    }

    public double getActualCost() {
        return actualCost;
    }

    public void setActualCost(double actualCost) {
        this.actualCost = actualCost;
    }

    public String toString(){
        String costString;
        costString ="Project ID:  " +getProjectId();
        costString +="Days Passed since start: "+getDaysPassed()+" days";
        costString +="project Duration/Days: "+getProjectDuration()+" days.";
        costString +="*********************************************************************";
        costString +="Cost Variance($): "+ getCostVariance();
        costString +="*********************************************************************";
        return costString;}
}
