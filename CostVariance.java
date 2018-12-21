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
       costString ="ID: "+ projectID+"\n"+"Budget($):"+getBudget()+"\n"+"Earned Value($): "+getEarnedValue()+"\n"+"Cost Variance($):"+getCostVariance()+"\n"+"Days passed: "+getDaysPassed()+"\n"+"Project length/Days: "+getProjectDuration();
       costString +="\n*****************************************************************************************************************";
       return costString;}

}
