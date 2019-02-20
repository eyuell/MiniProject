package Git.MiniProject;

import java.util.ArrayList;

public class SystemStore {
    private ArrayList<Finance> finances;

    public SystemStore(){
        this.finances = new ArrayList<>();
    }

    //Add new SV
    public Finance registerScheduleVariance(double budget, double earnedValue, double projectDuration, double daysPassed, String projectId) {

        Finance newSV = new ScheduleVariance(budget, earnedValue, projectDuration, daysPassed, projectId);
        finances.add(newSV);
        return newSV;
    }

    //Add new CV
    public Finance registerCostVariance(double budget, double earnedValue, double projectDuration, double daysPassed, double actualCost, String projectId){
        Finance newCV=new CostVariance(budget,earnedValue ,actualCost ,projectDuration ,daysPassed, projectId);
        finances.add(newCV);
        return newCV;
    }

    //Print all stored values for all projects.
    public void printAllFinances() {
        for (int i = 0; i < finances.size(); i++) {
            if (finances.get(i) != null)
                System.out.println(finances.get(i).toString());
        }
    }

    //Print all values for a specific project ID
    public void printAllFinancesByID(String projectID) {
        for (int i = 0; i < finances.size(); i++) {
            if (finances.get(i) != null && finances.get(i).getProjectId().equals(projectID)) {
                System.out.println(finances.get(i).toString());
            }
        }
    }
}
