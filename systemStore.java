import java.util.ArrayList;
import java.util.List;

public class systemStore {
    private List<Finance> finances;

    public systemStore(){
        this.finances = new ArrayList<>();
    }

    public Finance registerScheduleVariance(double budget, double earnedValue, double projectDuration, double daysPassed, int projectId) {

        Finance newSV = new scheduleVariance(budget, earnedValue, projectDuration, daysPassed);
        finances.add(newSV);
        return newSV;
    }
    public Finance registerCostVariance(double budget, double earnedValue, double projectDuration, double daysPassed, double actualCost){
        Finance newCV=new costVariance(budget,earnedValue ,actualCost ,projectDuration ,daysPassed );
        finances.add(newCV);
        return newCV;
    }

    public int sizeOfArray() {
        return finances.size();
    }

    public String printAllFinances() {
        for (int i = 0; i < finances.size(); i++) {
            if (finances.get(i) != null)
                System.out.println(finances.get(i).toString());
        }
        return null;


    }

}