package PersonalCodeTraining.ProjectManagement;

import PersonalCodeTraining.InputHandler;

public class RiskEvaluator {

    public String name (String name){
        name = name.toUpperCase();
        return name;
    }

    public double probability (double probability){
        double leastValue = 0.0;
        double maxValue = 10.0;
        while (probability < leastValue || probability > maxValue){
            System.out.println("Enter a value between 1 and 10");
            probability = new InputHandler().Double();
            probability = new RiskEvaluator().probability(probability);
        }

        return probability;
    }

    public double impact (double impact){
        double leastValue = 0.0;
        double maxValue = 10.0;
        while (impact < leastValue || impact > maxValue){
            System.out.println("Enter a value between 1 and 10.");
            impact = new InputHandler().Double();
            impact = new RiskEvaluator().impact(impact);
        }
        return  impact;
    }
}
