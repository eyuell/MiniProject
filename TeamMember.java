package Git.MiniProject;

public class TeamMember extends Manpower {
    private String name;
    private String id;
    private double hourlyRate;

    public TeamMember(String name, String id, String qualification){
        super(qualification);
        this.name = name;
        this.id = id;
        setHourlyRate(255.0);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public double getHourlyRate() { return hourlyRate; }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
}