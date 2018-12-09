package MiniProject;

public class Risk {
    private String riskName;
    private double probability;
    private  double impact;

    public Risk(String riskName, double probability, double impact){
        this.riskName = riskName;
        this.probability = probability;
        this.impact = impact;
    }

    public double calculateRisk (){ return this.probability * this.impact; }

    public String getRiskName() {
        return riskName;
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getImpact() {
        return impact;
    }

    public void setImpact(double impact) {
        this.impact = impact;
    }

    @Override
    public String toString() {
        return "  " + riskName + "           " + probability + "         " + impact + "     " + calculateRisk();
    }
}