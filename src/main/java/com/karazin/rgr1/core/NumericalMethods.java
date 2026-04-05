package com.karazin.rgr1.core;

public class NumericalMethods {
    
    private static double centralDifference(double x, double h, Evaluatable f) {
        return (f.evalf(x + h) - f.evalf(x - h)) / (2 * h);
    }
    
    public static double derivative(double x, double tolerance, Evaluatable f) {
        double h = 0.1;
        double prev = centralDifference(x, h, f);
        double curr = centralDifference(x, h / 10, f);
        
        for (int i = 0; i < 100; i++) {
            h /= 10;
            double next = centralDifference(x, h, f);
            
            if (Math.abs(next - curr) >= Math.abs(curr - prev) ||
                Math.abs(curr - prev) < tolerance) {
                return curr;
            }
            
            prev = curr;
            curr = next;
        }
        return curr;
    }
    
    public static double derivative(double x, Evaluatable f) {
        return derivative(x, 1e-8, f);
    }
}
