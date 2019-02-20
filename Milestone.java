package Git.MiniProject;

import java.time.LocalDate;

public class Milestone {
    private String name;
    private String id;
    private LocalDate date;

    public Milestone (String name, String id, LocalDate date){
        this.name = name;
        this.id = id;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  getName() + " (" + getId() + ") milestone date is on " + getDate();
    }
}