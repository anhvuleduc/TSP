import java.util.*;

public class Individual{
    private List<Integer> individual;
    public Individual(List<Integer> individual){
        this.individual = individual;
    }
    public Individual(int pathlength){
        this.individual = new ArrayList<Integer>();
        for (int i = 0; i < pathlength; i++){
            individual.add(i);
        }
        Collections.shuffle(individual);  
    }
    public List<Integer> getIndividual(){
        return individual;
    }
    public void setIndividual(List<Integer> individual){
        this.individual = individual;
    }
    public int getCity(int pos){
        return individual.get(pos);
    }
    public void setCity(int pos, int n){
        individual.set(pos,n);
    }
    public double getIndividualFitness(List<Point> listofcities){
        List<Point> cities = new ArrayList<Point>();
        for (int i = 0; i < individual.size(); i++){
            cities.add(listofcities.get(individual.get(i)));
        }
        Path path = new Path(cities);
        return path.getLength();
    }
    public void display(){
        System.out.println(individual);
    }
}