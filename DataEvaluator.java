package Git.MiniProject;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Scanner;

public class DataEvaluator {
    public final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    public String connectivityType(String connectivityType){

        String result = "";
        boolean repeatLoop;
        do{
            connectivityType = connectivityType.toUpperCase();
            repeatLoop = false;
            switch (connectivityType){
                case "SS":
                    result = "SS";
                    break;
                case "SF":
                    result = "SF";
                    break;
                case "FS":
                    result = "FS";
                    break;
                case "FF":
                    result = "FF";
                    break;
                default:
                    System.out.println();
                    System.out.print("Correct choice is not made. Enter one of the following (SS, SF, FS, FF) ");
                    connectivityType = new KeyboardInput().Line();
                    repeatLoop = true;
                    break;
            }
        }while (repeatLoop);
        return result;
    }

    public String yesOrNo(String choice){
        choice = choice.toUpperCase();
        String YES = "Y";
        String NO = "N";

        System.out.println();
        while (!choice.equals(YES) && !choice.equals(NO)){
            System.out.print("Correct choice is not made, Enter Y or N (for yes or no ");
            choice = new KeyboardInput().Line();
            choice = choice.toUpperCase();
        }
        return choice;
    }

    public String nameLength(String name){
        int limitOfName = 30;
        while (name.length() > limitOfName){
            System.out.println("activity name shall be limited to 30 characters including spaces ");
            System.out.println("Enter a shorter version of name for the activity ");
            name = new KeyboardInput().Line();
        }
        return name;
    }

    public LocalDate extractConnectivityDate (String startOrFinish, ArrayList<Connectivity> connectivity){
        int numberOfConnectivity = connectivity.size();
        ArrayList<LocalDate> starts = new ArrayList<>();
        ArrayList<LocalDate> finishes = new ArrayList<>();
        LocalDate startDate;
        LocalDate finishDate;
        LocalDate result = null;

        for (int i = 0; i < numberOfConnectivity; i++){
            Connectivity con = connectivity.get(i);
            String type = con.getStartOrFinish();

            if(type.equals("FS") && con.getLinkedTo().getPlannedFinish() != null){
                long days = con.getConnectivityDuration();
                LocalDate otherTaskFinish = con.getLinkedTo().getPlannedFinish();
                if( days > 0){
                    startDate = otherTaskFinish.plusDays(days);
                }else{
                    days = (-1) * days;
                    startDate = otherTaskFinish.minusDays(days);
                }
                starts.add(startDate);

            }else if(type.equals("SS") && con.getLinkedTo().getPlannedStart() != null){
                long days = con.getConnectivityDuration();
                LocalDate otherTaskStart = con.getLinkedTo().getPlannedStart();
                if( days > 0){
                    startDate = otherTaskStart.plusDays(days);
                }else{
                    days = (-1) * days;
                    startDate = otherTaskStart.minusDays(days);
                }
                starts.add(startDate);
            }else if(type.equals("SF") && con.getLinkedTo().getPlannedStart() != null){
                long days = con.getConnectivityDuration();
                LocalDate otherTaskStart = con.getLinkedTo().getPlannedStart();
                if( days > 0){
                    finishDate = otherTaskStart.plusDays(days);
                }else{
                    days = (-1) * days;
                    finishDate = otherTaskStart.minusDays(days);
                }
                finishes.add(finishDate);
            }else if(type.equals("FF") && con.getLinkedTo().getPlannedFinish() != null){
                long days = con.getConnectivityDuration();
                LocalDate otherTaskFinish = con.getLinkedTo().getPlannedFinish();
                if( days > 0){
                    finishDate = otherTaskFinish.plusDays(days);
                }else{
                    days = (-1) * days;
                    finishDate = otherTaskFinish.minusDays(days);
                }
                finishes.add(finishDate);
            }
        }

        if(startOrFinish.equals("start") && starts.size() > 0){
            int numberOfStarts = starts.size();
            Collections.sort(starts);
            result = starts.get((numberOfStarts - 1));
        }else if(startOrFinish.equals("finish") && finishes.size() > 0){
            Collections.sort(finishes);
            result = finishes.get((0));
        }
        return result;
    }

    public void resourceAllocation(Project project){
        ArrayList<Task> tasks = project.getTasks();
        for(int i = 0; i < tasks.size(); i++){
            Task currentTask = tasks.get(i);

        }
    }

