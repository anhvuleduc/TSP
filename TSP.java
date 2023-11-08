import java.util.*;
import java.util.concurrent.TimeUnit;
public class TSP {
    public static boolean Terminate(List<Double> best){
        if (best.size() < 100) return true;
        else{
            double a = best.get(best.size() - 100);
            double b = best.get(best.size() - 1);
            if (b > a * 99/100) return false;
            else return true;
        }
    }
    public static void main(String[] args) throws InterruptedException{
        Scanner input = new Scanner(System.in);
        int numOfCities = input.nextInt();
        List<Point> listOfCities = new ArrayList<Point>();
        List<Double> best = new ArrayList<Double>();
        for (int i = 0; i < numOfCities; i++){
           double x = input.nextDouble();
           double y = input.nextDouble();
           Point city = new Point(x,y);
           listOfCities.add(city);
        }
        HarmonySearch hm = new HarmonySearch(0.95,0.1,100, listOfCities);
        Memory memory = hm.initMemory();
        int generation = 0;
        double initial = hm.getFittest(memory, 0).getIndividualFitness(listOfCities);
        while (generation < 1000){
           hm.evalMemory(memory);
           System.out.println("Generation: " + generation);
           System.out.println("Current best: " + hm.getFittest(memory, 0).getIndividualFitness(listOfCities));
           System.out.println("Current worst: " + hm.getFittest(memory, memory.getMemory().length-1).getIndividualFitness(listOfCities));
           memory = hm.Crossover(memory);
           best.add(hm.getFittest(memory, 0).getIndividualFitness(listOfCities));
           generation++;
           TimeUnit.SECONDS.sleep(0);
           System.out.println("");

        }
        System.out.println("Initial length: " + initial); 
        System.out.print("Best solution: ");
        hm.getFittest(memory, 0).display();
        System.out.println("Path length: " + hm.getFittest(memory, 0).getIndividualFitness(listOfCities));
        input.close();
    }
}
