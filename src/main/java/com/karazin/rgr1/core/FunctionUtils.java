package com.karazin.rgr1.core;

import java.io.*;
import java.util.*;

/**
 * Утиліти для роботи з функціями.
 * Новий клас після рефакторингу.
 */
public final class FunctionUtils {
    
    private FunctionUtils() {}
    
    /**
     * Табулює функцію на заданому інтервалі
     */
    public static List<double[]> tabulate(Evaluatable f, double xMin, double xMax, double step) {
        List<double[]> result = new ArrayList<>();
        for (double x = xMin; x <= xMax + 1e-10; x += step) {
            result.add(new double[]{x, f.evalf(x)});
        }
        return result;
    }
    
    /**
     * Зберігає результати табуляції у файл
     */
    public static void saveToFile(String filename, Evaluatable f, double xMin, double xMax, double step) 
            throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.printf("%15s%20s%20s%20s\n", "x", "f(x)", "f'(x)", "|error|");
            writer.printf("%s\n", "-".repeat(70));
            
            for (double x = xMin; x <= xMax + 1e-10; x += step) {
                double fx = f.evalf(x);
                double dfx = f.derivative(x);
                writer.printf("%15.6f%20.6f%20.6f%20.2e\n", x, fx, dfx, 0.0);
            }
        }
    }
    
    /**
     * Знаходить максимум функції на інтервалі
     */
    public static double findMax(Evaluatable f, double xMin, double xMax, double step) {
        double maxValue = Double.NEGATIVE_INFINITY;
        for (double x = xMin; x <= xMax + 1e-10; x += step) {
            double value = f.evalf(x);
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
    
    /**
     * Знаходить мінімум функції на інтервалі
     */
    public static double findMin(Evaluatable f, double xMin, double xMax, double step) {
        double minValue = Double.POSITIVE_INFINITY;
        for (double x = xMin; x <= xMax + 1e-10; x += step) {
            double value = f.evalf(x);
            if (value < minValue) {
                minValue = value;
            }
        }
        return minValue;
    }
}