    public LocalDate readDate(){
        boolean repeat;
        int year = 0;
        int month = 0;
        int day = 0;
        LocalDate today = LocalDate.now();
        do{
            repeat = false;
            System.out.print("(YYYY/MM/DD) : ");
            String rawDate = new KeyboardInput().Line();
            String yearString = rawDate.substring(0,4);
            String monthString = rawDate.substring(5,7);
            String dayString = rawDate.substring(8);
            String yearCopy = yearString;
            String monthCopy = monthString;
            String dayCopy = dayString;
            yearCopy = yearCopy.replaceAll("[0-9]","");
            monthCopy = monthCopy.replaceAll("[0-9]","");
            dayCopy = dayCopy.replaceAll("[0-9]","");

            if((yearCopy.length()+monthCopy.length()+dayCopy.length()) != 0){
                repeat = true;
            }else{
                year = Integer.parseInt(yearString);
                month = Integer.parseInt(monthString);
                day = Integer.parseInt(dayString);

                if(year > today.getYear()){
                    System.out.print("Year, ");
                    repeat = true;
                }else if (month > 12){
                    System.out.print("Month, ");
                    repeat = true;
                }else if(day > monthDays(year,month)){
                    System.out.print("Day ");
                }
            }
            if(repeat){
                System.out.print("Input needs correction. Enter Again");
            }
        }while (repeat);

        return LocalDate.of(year,month,day);
    }

    //evaluates a name
    public String name(String name){
        final int FIRST = 0;
        final int SECOND = 1;
        final int ONE = 1;
        name = name.toLowerCase();

        ////changing the first letter to upper case
        name = name.substring(FIRST,SECOND).toUpperCase() + name.substring(SECOND);

        if (name.contains(" ")){ //if the name have a space
            int space = name.indexOf(" "); //index of the space
            String name1 = name.substring(FIRST,space); //first name (capitalized earlier)
            String name2 = name.substring(space + ONE); //second name

            //second name first letter capitalized
            name2 = name2.substring(FIRST,SECOND).toUpperCase() + name2.substring(SECOND);

            name = name1 + " " + name2; //concatenation
        }
        return name;
    }

    public int year(int year){
        final int NOT_ACCEPTABLE_YEAR = 0;
        final int MIN_AGE = 0;
        final int MAX_AGE = 100;

        while((CURRENT_YEAR - year) < MIN_AGE ){
            System.out.println("Year cannot be less that current year");
            System.out.println("Enter year again ");
            year = new KeyboardInput().Int();
        }

        return year;
    }

    //evaluates the month in SSN input
    public int month(int month){
        final int NOT_ACCEPTABLE_MONTH = 0;
        final int MIN = 0;
        final int MAX = 12;

        if(month < MIN || month > MAX) {
            return NOT_ACCEPTABLE_MONTH;
        }
        return month;
    }

    public int date (int date){
        final int NOT_ACCEPTABLE_DATE = 0;
        final int MIN = 0;
        final int MAX = 31;

        if(date < MIN || date > MAX) {
            return NOT_ACCEPTABLE_DATE;
        }
        return date;
    }

    //evaluates the number of days in a month
    public int monthDays(int year, int month){
        final int NOT_ACCEPTABLE_YEAR_MONTH = 0;
        final int MIN = 0;
        final int MAX = 12;

        if(year < MIN || month > MAX || month < MIN) {
            return NOT_ACCEPTABLE_YEAR_MONTH;
        }//this may not happen as our program make sure that the data is right

        //java method to find the information of particular month of a particular year
        YearMonth yearMonth = YearMonth.of(year,month);

        //java method to find the number of days in a month of particular year
        return yearMonth.lengthOfMonth();
    }

    //Double inputs collection
    public double positiveDouble(String typedKey){
        double evaluatedKey = 0.0;
        final int YES = 0;
        final int ZERO = 0;
        int repeat = 0;

        Scanner newKeyTyped = new Scanner(System.in);

        while (repeat == YES ){
            int initialLength = typedKey.length();
            if(typedKey.contains("-") && typedKey.indexOf("-") == ZERO){  //if the string is negative
                System.out.println("A positive value is needed");
            }
            // every character except numbers will be replaced by empty string
            String shortedText = typedKey.replaceAll("[^0-9.]", "");
            int finalLength = shortedText.length();

            // if the input is only of numbers and if input is not empty string
            if ((initialLength == finalLength) && !typedKey.equals("")){
                evaluatedKey = Double.parseDouble(typedKey); // the input string is converted to Double
                repeat++;

            } else { //otherwise the input is not a valid number format and another try will be made
                System.out.print("Please enter the required type of input. ");
                typedKey= newKeyTyped.nextLine();
                repeat = YES;
            }
        }
        return evaluatedKey;
    }

    public double roundDouble(double number, int digitAfterDecimal){
        double multiplier = 1.0;
        double STANDARD = 10.0;

        while (digitAfterDecimal < 0){
            System.out.println("Digit after decimal should not be negative.");
            System.out.println("Enter a positive number for the digits after the decimal");
            digitAfterDecimal = new KeyboardInput().positiveInt();
        }

        for(int i = 0; i < digitAfterDecimal; i++){
            multiplier = STANDARD * multiplier;
        }

        return Math.round(number * multiplier) / multiplier;
    }
}