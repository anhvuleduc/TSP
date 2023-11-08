import java.util.*;
public class Memory {
    private Individual[] memory;
    private List<Point> listofcities;
    public Memory(int memorysize, int numofcities, List<Point> listofcities){
        memory = new Individual[memorysize];
        for (int i = 0; i < memorysize; i++)
           memory[i] = new Individual(numofcities);
        this.listofcities = listofcities;
    }
    public Individual[] getMemory(){
        return memory;
    }
    public void setMemory(Individual[] memory){
        this.memory = memory;
    }
    public void setNote(int pos, Individual individual){
        memory[pos] = individual;
    }
    public Individual getNote(int pos){
        return memory[pos];
    }
    public Individual getFittest(int pos){
        Arrays.sort(this.memory, new Comparator<Individual>(){
            public int compare(Individual path1, Individual path2){
                if (path1.getIndividualFitness(listofcities) < path2.getIndividualFitness(listofcities))
                  return -1;
                else if (path1.getIndividualFitness(listofcities) > path2.getIndividualFitness(listofcities))
                  return 1;
                else return 0;
            }
        });
        return memory[pos];
    }
    
}