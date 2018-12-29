package MiniProject;

import java.util.ArrayList;

public class RiskMatrix {
    // Background
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m"; //"\033[42m" Green
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m"; // Yellow
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m"; // Red

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK

    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    public static final int REGISTER_RISK = 1;
    public static final int PRINT_RISKS = 2;
    public static final int EDIT_RISKS = 3;
    public static final int CONVERT_PROBABILITY_AND_IMPACT_TO_PERCENTAGE = 4;
    public static final int QUIT = 5;

    private Project currentProject;

    public RiskMatrix(Project currentProject){
        this.currentProject = currentProject;
    }

    public Project getCurrentProject() {
        return currentProject;
    }

    public void printMenu (){
        System.out.println("SELECT FROM THE FOLLOWING OPTIONS");
        System.out.println();
        System.out.println("1. REGISTER PROJECT RISK.");
        System.out.println("2. RISK PRINT FUNCTION.");
        System.out.println("3. RISK EDIT FUNCTION.");
        System.out.println("4. CONVERT PROBABILITY AND IMPACT TO PERCENTAGE VALUE.");
        System.out.println("5. QUIT.");
        System.out.println();
    }

    public void runRisk(){ // this methods handles all the risk matrix functions.

        int option;
        int loopCounter = 0;

        do {
            loopCounter++;
            if (loopCounter == 1){
                System.out.println("========= RISK MATRIX =========");
                System.out.println();
            }

            printMenu();
            System.out.println("Enter an option from the above:");
            option = new KeyboardInput().positiveInt();

            switch (option){
                case REGISTER_RISK:
                    registerRisk();
                    System.out.println("**************************************");
                    break;

                case PRINT_RISKS:
                    printFunction();
                    System.out.println("**************************************");
                    break;

                case EDIT_RISKS:
                    editRisk();
                    System.out.println("**************************************");

                case CONVERT_PROBABILITY_AND_IMPACT_TO_PERCENTAGE:
                    percentageConverter();
                    System.out.println("**************************************");
                    break;

                case QUIT:
                    System.out.println("**************************************");
                    break;

                default:
                    System.out.println("Enter the right value");
                    break;
            }
        } while (option != QUIT);
    }

    public void registerRisk ( ){ //  this method handles risk registration

        if(currentProject != null){
            String riskID = readRiskID();

            String riskName = readRiskName();

            System.out.println("Risk Probability (b/n 0.0 and 1.0) :");
            double probability = new KeyboardInput().positiveDouble();
            probability = new RiskEvaluator().probability(probability);

            System.out.println("Risk Impact (b/n 0.0 and 10.0):");
            double impact = new KeyboardInput().positiveDouble();
            impact = new RiskEvaluator().impact(impact);

            currentProject.getRisks().add(new Risk(riskID, riskName, probability, impact));
        }
    }

    public void printFunction (){
        int option;
        final int PRINT_RISK_MATRIX = 1;
        final int PRINT_A_SPECIFIC_RISK = 2;
        final int PRINT_ALL_REGISTERED_RISKS = 3;
        final int PRINT_NUMBER_OF_REGISTERED_RISKS = 4;
        final int QUIT_PRINT_FUNCTION = 5;
        if(currentProject != null){
            ArrayList<Risk> risks = currentProject.getRisks();
            if(risks != null){
                do {
                    System.out.println("SELECT FROM THE FOLLOWING OPTIONS.");
                    System.out.println();
                    System.out.println("1. PRINT RISK MATRIX ");
                    System.out.println("2. PRINT A SPECIFIC RISK ");
                    System.out.println("3. PRINT ALL REGISTERED RISKS ");
                    System.out.println("4. DISPLAY NUMBER OF REGISTERED RISKS ");
                    System.out.println("5. QUIT PRINT FUNCTION ");
                    System.out.println();
                    option = new KeyboardInput().positiveInt();

                    switch (option){

                        case PRINT_RISK_MATRIX:
                            printRiskMatrix();
                            System.out.println("*************************************************************");
                            break;

                        case PRINT_A_SPECIFIC_RISK:
                            printSpecificRisk();
                            System.out.println("*************************************************************");
                            break;

                        case PRINT_ALL_REGISTERED_RISKS:
                            printAllRisks();
                            System.out.println("*************************************************************");
                            break;

                        case PRINT_NUMBER_OF_REGISTERED_RISKS:
                            printNumberOfRisk();
                            System.out.println("*************************************************************");
                            break;

                        case QUIT_PRINT_FUNCTION:
                            System.out.println("*************************************************************");
                            break;

                        default:
                            System.out.println("Enter a correct option.");
                            break;
                    }
                    new ProjectManagementTool().pause();
                } while (option!=QUIT_PRINT_FUNCTION);
            } else {
                System.out.println("There is no registered risk.");
            }
        } else {
            System.out.println("There is no registered project.");
        }
    }

    public void editRisk (){
        final int NAME = 1;
        final int PROBABILITY = 2;
        final int IMPACT = 3;
        final int REMOVE = 4;
        final int QUIT_EDIT = 5;
        int option;

        if(currentProject != null){
            ArrayList<Risk> risks = currentProject.getRisks();
            if(risks != null){
                do {
                    System.out.println("What would you like to edit?");
                    System.out.println();
                    System.out.println("1. Edit risk name.");
                    System.out.println("2. Edit risk probability.");
                    System.out.println("3. Edit risk impact.");
                    System.out.println("4. Delete risk..");
                    System.out.println("5. Quit edit function");
                    option = new KeyboardInput().positiveInt();

                    switch (option){
                        case NAME:
                            boolean repeat;
                            do{
                                repeat = false;
                                String riskID = checkRiskID();
                                Risk foundRisk = retrieveRiskByID(riskID, currentProject.getRisks());
                                if (foundRisk != null) {
                                    System.out.println("Enter new name:");
                                    String newName = new KeyboardInput().Line();
                                    newName = new RiskEvaluator().name(newName);
                                    foundRisk.setRiskName(newName);
                                    System.out.println("You have successfully updated the risk's name to " + foundRisk.getRiskName());
                                } else {
                                    System.out.println("The risk name your are trying to access is not registered");
                                    repeat = true;
                                }
                            } while (repeat);
                            break;

                        case PROBABILITY://
                            do{
                                repeat = false;
                                String riskID = checkRiskID();
                                Risk foundRisk = retrieveRiskByID(riskID, currentProject.getRisks());
                                if (foundRisk != null ){
                                    System.out.println("Enter the new probability value:");
                                    double newProbability = new KeyboardInput().positiveDouble();
                                    newProbability = new RiskEvaluator().probability(newProbability);
                                    foundRisk.setProbability(newProbability);
                                    System.out.println("You have successfully updated the risk's probability to " + foundRisk.getProbability());
                                } else {
                                    System.out.println("The risk name you are trying to access is not registered.");
                                    repeat = true;
                                }
                            } while (repeat);
                            break;

                        case IMPACT:
                            do{
                                repeat = false;
                                String riskID = checkRiskID();
                                Risk foundRisk = retrieveRiskByID(riskID, currentProject.getRisks());
                                if(foundRisk != null){
                                    System.out.println("Enter the new impact value :");
                                    double newImpact = new KeyboardInput().positiveDouble();
                                    newImpact = new RiskEvaluator().impact(newImpact);
                                    foundRisk.setImpact(newImpact);
                                    System.out.println("You have successfully updated the risk's impact to " + foundRisk.getImpact());
                                } else {
                                    System.out.println("The risk name you are trying to access is not registered.");
                                    repeat = true;
                                }
                            } while (repeat);
                            break;

                        case REMOVE:
                            do{
                                repeat = false;
                                String riskID = checkRiskID();
                                Risk foundRisk = retrieveRiskByID(riskID, currentProject.getRisks());
                                if ( foundRisk != null){
                                    System.out.println("Are you sure you want to delete " + foundRisk.getRiskName() + "?" );
                                    deleteRisk(foundRisk, currentProject);
                                } else {
                                    System.out.println("The risk name you trying to access is not registered.");
                                    repeat = true;
                                }
                            }  while (repeat);
                            break;

                        case QUIT:
                            System.out.println("*********************************************************");
                            break;

                        default:
                            System.out.println("Enter the right option");
                            break;
                    }
                    new ProjectManagementTool().pause();
                } while (option != QUIT_EDIT);
            } else {
                System.out.println("There is no registered risk.");
            }
        } else {
            System.out.println("There is no registered project.");
        }
    }

    public void deleteRisk(Risk foundRisk, Project project){ // this methods is handles the risk deletion functions
        if(project != null){
            final int YES = 1;
            int option;
            System.out.println("Select "+"1 "+"to delete risk.");
            System.out.println("Select any other number  to abort.");
            option = new KeyboardInput().Int();
            if (option == YES){
                project.getRisks().remove(foundRisk);
                System.out.println("Risk deleted!.");

            } else {
                System.out.println("Process aborted.");
            }
        }
    }

    public Risk retrieveRegisteredRisk(String riskName, Project project){
        if(project != null){
            ArrayList<Risk> risks = project.getRisks();
            if ( risks != null){
                for(Risk risk : risks){
                    if (risk.getRiskName().equals(riskName)){
                        return risk;
                    }
                }
            }
        }
        return null;
    }

    public String readRiskID (){// for new risk ID
        String riskID = "";
        if(currentProject != null){
            System.out.println("Enter new risk ID");
            riskID = new KeyboardInput().Line();
            while (retrieveRiskByID(riskID, currentProject.getRisks()) != null){
                System.out.print("ID is already registered by other risk. Enter another risk ID");
                riskID = new KeyboardInput().Line();
            }
        }
        return riskID;
    }

    public String checkRiskID (){ // for existing risk ID
        String riskID = "";
        if(currentProject != null){
            ArrayList<Risk> risks = currentProject.getRisks();
            if(risks != null){
                System.out.println("=================================");
                System.out.println("    List of Risks (ID and Name) ");
                for (Risk risk: risks) {
                    System.out.println(risk.getRiskID() + " :- " + risk.getRiskName());
                }
                System.out.println();
                System.out.println("Form the above list of Risks, enter risk ID ");
                riskID = new KeyboardInput().Line();
                while (retrieveRiskByID(riskID, risks) == null){
                    System.out.print("The provided ID does not exist. Enter risk ID correctly");
                    riskID = new KeyboardInput().Line();
                }
            } else {
                System.out.println("There are no registered risks ");
            }
        }
        return riskID;
    }

    public  String  readRiskName (){ // this methods handles all name input requests.
        System.out.println("Enter Risk Name:");
        String name = new KeyboardInput().Line();
        name = new RiskEvaluator().name(name);

        while (retrieveRegisteredRisk(name, currentProject) != null) {
            System.out.println();
            System.out.println("There exists a risk with the same name use, another name. ");
            System.out.println();
            System.out.println("Enter new Risk Name.");
            name = new KeyboardInput().Line();
            name = new RiskEvaluator().name(name);
        }
        return name;
    }

    public String readRisk (){ // for Existing risk names
        System.out.println("Enter Risk Name :");
        String name = new KeyboardInput().Line();
        name = new RiskEvaluator().name(name);
        return name ;
    }

    public Risk retrieveRiskByID(String riskID, ArrayList<Risk> risks){
        for(int i = 0; i < risks.size(); i++){
            Risk currentRisk = risks.get(i);
            if(currentRisk.getRiskID().equals(riskID)){
                return currentRisk;
            }
        }
        return null;
    }

    public void printRiskMatrix(){
        ArrayList<Risk> risks = currentProject.getRisks();
        if(risks != null){
            System.out.println("        IMPACT");
            System.out.println("          ʌ");
            System.out.println("          |");
            System.out.print("          |");
            for(int i = 25; i >= 0 ;i--){
                System.out.println();
                if(i == 23){
                    System.out.print("Extensive |");
                } else if (i == 18){
                    System.out.print("   Major  |");
                } else if (i == 13){
                    System.out.print("  Medium  |");
                } else if (i == 8) {
                    System.out.print("   Minor  |");
                } else if (i == 3){
                    System.out.print("No Impact |");
                } else {
                    System.out.print("          |");
                }

                for(int j = 0; j < 60 ;j++){
                    if(i > 20){
                        System.out.print(ANSI_RED_BACKGROUND);
                        printMatrixField(i, j);
                        System.out.print(RESET);
                    } else if(i > 15 && j < 37){
                        System.out.print(ANSI_YELLOW_BACKGROUND);
                        printMatrixField(i, j);
                        System.out.print(RESET);
                    } else if(i > 15 && j > 36){
                        System.out.print(ANSI_RED_BACKGROUND);
                        printMatrixField(i, j);
                        System.out.print(RESET);
                    } else if(i > 10 && j < 13){
                        System.out.print(ANSI_GREEN_BACKGROUND);
                        printMatrixField(i, j);
                        System.out.print(RESET);
                    } else if(i > 10 && j < 49){
                        System.out.print(ANSI_YELLOW_BACKGROUND);
                        printMatrixField(i, j);
                        System.out.print(RESET);
                    } else if(i > 10 && j > 48){
                        System.out.print(ANSI_RED_BACKGROUND);
                        printMatrixField(i, j);
                        System.out.print(RESET);
                    } else if(i > 5 && j < 25){
                        System.out.print(ANSI_GREEN_BACKGROUND);
                        printMatrixField(i, j);
                        System.out.print(RESET);
                    } else if(i > 5 && j > 24){
                        System.out.print(ANSI_YELLOW_BACKGROUND);
                        printMatrixField(i, j);
                        System.out.print(RESET);
                    } else if (i <= 5){
                        System.out.print(ANSI_GREEN_BACKGROUND);
                        printMatrixField(i, j);
                        System.out.print(RESET);
                    }
                }
            }
            System.out.println();
            System.out.println("           -----------.-----------.-----------.-----------.-----------.----->");
            System.out.println("              Highly     Unlikely   Possible      Likely      Very            PROBABILITY");
            System.out.println("             Unlikely                                        Likely ");
            System.out.println();
            System.out.println();
            System.out.println("                             LEGEND:                ");
            System.out.println("                                " + ANSI_GREEN_BACKGROUND + "        " + RESET + " Low Severity (Acceptable)");
            System.out.println("                                " + ANSI_YELLOW_BACKGROUND + "        " + RESET + " Medium Severity ");
            System.out.println("                                " + ANSI_RED_BACKGROUND + "        " + RESET + " High Severity (Not Acceptable)");
            System.out.println();
            System.out.println("                                        * single Risk plot ");
            System.out.println("                                        ℗ more than one Risk plot ");
        }

    }

    public void printMatrixField(int i, int j){
        int impact, prob;
        int counter = 0;
        ArrayList<Risk> risks = currentProject.getRisks();
        for (Risk risk:risks) {
            impact = (int) Math.round(risk.getImpact()/10.0 * 25.0);
            if (impact == i){
                prob = (int) Math.round(risk.getProbability() * 60.0);
                if (prob == j){
                    counter = counter + 1;
                }
            }
        }

        if(counter == 0){
            System.out.print(" ");
        } else if (counter == 1){
            System.out.print(BLACK_BOLD + "*");
        } else {
            System.out.print(BLACK_BOLD + "℗");
        }
    }

    public void printSpecificRisk (){
        String riskID = checkRiskID();
        Risk foundRisk = retrieveRiskByID(riskID, currentProject.getRisks());
        int indent = foundRisk.getRiskName().length() + 3 ;
        if(indent < 12){
            indent = 12;
        }
        int before = (indent - 12)/4;
        int after = indent - 12 - before;
        printEmpty(after);
        System.out.print("NAME OF RISK");
        printEmpty(4);
        System.out.print("PROBABILITY");
        printEmpty(4);
        System.out.print("IMPACT");
        printEmpty(4);
        System.out.print("RISK");
        System.out.println();

        System.out.print(foundRisk.getRiskName());
        int afterName = indent - foundRisk.getRiskName().length();
        printEmpty(afterName);
        printEmpty(6);
        System.out.print(foundRisk.getProbability());
        printEmpty(8);
        printEmpty(2);
        System.out.print(foundRisk.getImpact());
        printEmpty(6);
        System.out.print(foundRisk.getCalculatedRisk());
        System.out.println();
    }

    public void printEmpty(int spaces){
        for(int i = 0; i<spaces; i++){
            System.out.print(" ");
        }
    }

    public void printAllRisks(){

        ArrayList<Risk> risks = currentProject.getRisks();
        if(risks != null){
            int indent = risks.get(0).getRiskName().length();
            for (Risk currentRisk : risks){
                if   ( currentRisk.getRiskName().length() > indent) {
                    indent = currentRisk.getRiskName().length();
                }
            }

            indent = indent + 3 ;
            if(indent < 12){
                indent = 12;
            }
            int before = (indent - 12)/3;
            int after = indent - 12 - before;
            printEmpty(before + 2);
            System.out.print("NAME OF RISK");
            printEmpty(after);
            System.out.print("PROBABILITY");
            printEmpty(4);
            System.out.print("IMPACT");
            printEmpty(4);
            System.out.print("RISK");
            System.out.println();

            for ( Risk foundRisk : risks) {
                System.out.print(foundRisk.getRiskName());
                int afterName = indent - foundRisk.getRiskName().length();
                printEmpty(afterName);
                printEmpty(6);
                System.out.print(foundRisk.getProbability());
                printEmpty(8);
                printEmpty(2);
                System.out.print(foundRisk.getImpact());
                printEmpty(6);
                System.out.print(foundRisk.getCalculatedRisk());
                System.out.println();
            }
        } else {
            System.out.println("There is no registered risk.");
        }
    }

    public void printNumberOfRisk(){
        int numberOfRisks;
        if(currentProject != null){
            ArrayList<Risk> risks = currentProject.getRisks();
            if (risks!=null){
                numberOfRisks = risks.size();
                if (numberOfRisks == 1){
                    System.out.println("There is " + risks.size() + " registered risk.");
                } else {
                    System.out.println("There are " + risks.size() + " registered risks.");
                }
            } else {
                System.out.println("There is no registered risk.");
            }
        }
    }

    public void percentageConverter(){ // this methods handles the converting to percentage function.
        final int PROBABILITY = 1;
        final int IMPACT = 2;
        final int QUIT = 3;
        int option ;
        if(currentProject != null){
            ArrayList<Risk> risks = currentProject.getRisks();
            if(risks != null){
                do {
                    System.out.println("1. CONVERT PROBABILITY");
                    System.out.println("2. CONVERT IMPACT.");
                    System.out.println("3. QUIT");
                    option = new KeyboardInput().positiveInt();

                    switch (option) {
                        case PROBABILITY:
                            probabilityPercentage();
                            System.out.println("*************************************");
                            break;

                        case IMPACT:
                            impactPercentage();
                            System.out.println("*************************************");
                            break;

                        case QUIT:
                            System.out.println("*************************************");
                            break;

                        default:
                            System.out.println("Enter a valid input");
                            break;
                    }
                } while (option != QUIT);
            }else {
                System.out.println("There are no registered risks ");
            }
        }
    }

    public void probabilityPercentage(){ // this method coverts probability value of a given risk to percentage value.

        final double fixedProbability = 10.0;
        double maxPercentage = 100.0;
        if(currentProject != null){
            String riskID = checkRiskID();
            Risk foundRisk = retrieveRiskByID(riskID, currentProject.getRisks());

            if(foundRisk != null){
                double foundProbability = foundRisk.getProbability();
                double convertedProbability = (foundProbability/fixedProbability) * maxPercentage;
                System.out.println("The risk " + foundRisk.getRiskName() + " has a probability of " + convertedProbability
                        + " %");
            } else{
                System.out.println("The risk name does not exist.");
            }
        }
    }

    public void impactPercentage(){ // this method converts impact values of a given risk to percentage values.
        final double fixedImpact = 10.0;
        double maxPercentage = 100.0;
        if(currentProject != null){
            String riskID = checkRiskID();
            Risk foundRisk = retrieveRiskByID(riskID, currentProject.getRisks());
            if (foundRisk != null){
                double foundImpact = foundRisk.getImpact();
                double convertedImpact = (foundImpact/fixedImpact) * maxPercentage;
                System.out.println("The risk "+ foundRisk.getRiskName() + " has an impact of "+ convertedImpact + " %");
            } else {
                System.out.println("The risk name does not exist");
            }
        }
    }

}
