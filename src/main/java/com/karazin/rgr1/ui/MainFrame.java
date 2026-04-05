package com.karazin.rgr1.ui;

import com.karazin.rgr1.core.*;
import com.karazin.rgr1.io.CSVReader;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;

public class MainFrame extends JFrame {
    private JTextField functionField;
    private JTextField paramField;
    private JTextField xMinField, xMaxField;
    private JTextArea resultArea;
    private FunctionPlotter plotter;
    private Evaluatable currentFunction;
    
    public MainFrame() {
        setTitle("РГР №1 - Аналізатор функцій");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.NORTH);
        
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        scrollPane.setBorder(new TitledBorder("Результати"));
        add(scrollPane, BorderLayout.CENTER);
        
        plotter = new FunctionPlotter();
        add(plotter, BorderLayout.SOUTH);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Параметри"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Функція f(x):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        functionField = new JTextField("exp(-a*x*x)*sin(x)", 30);
        panel.add(functionField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(new JLabel("Параметр a:"), gbc);
        gbc.gridx = 1;
        paramField = new JTextField("1.0", 10);
        panel.add(paramField, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("x від:"), gbc);
        gbc.gridx = 3;
        xMinField = new JTextField("-3", 5);
        panel.add(xMinField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("x до:"), gbc);
        gbc.gridx = 1;
        xMaxField = new JTextField("3", 5);
        panel.add(xMaxField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton evalBtn = new JButton("Обчислити");
        JButton plotBtn = new JButton("Побудувати графік");
        JButton loadCSVBtn = new JButton("Завантажити CSV");
        JButton derivativeBtn = new JButton("Похідна");
        
        evalBtn.addActionListener(e -> evaluateFunction());
        plotBtn.addActionListener(e -> plotFunction());
        loadCSVBtn.addActionListener(e -> loadFromCSV());
        derivativeBtn.addActionListener(e -> computeDerivative());
        
        buttonPanel.add(evalBtn);
        buttonPanel.add(plotBtn);
        buttonPanel.add(loadCSVBtn);
        buttonPanel.add(derivativeBtn);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private void evaluateFunction() {
        try {
            double a = Double.parseDouble(paramField.getText());
            double x = askForX();
            
            currentFunction = new AnalyticFunction(a);
            double result = currentFunction.evalf(x);
            
            resultArea.append(String.format("f(%.4f) = %.10f\n", x, result));
        } catch (Exception ex) {
            resultArea.append("Помилка: " + ex.getMessage() + "\n");
        }
    }
    
    private void computeDerivative() {
        try {
            double a = Double.parseDouble(paramField.getText());
            double x = askForX();
            
            currentFunction = new AnalyticFunction(a);
            NumericalMethods.DerivativeResult detailed = 
                NumericalMethods.derivativeWithInfo(x, 1e-8, currentFunction);
            
            resultArea.append(String.format(
                "f'(%.4f) = %.10f (похибка: %.2e, ітерацій: %d)\n",
                x, detailed.getValue(), detailed.getEstimatedError(), detailed.getIterations()));
        } catch (Exception ex) {
            resultArea.append("Помилка: " + ex.getMessage() + "\n");
        }
    }
    
    private void plotFunction() {
        try {
            double a = Double.parseDouble(paramField.getText());
            double xMin = Double.parseDouble(xMinField.getText());
            double xMax = Double.parseDouble(xMaxField.getText());
            
            currentFunction = new AnalyticFunction(a);
            plotter.setFunction(currentFunction, xMin, xMax);
            
            resultArea.append(String.format("Графік побудовано на [%.2f, %.2f]\n", xMin, xMax));
        } catch (Exception ex) {
            resultArea.append("Помилка: " + ex.getMessage() + "\n");
        }
    }
    
    private void loadFromCSV() {
        JFileChooser chooser = new JFileChooser("data");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                TabularFunctionAdvanced tabFunc = new TabularFunctionAdvanced();
                tabFunc.loadFromCSV(chooser.getSelectedFile().getPath());
                currentFunction = tabFunc;
                
                double xMin = Double.parseDouble(xMinField.getText());
                double xMax = Double.parseDouble(xMaxField.getText());
                plotter.setFunction(currentFunction, xMin, xMax);
                
                resultArea.append("Завантажено табличну функцію з " + 
                    tabFunc.size() + " точок\n");
            } catch (IOException ex) {
                resultArea.append("Помилка: " + ex.getMessage() + "\n");
            }
        }
    }
    
    private double askForX() {
        String input = JOptionPane.showInputDialog(this, "Введіть значення x:", 
            "Обчислення", JOptionPane.QUESTION_MESSAGE);
        return Double.parseDouble(input);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainFrame().setVisible(true);
        });
    }
}
