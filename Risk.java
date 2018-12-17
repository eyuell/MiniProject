package MiniProject;

public class Risk {
    private String riskID;
    private String riskName;
    private double probability;
    private  double impact;

    public Risk(String riskID, String riskName, double probability, double impact){
        this.riskID = riskID;
        this.riskName = riskName.toUpperCase();
        this.probability = probability;
        this.impact = impact;
    }

    public String getRiskID() {
        return riskID;
    }

    public double getImpact() {
        return impact;
    }

    public double getProbability() {
        return probability;
    }

    public String getRiskName() {
        return riskName;
    }

    public void setRiskID(String riskID) {
        this.riskID = riskID;
    }

    public void setImpact(double impact) {
        this.impact = impact;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    public double getCalculatedRisk (){ return (Math.round(this.probability * this.impact *100.0)/100.0); }

    @Override
    public String toString() {
        return getRiskName() + " (" + getRiskID() + ")" + ": has a probability of " + getProbability() + " and an impact of " + getImpact();
    }
}