package com.karazin.rgr1.core;

import com.karazin.rgr1.interpolation.Point2D;
import com.karazin.rgr1.io.CSVReader;
import java.util.*;

/**
 * Таблична функція з використанням TreeSet та TreeMap
 */
public class TabularFunctionAdvanced implements Evaluatable {
    
    private TreeSet<Point2D> pointsSet;
    private TreeMap<Double, Double> pointsMap;
    
    public TabularFunctionAdvanced() {
        pointsSet = new TreeSet<>((p1, p2) -> Double.compare(p1.getX(), p2.getX()));
        pointsMap = new TreeMap<>();
    }
    
    public void addPoint(double x, double y) {
        Point2D p = new Point2D(x, y);
        pointsSet.add(p);
        pointsMap.put(x, y);
    }
    
    public void addPoint(Point2D p) {
        pointsSet.add(p);
        pointsMap.put(p.getX(), p.getY());
    }
    
    public void loadFromCSV(String filename) throws java.io.IOException {
        pointsSet.clear();
        pointsMap.clear();
        List<Point2D> points = CSVReader.readPoints(filename);
        for (Point2D p : points) {
            addPoint(p);
        }
    }
    
    public int size() {
        return pointsSet.size();
    }
    
    public TreeSet<Point2D> getPointsSet() {
        return new TreeSet<>(pointsSet);
    }
    
    public TreeMap<Double, Double> getPointsMap() {
        return new TreeMap<>(pointsMap);
    }
    
    @Override
    public double evalf(double x) {
        if (pointsSet.isEmpty()) return 0;
        
        if (pointsMap.containsKey(x)) {
            return pointsMap.get(x);
        }
        
        Double floorKey = pointsMap.floorKey(x);
        Double ceilingKey = pointsMap.ceilingKey(x);
        
        if (floorKey == null) return pointsMap.get(ceilingKey);
        if (ceilingKey == null) return pointsMap.get(floorKey);
        if (floorKey.equals(ceilingKey)) return pointsMap.get(floorKey);
        
        double x1 = floorKey;
        double x2 = ceilingKey;
        double y1 = pointsMap.get(x1);
        double y2 = pointsMap.get(x2);
        
        return y1 + (y2 - y1) * (x - x1) / (x2 - x1);
    }
    
    @Override
    public String toString() {
        return String.format("TabularFunctionAdvanced[points=%d]", size());
    }
}
