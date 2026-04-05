package com.karazin.rgr1;

import com.karazin.rgr1.core.*;
import java.io.*;

public class DerivativeApplication {
    
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("РГР №1: Згадати все...");
        System.out.println("==================================================");
        
        // Створюємо папку для даних
        new File("data").mkdirs();
        
        // Тестуємо функції для різних a
        double[] aValues = {0.5, 1.0, 1.5};
        
        for (double a : aValues) {
            System.out.println("\nТестування функції f(x)=exp(-" + a + "*x²)*sin(x)");
            AnalyticFunction f = new AnalyticFunction(a);
            String filename = "data/f_a" + a + ".dat";
            
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                writer.printf("%15s%20s%20s\n", "x", "f(x)", "f'(x)");
                
                int count = 0;
                for (double x = 1.5; x <= 6.5 + 0.0001; x += 0.05) {
                    double fx = f.evalf(x);
                    double dfx = NumericalMethods.derivative(x, 1e-6, f);
                    writer.printf("%15.6f%20.6f%20.6f\n", x, fx, dfx);
                    count++;
                }
                
                System.out.println("   Збережено " + count + " точок у " + filename);
                System.out.println("   Приклад: x=2.0: f=" + String.format("%.6f", f.evalf(2.0)) + 
                    ", f'=" + String.format("%.6f", NumericalMethods.derivative(2.0, 1e-6, f)));
                
            } catch (IOException e) {
                System.err.println("Помилка: " + e.getMessage());
            }
        }
        
        System.out.println("\n==================================================");
        System.out.println("Роботу завершено! Дані збережено в папку data/");
    }
}
