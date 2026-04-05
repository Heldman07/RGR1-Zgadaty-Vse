package com.karazin.rgr1.ui;

import com.karazin.rgr1.core.Evaluatable;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class FunctionPlotter extends JPanel {
    private Evaluatable function;
    private double xMin = -5, xMax = 5;
    private double yMin = -2, yMax = 2;
    
    public FunctionPlotter() {
        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Графік функції"));
    }
    
    public void setFunction(Evaluatable f, double xMin, double xMax) {
        this.function = f;
        this.xMin = xMin;
        this.xMax = xMax;
        updateYBounds();
        repaint();
    }
    
    private void updateYBounds() {
        if (function == null) return;
        double step = (xMax - xMin) / 100;
        yMin = Double.POSITIVE_INFINITY;
        yMax = Double.NEGATIVE_INFINITY;
        
        for (double x = xMin; x <= xMax; x += step) {
            double y = function.evalf(x);
            if (Double.isFinite(y)) {
                yMin = Math.min(yMin, y);
                yMax = Math.max(yMax, y);
            }
        }
        double margin = (yMax - yMin) * 0.1;
        yMin -= margin;
        yMax += margin;
        if (yMin == yMax) { yMin--; yMax++; }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (function == null) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int width = getWidth(), height = getHeight();
        drawGrid(g2d, width, height);
        drawAxes(g2d, width, height);
        drawFunction(g2d, width, height);
    }
    
    private void drawGrid(Graphics2D g2d, int width, int height) {
        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = -10; i <= 10; i++) {
            int x = xToPixel(i, width);
            if (x >= 0 && x <= width) g2d.drawLine(x, 0, x, height);
            int y = yToPixel(i, height);
            if (y >= 0 && y <= height) g2d.drawLine(0, y, width, y);
        }
    }
    
    private void drawAxes(Graphics2D g2d, int width, int height) {
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        int x0 = xToPixel(0, width);
        int y0 = yToPixel(0, height);
        g2d.drawLine(0, y0, width, y0);
        g2d.drawLine(x0, 0, x0, height);
        g2d.drawString("X", width - 10, y0 - 5);
        g2d.drawString("Y", x0 + 5, 15);
    }
    
    private void drawFunction(Graphics2D g2d, int width, int height) {
        g2d.setColor(new Color(31, 119, 180));
        g2d.setStroke(new BasicStroke(2));
        double step = (xMax - xMin) / width;
        int prevX = -1, prevY = -1;
        
        for (int px = 0; px <= width; px++) {
            double x = xMin + px * step;
            double y = function.evalf(x);
            if (Double.isFinite(y)) {
                int py = yToPixel(y, height);
                if (prevX >= 0 && Math.abs(py - prevY) < height / 2) {
                    g2d.drawLine(prevX, prevY, px, py);
                }
                prevX = px;
                prevY = py;
            } else {
                prevX = -1;
                prevY = -1;
            }
        }
    }
    
    private int xToPixel(double x, int width) {
        return (int)((x - xMin) / (xMax - xMin) * width);
    }
    
    private int yToPixel(double y, int height) {
        return (int)((yMax - y) / (yMax - yMin) * height);
    }
}
