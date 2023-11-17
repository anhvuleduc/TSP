import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

public class HarmonySearch{

   private double hmcr; //Harmony Memory Considering Rate
   private double par;  //Pitch Adjusting Rate
   private int memorySize;
   private List<Point> listOfCities;

   public HarmonySearch(double hmcr, double par, int memorySize, List<Point> listOfCities){
     this.hmcr = hmcr;
     this.par = par;
     this.memorySize = memorySize;
     this.listOfCities = listOfCities;
   }

   public Memory initMemory(){
      Memory memory = new Memory(memorySize, listOfCities.size(), listOfCities);
      return memory;
   }

   public Individual getFittest(Memory memory, int pos){
        Arrays.sort(memory.getMemory(), new Comparator<Individual>(){
            public int compare(Individual path1, Individual path2){
                if (path1.getIndividualFitness(listOfCities) < path2.getIndividualFitness(listOfCities))
                  return -1;
                else if (path1.getIndividualFitness(listOfCities) > path2.getIndividualFitness(listOfCities))
                  return 1;
                else return 0;
            }
        });
        return memory.getMemory()[pos];
    }

    public void evalMemory(Memory memory){
      Arrays.sort(memory.getMemory(), new Comparator<Individual>(){
            public int compare(Individual path1, Individual path2){
                if (path1.getIndividualFitness(listOfCities) < path2.getIndividualFitness(listOfCities))
                  return -1;
                else if (path1.getIndividualFitness(listOfCities) > path2.getIndividualFitness(listOfCities))
                  return 1;
                else return 0;
            }
        });
    }

    public Memory Crossover(Memory memory){
      this.evalMemory(memory);
      Individual newIndividual = new Individual(listOfCities.size());
      double ratio = Math.random();
      if (ratio <= hmcr){
        System.out.println("Crossover happens");
        List<Integer> Unused = new ArrayList<Integer>();
        for (int i = 0; i < listOfCities.size(); i++) Unused.add(i);
        boolean[] used = new boolean[listOfCities.size()];
        for (int i = 0; i < listOfCities.size(); i++) used[i] = false;
        for (int i = 0; i < listOfCities.size(); i++){
          double rant = Math.random();
          int pos = (int)(rant * (memorySize)); // lấy gen từ cá thể ở vị trí pos
          int num = memory.getMemory()[pos].getCity(i); 
          int times = 1;
          while (times < 10 && used[num] == true){
            rant = Math.random();
            num = memory.getMemory()[pos].getCity(i);
            times++;
          }
          if (used[num] == false){
            newIndividual.setCity(i,num);
            used[num] = true;
            Collections.sort(Unused);
            int index = Collections.binarySearch(Unused, num);
            Unused.remove(index);
            newIndividual.setCity(i,num);
          }
          else {
           for (int j = 0; j < listOfCities.size(); j++){
             if (used[j] == false){
               newIndividual.setCity(i, j);
               used[j] = true;
               break;
             }
           }
            // Một cách CrossOver khác nhưng Vũ test thấy ko ra kết quả vjp lắm
            // Collections.shuffle(Unused);
            // num = Unused.get(Unused.size()-1);
            // used[num] = true;
            // Unused.remove(Unused.size()-1);
            // newIndividual.setCity(i, num);

          }
        }
      }
      newIndividual = mutation(newIndividual);
      newIndividual.display();
      System.out.println(newIndividual.getIndividualFitness(listOfCities));
      Memory newMemory = new Memory(memorySize, listOfCities.size(), listOfCities);
      newMemory.setMemory(memory.getMemory());
      Individual worst = this.getFittest(memory, memorySize-1);
      if (worst.getIndividualFitness(listOfCities) > newIndividual.getIndividualFitness(listOfCities)){
             newMemory.setNote(memorySize-1, newIndividual);  
      }
      return newMemory;
    }


    public Individual mutation(Individual individual){
      if (individual.getIndividual().size() <= 2)
          return individual;
      Individual newIndividual = new Individual(listOfCities.size());
      newIndividual.setIndividual(individual.getIndividual());
      for (int i = 0; i < listOfCities.size(); i++){
        double ratio = Math.random();
        if (ratio > par) continue;
        if (newIndividual.getCity(i) == 0){
           for (int j = 0; j < listOfCities.size(); j++)
             if (newIndividual.getCity(j) == 1){
                newIndividual.setCity(j,0);
                break;}
            newIndividual.setCity(i, 1);
        }
        else if (newIndividual.getCity(i) == listOfCities.size() - 1){
           for (int j = 0; j < listOfCities.size(); j++)
             if (newIndividual.getCity(j) == listOfCities.size() - 2){
                newIndividual.setCity(j,listOfCities.size() - 1);
                break;
              }
            newIndividual.setCity(i, listOfCities.size() - 2);
        }
        else {
          int plus = 1;
          double rand = Math.random();
          if (rand < 0.5) plus = -1;
          for (int j = 0; j < listOfCities.size(); j++)
            if (newIndividual.getCity(j) == newIndividual.getCity(i) + plus){
               newIndividual.setCity(j, newIndividual.getCity(i));
               break;
            }
           newIndividual.setCity(i, newIndividual.getCity(i) + plus); 
        }
      }
      return newIndividual;
    }
}
