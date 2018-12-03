package MiniProject;


import java.time.LocalDate;

public class TeamMember extends Manpower {
    private String name;

    public TeamMember(String name, String qualification, double hoursWorked, Task task, LocalDate date){
        super(qualification, hoursWorked, task, date);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}