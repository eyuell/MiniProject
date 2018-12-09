import java.util.ArrayList;
import java.util.List;

public class systemStore {
    private List<Finance> finances;

    public systemStore() {
        this.finances = new ArrayList<>();
    }

//Add new SV
    public Finance registerScheduleVariance(double budget, double earnedValue, double projectDuration, double daysPassed, String projectID) {
        Finance newSV = new scheduleVariance(budget, earnedValue, projectDuration, daysPassed, projectID);
        finances.add(newSV);
        return newSV;
    }

//Add new CV
    public Finance registerCostVariance(double budget, double earnedValue, double projectDuration, double daysPassed, double actualCost, String projectID) {
        Finance newCV = new costVariance(budget, earnedValue, actualCost, projectDuration, daysPassed, projectID);
        finances.add(newCV);
        return newCV;
    }

//Print a INT of how many Objects are stored in array
    public int sizeOfArray() {
        return finances.size();
    }

//Print all stored values for all projects.
    public String printAllFinances() {
        for (int i = 0; i < finances.size(); i++) {
            if (finances.get(i) != null)
                System.out.println(finances.get(i).toString());
        }
        return null;
    }
//Print all values for a specific project ID
    public Finance printAllFinancesByID(String projectID) {
        for (int i = 0; i < finances.size(); i++) {
            if (finances.get(i) != null && finances.get(i).getProjectID().equals(projectID)) {
                System.out.println(finances.get(i).toString());
            }
        }
        return null;
    }
}



