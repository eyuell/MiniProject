import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {
    public static void main(String[]args){

      systemStore drake=new systemStore();
        drake.registerScheduleVariance(100000, 50000,38 ,12,1 );
        drake.registerScheduleVariance(200000, 50000,38 ,15,2 );
        drake.registerCostVariance(100000,50000 ,38 ,12 , 78000 );
        System.out.println(drake.sizeOfArray());
        System.out.println(drake.printAllFinances());
    }
}


/*TODO: different classes move method to System. Try printing in main. Methods are done. Learn to add them to array,
TODO: Start on JsonFile.
TODO: Understand the graphical UI
TODO: Go to georges GIT and read the file where he used GUI and JSON files.
TODO: make the code run by 12/10.2018. WITHOUT GUI. METHODS SHOULD STORE AND BE FUNCTIONAL.
TODO: WORK ON GUI WHEN TIME COMES. STUDY QT OR FMXL.
 */
