package com.karazin.rgr1.interpolation;

public class Point2D implements Comparable<Point2D> {
    private double x;
    private double y;
    
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Point2D() {
        this(0, 0);
    }
    
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    
    @Override
    public int compareTo(Point2D other) {
        return Double.compare(this.x, other.x);
    }
    
    @Override
    public String toString() {
        return String.format("(%.4f, %.4f)", x, y);
    }
}
