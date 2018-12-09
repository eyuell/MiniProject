public class Main {
    public static void main(String[] args) {
        systemStore drake = new systemStore();
        drake.registerScheduleVariance(100000, 50000, 38, 12, "1");
        drake.registerScheduleVariance(200000, 50000, 38, 15, "2");
        drake.registerCostVariance(100000, 50000, 38, 12, 78000,"1");
        //System.out.println(drake.sizeOfArray());
        //System.out.println(drake.printAllFinances());
        System.out.println( drake.printAllFinancesByID("1"));
        System.out.println(drake.printAllFinances());
    }

}


