package MiniProject;

import javafx.scene.Scene;

public class ScheduleVariance extends Finance {
    private float scheduleVariance;
    private double daysPassed;
    private double projectDuration;
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m"; //"\033[42m" Green
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m"; // Yellow
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m"; // Red
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK

    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    public ScheduleVariance(double budget, double earnedValue, double projectDuration, double daysPassed, String projectId) {
        super(budget, earnedValue, projectDuration, daysPassed, projectId);
        this.daysPassed = daysPassed;
        this.projectDuration = projectDuration;
    }

    public float getScheduleVariance() {
        this.scheduleVariance = (float) ((getEarnedValue()) - (this.getPlannedValue()));
        if (scheduleVariance<0){
            System.out.println(ANSI_RED_BACKGROUND+BLACK_BOLD+"Project ID:  " + getProjectId());

        }else if(scheduleVariance>0){
            System.out.println(ANSI_GREEN_BACKGROUND+BLACK_BOLD+"Project ID:  " + getProjectId());

        }else if(scheduleVariance==0) {
            System.out.println(BLUE_BACKGROUND_BRIGHT + BLACK_BOLD + "Project ID:  " + getProjectId());
        }

        return scheduleVariance;
    }

    public double getPlannedValue() {
        double plannedValue;
        plannedValue = getBudget() * getPlannedPercentageCompleted();
        return plannedValue;
    }

    public double getPlannedPercentageCompleted() {
        double plannedPercentageCompleted;
        plannedPercentageCompleted = (daysPassed / projectDuration);
        return plannedPercentageCompleted;
    }


    public String toString() {
        String costString;
        costString ="   ========SCHEDUAL VARIANCE========="+System.lineSeparator();
        costString+="     ----------------------------"+System.lineSeparator();
        costString+="              "+getScheduleVariance()+"SEK"+System.lineSeparator();
        costString+="     ----------------------------"+System.lineSeparator();
        costString+=""+System.lineSeparator();
        costString+=BLACK_BOLD+"RED BACKGROUND = BEHIND, GREEN BACKGROUND = ON TRACK"+System.lineSeparator();
        return costString;
    }
}

