import java.util.*;
public class Path{
    public List<Point> path;
    public Path(){
        this.path = new ArrayList<Point>();
    }
    public Path(List<Point> points){
        this.path = points;
    }
    public void appendPoint(Point point){
        path.add(point);
    }
    public void appendPoint(double x, double y){
        Point point = new Point(x,y);
        path.add(point);
    }
    public void changePoint(int pos, double x, double y){
        path.set(pos, new Point(x,y));
    }
    public double getLength(){
        if (path.size() <= 1) return 0;
        double ans = 0;
        for (int i = 0; i < path.size()-1; i++){
           double x1 = path.get(i).getX();
           double y1 = path.get(i).getY();
           double x2 = path.get(i+1).getX();
           double y2 = path.get(i+1).getY();
           ans += Math.sqrt((x1-x2) * (x1-x2) + (y1-y2) * (y1-y2));
        }
        return ans;
    }
}