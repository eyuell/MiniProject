package Git.MiniProject;

import java.time.LocalDate;

public class TeamMemberAllocation {
    private TeamMember teamMember;
    private double workHours;
    private LocalDate date;
    TeamMemberAllocation(TeamMember teamMember, double workHours, LocalDate date){
        this.teamMember = teamMember;
        this.workHours = workHours;
        this.date = date;
    }

    public double getWorkHours() {
        return workHours;
    }

    public TeamMember getTeamMember() {
        return teamMember;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setTeamMember(TeamMember teamMember) {
        this.teamMember = teamMember;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}