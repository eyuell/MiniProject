package MiniProject;

public class Risk {
    private String riskID;
    private String riskName;
    private String riskType;
    private String riskStatus;
    private double probability;
    private  double impact;

    public Risk(String riskID, String riskName, String riskType, double probability, double impact){
        this.riskID = riskID;
        this.riskName = riskName.toUpperCase();
        this.riskType = riskType;
        this.probability = probability;
        this.impact = impact;
        setRiskStatus("To be Addressed");
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

    public String getRiskType() { return riskType; }

    public String getRiskStatus() { return riskStatus; }

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

    public void setRiskType(String riskType) { this.riskType = riskType; }

    public void setRiskStatus(String riskStatus) { this.riskStatus = riskStatus; }

    public double getCalculatedRisk (){
        double DIGIT_LIMIT = 100.0;
        return (Math.round(this.probability * this.impact * DIGIT_LIMIT)/DIGIT_LIMIT);
    }

    @Override
    public String toString() {
        return getRiskName() + " (" + getRiskID() + ")" + ": has a probability of " + getProbability() + " and an impact of " + getImpact();
    }
}
