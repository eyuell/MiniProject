package Git.MiniProject;

public class RiskEvaluator{

    public String name (String name){
        name = name.toUpperCase();
        return name;
    }

    public double probability (double probability){
        double leastValue = 0.0;
        double maxValue = 1.0;
        while (probability < leastValue || probability > maxValue){
            System.out.println("Enter a value between 0.0 and 1.0 ");
            probability = new KeyboardInput().positiveDouble();
            probability = new RiskEvaluator().probability(probability);
        }
        return probability;
    }

    public double impact (double impact){
        double leastValue = 0.0;
        double maxValue = 10.0;
        while (impact < leastValue || impact > maxValue){
            System.out.println("Enter a value between 0.0 and 10.0 ");
            impact = new KeyboardInput().positiveDouble();
            impact = new RiskEvaluator().impact(impact);
        }
        return  impact;
    }
}
