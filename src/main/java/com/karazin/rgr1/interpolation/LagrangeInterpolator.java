package com.karazin.rgr1.interpolation;

import com.karazin.rgr1.core.Evaluatable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LagrangeInterpolator implements Evaluatable {
    private List<Point2D> points;
    
    public LagrangeInterpolator() {
        points = new ArrayList<>();
    }
    
    public void addPoint(Point2D point) {
        points.add(point);
    }
    
    public void addPoint(double x, double y) {
        points.add(new Point2D(x, y));
    }
    
    public void clear() {
        points.clear();
    }
    
    public void sort() {
        Collections.sort(points);
    }
    
    public int size() {
        return points.size();
    }
    
    public Point2D getPoint(int index) {
        return points.get(index);
    }
    
    @Override
    public double evalf(double x) {
        if (points.isEmpty()) return 0;
        
        double result = 0.0;
        int n = points.size();
        
        for (int i = 0; i < n; i++) {
            double term = points.get(i).getY();
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    term *= (x - points.get(j).getX()) / 
                            (points.get(i).getX() - points.get(j).getX());
                }
            }
            result += term;
        }
        return result;
    }
}
