package PersonalCodeTraining.ProjectManagement;

import PersonalCodeTraining.InputHandler;

import java.util.ArrayList;
import java.util.List;

public class RiskMatrix {
    public static final int REGISTER_RISK = 1;
    public static final int PRINT_RISKS = 2;
    public static final int EDIT_RISKS = 3;
    public static final int CONVERT_PROBABILITY_AND_IMPACT_TO_PERCENTAGE = 4;
    public static final int QUIT = 5;

    private List<Risk> risks;

    public RiskMatrix(){
        this.risks = new ArrayList<>();
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

    public void run(){ // this methods handles all the risk matrix functions.
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
            option = new InputHandler().Int();

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

    public void registerRisk (){ //  this method handles risk registration

        String riskName = readRiskName();


            System.out.println("Risk Probability:");
            double probability = new InputHandler().Double();
            probability = new RiskEvaluator().probability(probability);

            System.out.println("Risk Impact:");
            double impact = new InputHandler().Double();
            impact = new RiskEvaluator().impact(impact);

            risks.add(new Risk(riskName, probability, impact));


    }

    public void printFunction (){
        int option;
        final int PRINT_A_SPECIFIC_RISK = 1;
        final int PRINT_ALL_REGISTERED_RISKS = 2;
        final int PRINT_NUMBER_OF_REGISTERED_RISKS = 3;
        final int QUIT_PRINT_FUNCTION = 4;

        do {

            System.out.println("SELECT FROM THE FOLLOWING OPTIONS.");
            System.out.println();
            System.out.println("1. PRINT A SPECIFIC RISK.");
            System.out.println("2. PRINT ALL REGISTERED RISKS.");
            System.out.println("3. PRINT NUMBER OF REGISTERED RISKS.");
            System.out.println("4. QUIT PRINT FUNCTION.");
            System.out.println();
            option = new InputHandler().Int();

            switch (option){
                case PRINT_A_SPECIFIC_RISK:
                    printSpecificRisk();
                    System.out.println("**************************************");
                    break;

                case PRINT_ALL_REGISTERED_RISKS:
                    printAllRisks();
                    System.out.println("**************************************");
                    break;

                case PRINT_NUMBER_OF_REGISTERED_RISKS:
                    printNumberOfRisk();
                    System.out.println("**************************************");
                    break;

                case QUIT_PRINT_FUNCTION:
                    System.out.println("**************************************");
                    break;

                    default:
                        System.out.println("Enter a correct option.");
                        break;
            }
        } while (option!=QUIT_PRINT_FUNCTION);

    }

    public void editRisk (){
        final int NAME = 1;
        final int PROBABILITY = 2;
        final int IMPACT = 3;
        final int REMOVE = 4;
        final int QUIT_EDIT = 5;
        int option;

        do {

            System.out.println("What would you like to edit?");
            System.out.println();
            System.out.println("1. Edit risk name.");
            System.out.println("2. Edit risk probability.");
            System.out.println("3. Edit risk impact.");
            System.out.println("4. Delete risk..");
            System.out.println("5. Quit edit function");
            option = new InputHandler().Int();

            switch (option){
                case NAME:
                    boolean repeat;
                    do{
                        repeat = false;
                        String name = readRisk();
                        Risk foundRisk = findRisk(name);
                            if (foundRisk.getRiskName().equals(name)) {
                                System.out.println("Enter new name:");
                                String newName = new InputHandler().Line();
                                newName = new RiskEvaluator().name(newName);
                                foundRisk.setRiskName(newName);
                                System.out.println("You have successfully updated the risk's name to " + foundRisk.getRiskName());
                            }

                        else {
                            System.out.println("The risk name your are trying to access is not registered");
                            repeat = true;
                        }

                    } while (repeat);
                    break;

                case PROBABILITY://
                    do{
                        repeat = false;
                        String name = readRisk();
                        Risk foundRisk = findRisk(name);
                        if (foundRisk.getRiskName().equals(name)){
                            System.out.println("Enter the new probability value:");
                            double newProbability = new InputHandler().Double();
                            newProbability = new RiskEvaluator().probability(newProbability);
                            foundRisk.setProbability(newProbability);
                            System.out.println("You have successfully updated the risk's probability to " + foundRisk.getProbability());

                        }
                        else {
                            System.out.println("The risk name you are trying to access is not registered.");
                            repeat = true;
                        }
                    } while (repeat);
                    break;

                case IMPACT:
                    do{
                        repeat = false;
                        String name = readRisk();
                        Risk foundRisk = findRisk(name);
                        if(foundRisk.getRiskName().equals(name)){
                            System.out.println("Enter the new impact value :");
                            double newImpact = new InputHandler().Double();
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
                        String name = readRisk();
                        Risk foundRisk = findRisk(name);
                        if ( foundRisk.getRiskName().equals(name)){
                            System.out.println("Are you sure you want to delete " + foundRisk.getRiskName() + "?" );
                            deleteRisk(foundRisk);
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


        } while (option != QUIT_EDIT);

    }
    public void deleteRisk(Risk foundRisk){ // this methods is handles the risk deletion functions
        final int YES = 1;
        int option;
            System.out.println("Select "+"1 "+"to delete risk.");
            System.out.println("Select any other number  to abort.");
            option = new InputHandler().Int();
            if (option == YES){
                risks.remove(foundRisk);
                System.out.println("Risk deleted!.");

            } else {
                System.out.println("Process aborted.");
            }


    }

    public Risk retrieveRegisteredRisk(String riskName){
        if (risks !=null){
            for(Risk risk : risks){
                if (risk.getRiskName().equals(riskName)){
                    return risk;
                }
            }
        }
        return null;
    }
    public  String  readRiskName (){ // this methods handles all name input requests.
        System.out.println("Enter Risk Name:");
        String name = new InputHandler().Line();
        name = new RiskEvaluator().name(name);

        while (retrieveRegisteredRisk(name) != null) {
            System.out.println();
            System.out.println("There exists a risk with the same name use, another name. ");
            System.out.println();
            System.out.println("Enter new Risk Name.");
            name = new InputHandler().Line();
            name = new RiskEvaluator().name(name);
        }
        return name;

    }
    public String readRisk (){
        System.out.println("Enter Risk Name :");
        String name = new InputHandler().Line();
        name = new RiskEvaluator().name(name);
        return name ;
    }

    public void printSpecificRisk (){
        String name = readRisk();
        name = new RiskEvaluator().name(name);
        Risk foundRisk = retrieveRegisteredRisk(name);
        System.out.println("NAME OF RISK"+"        "+"PROBABILITY"+"   "+"IMPACT"+"   "+"RISK");
        if(foundRisk != null){
            System.out.println(foundRisk);
        } else {
            System.out.println("There is no registered risk in that name.");
        }
    }
    public void printAllRisks (){
        int i = 0;
        int empty = 0;
        System.out.println("NAME OF RISK"+"       "+"PROBABILITY"+"   "+"IMPACT"+"   "+"RISK");
        if(risks.size()!=empty){
            for (Risk risk : risks){
                i++;
                System.out.println(i+ ":-"+ risk);
            }
        } else {
            System.out.println("There is no registered risk.");
        }
    }
    public void printNumberOfRisk(){
        int numberOfRisks;
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

    public void percentageConverter(){ // this methods handles the converting to percentage function.
     final int PROBABILITY = 1;
     final int IMPACT = 2;
     final int QUIT = 3;
     int option ;
     do {
         System.out.println("1. CONVERT PROBABILITY");
         System.out.println("2. CONVERT IMPACT.");
         System.out.println("3. QUIT");
         option = new InputHandler().Int();
         switch (option) {
             case PROBABILITY:
                 probabilityPercentage();
                 System.out.println("*************************************");
                 break;

             case IMPACT:
                 impactPercentage();
                 System.out.println("****************************************");
                 break;

             case QUIT:
                 System.out.println("**********************************");
                 break;

             default:
                 System.out.println("Enter a valid input");
                 break;


         }
     } while (option != QUIT);
    }

    public  void probabilityPercentage(){ // this method coverts probability value of a given risk to percentage value.

        final double fixedProbability = 10.0;
        double maxPercentage = 100.0;
        String name = readRisk();
        name = new RiskEvaluator().name(name);
        Risk foundRisk = findRisk(name);
        if(foundRisk.getRiskName().equals(name)){
            double foundProbability = foundRisk.getProbability();
            double convertedProbability = (foundProbability/fixedProbability) * maxPercentage;
            System.out.println("The risk " + foundRisk.getRiskName() + " has a probability of " + convertedProbability
            + " %");
        } else
            System.out.println("The risk name does not exist.");
    }

    public void impactPercentage(){ // this method converts impact values of a given risk to percentage values.
        final double fixedImpact = 10.0;
        double maxPercentage = 100.0;
        String name = readRisk();
        name = new RiskEvaluator().name(name);
        Risk foundRisk = findRisk(name);
        if (foundRisk.getRiskName().equals(name)){
            double foundImpact = foundRisk.getImpact();
            double convertedImpact = (foundImpact/fixedImpact) * maxPercentage;
            System.out.println("The risk "+ foundRisk.getRiskName() + " has an impact of "+ convertedImpact + " %");
        }
        else
            System.out.println("The risk name does not exist");
    }
    public Risk findRisk (String name){ // got stuck in a loop.// throws an exception.

         if (retrieveRegisteredRisk(name) == null) {

             do {
                 System.out.println("There is no risk registered in that name , do one of the following steps.");

             } while (retrieveRegisteredRisk(name)== null);

         }

        return retrieveRegisteredRisk(name);
    }

    public static void main (String []args){ // This is where the program will run.
        RiskMatrix program = new RiskMatrix();
        program.run();
    }

}



