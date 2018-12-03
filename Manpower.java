package MiniProject;


import java.time.LocalDate;

public class Manpower {
    private double hourlySalaryRate;
    private double hoursWorked;
    private double salary;
    private LocalDate date;
    private String qualification;
    private Task task;

    public Manpower(String qualification, double hoursWorked, Task task, LocalDate date){
        this.date = date;
        this.qualification = qualification;
        this.hoursWorked = hoursWorked;
        this.task = task;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlySalaryRate() {
        return hourlySalaryRate;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getQualification() {
        return qualification;
    }

    public Task getTask() {
        return task;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setHourlySalaryRate(double hourlySalaryRate) {
        this.hourlySalaryRate = hourlySalaryRate;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}