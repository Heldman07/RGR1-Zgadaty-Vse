package com.karazin.rgr1.io;

import com.karazin.rgr1.interpolation.Point2D;
import java.io.*;
import java.util.*;

public class CSVReader {
    
    public static List<Point2D> readPoints(String filename) throws IOException {
        List<Point2D> points = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (isFirstLine && line.toLowerCase().contains("x")) {
                    isFirstLine = false;
                    continue;
                }
                isFirstLine = false;
                
                String[] parts = line.split("[;,]");
                if (parts.length >= 2) {
                    try {
                        double x = Double.parseDouble(parts[0].trim());
                        double y = Double.parseDouble(parts[1].trim());
                        points.add(new Point2D(x, y));
                    } catch (NumberFormatException e) {
                        System.err.println("Помилка парсингу: " + line);
                    }
                }
            }
        }
        return points;
    }
    
    public static void writePoints(String filename, List<Point2D> points) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("x;y");
            for (Point2D p : points) {
                writer.printf("%.6f;%.6f\n", p.getX(), p.getY());
            }
        }
    }
}
