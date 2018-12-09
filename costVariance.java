public class costVariance extends Finance {
    private double actualCost;

    public costVariance(double budget, double earnedValue, double actualCost, double projectDuration, double daysPassed, String projectID) {
        super(budget, earnedValue, projectDuration, daysPassed, projectID);
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
        costString =" ID      Budget($):      Earned Value($):       Cost Variance($):           Days passed:          Project length/Days:    ";
        costString +="\n "+projectID+"       " + getBudget() + "        " + getEarnedValue() + "                   " +getCostVariance() + "                       " + getDaysPassed()+"               "+projectDuration;
        costString +="\n*****************************************************************************************************************";
        return costString;}
}




