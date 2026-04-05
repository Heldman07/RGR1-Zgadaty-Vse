package com.karazin.rgr1.core;

public class AnalyticFunction implements Evaluatable {
    private double a;
    
    public AnalyticFunction(double a) {
        this.a = a;
    }
    
    public AnalyticFunction() {
        this(1.0);
    }
    
    @Override
    public double evalf(double x) {
        return Math.exp(-a * x * x) * Math.sin(x);
    }
    
    public double getA() { return a; }
    public void setA(double a) { this.a = a; }
}
