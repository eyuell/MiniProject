package Git.MiniProject;

import java.time.LocalDate;

public class ManpowerAllocation {
    private Manpower manpower;
    private double workHours;
    private LocalDate date;

    ManpowerAllocation(Manpower manpower, double workHours, LocalDate date){
        this.manpower = manpower;
        this.workHours = workHours;
        this.date = date;
    }

    public double getWorkHours() { return workHours; }

    public Manpower getManpower() { return manpower; }

    public LocalDate getDate() { return date; }

    public void setManpower(Manpower manpower) { this.manpower = manpower; }

    public void setWorkHours(double workHours) { this.workHours = workHours; }

    public void setDate(LocalDate date) { this.date = date; }
}