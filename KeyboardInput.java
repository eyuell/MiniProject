package MiniProject;

import java.util.Scanner;

//this class is to collect keyboard inputs throughout the project
public class KeyboardInput {

        //Integer inputs collection
    public int Int(){
        int evaluatedKey = 0;   //to be returned
        int multiplier = 1;     //reserved for negative numbers
        final int YES = 0;
        final int ZERO = 0;
        final int NEGATIVE = -1;
        int repeat = 0;

        Scanner newKeyTyped = new Scanner(System.in);

        String typedKey = newKeyTyped.nextLine();   //reading string


        while (repeat == YES ){
            int initialLength = typedKey.length();
            if(typedKey.contains("-") && typedKey.indexOf("-") == ZERO ){  //if the string starts with a negative
                multiplier = NEGATIVE;
            }

            // every character except numbers will be replaced by empty string
            String shortedText = typedKey.replaceAll("[^0-9]", "");
            int finalLength = shortedText.length();

            // if the input is only of numbers and if input is not empty string
            if ((initialLength == finalLength) && !typedKey.equals("")){
                evaluatedKey = Integer.parseInt(typedKey); // the input string is converted to int
                repeat ++;

                // the input string is converted back to -ve int
            }else if(multiplier < ZERO && shortedText.length() != ZERO){
                evaluatedKey = multiplier * Integer.parseInt(shortedText);
                repeat ++;
            }else { //otherwise the input is not a valid number format and another try will be made
                System.out.print("Error !!! Please enter the required type of input. ");
                typedKey = newKeyTyped.nextLine();
                repeat = YES;
            }
        }
        return evaluatedKey;
    }

    //Integer inputs collection
    public int positiveInt(){
        int evaluatedKey = 0;   //to be returned
        final int YES = 0;
        final int ZERO = 0;
        int repeat = 0;

        Scanner newKeyTyped = new Scanner(System.in);

        String typedKey = newKeyTyped.nextLine();   //reading string


        while (repeat == YES ){
            int initialLength = typedKey.length();
            if(typedKey.contains("-") && typedKey.indexOf("-") == ZERO ){  //if the string starts with a negative
                System.out.println("A positive value is needed");
            }

            // every character except numbers will be replaced by empty string
            String shortedText = typedKey.replaceAll("[^0-9]", "");
            int finalLength = shortedText.length();

            // if the input is only of numbers and if input is not empty string
            if ((initialLength == finalLength) && !typedKey.equals("")){
                evaluatedKey = Integer.parseInt(typedKey); // the input string is converted to int
                repeat ++;
            }else { //otherwise the input is not a valid number format and another try will be made
                System.out.print("Please enter the required type of input. ");
                typedKey = newKeyTyped.nextLine();
                repeat = YES;
            }
        }
        return evaluatedKey;
    }

        //Double inputs collection
    public double Double(){
        double evaluatedKey = 0.0;
        double multiplier = 1.0;
        final int YES = 0;
        final int ZERO = 0;
        final int NEGATIVE = -1;
        int repeat = 0;

        Scanner newKeyTyped = new Scanner(System.in);

        String typedKey= newKeyTyped.nextLine();  //input as a string

        while (repeat == YES ){
            int initialLength = typedKey.length();
            if(typedKey.contains("-") && typedKey.indexOf("-") == 0){  //if the string is negative
                multiplier = NEGATIVE;
            }
            // every character except numbers will be replaced by empty string
            String shortedText = typedKey.replaceAll("[^0-9.]", "");
            int finalLength = shortedText.length();

            // if the input is only of numbers and if input is not empty string
            if ((initialLength == finalLength) && !typedKey.equals("")){
                evaluatedKey = Double.parseDouble(typedKey); // the input string is converted to Double
                repeat++;

                //keeping the back the -ve sign
            }else if (multiplier < ZERO && shortedText.length()!= ZERO ) {
                evaluatedKey = multiplier * Double.parseDouble(shortedText);
                repeat++;
            } else { //otherwise the input is not a valid number format and another try will be made
                System.out.print("Error !!! Please enter the required type of input. ");
                typedKey= newKeyTyped.nextLine();
                repeat = YES;
            }
        }
        return evaluatedKey;
    }

    //Double inputs collection
    public double positiveDouble(){
        double evaluatedKey = 0.0;
        final int YES = 0;
        final int ZERO = 0;
        int repeat = 0;

        Scanner newKeyTyped = new Scanner(System.in);

        String typedKey= newKeyTyped.nextLine();  //input as a string

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

    //Strings input collection
    public String Line(){

        final int ZERO = 0;

        Scanner newKeyTyped = new Scanner(System.in);
        String typedKey= newKeyTyped.nextLine();

        //All the spaces will be replaced by empty string
        String testString = typedKey.replaceAll(" ", "");

        //if no string is given OR if only spaces are given as an input, error handling
        if((typedKey.length() == ZERO) || (testString.length() == ZERO)){
            do {
                System.out.print("Enter valid string input. ");
                typedKey = newKeyTyped.nextLine();//New string input re-signed
                System.out.println();

                //again the spaces are replaced by empty string to see if there is
                // any other characters remaining as an input
                testString = typedKey.replaceAll(" ", "");

                //unless a minimum of 1 character is given, repeat the loop
            }while ((typedKey.length() == ZERO) || (testString.length() == ZERO));
        }

        //taking a maximum loop as the length of the string, any double spaces are reduced to one
        for(int i = ZERO; i < (typedKey.length()); i++) {
            typedKey = typedKey.replaceAll("  "," ");
        }
        return typedKey;
    }

    //this just to capture an enter key used as a pausing mechanism
    public void enter(){
        Scanner newKeyTyped = new Scanner(System.in);
        newKeyTyped.nextLine();
    }
}