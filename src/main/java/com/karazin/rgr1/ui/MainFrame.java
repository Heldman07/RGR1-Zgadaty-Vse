package com.karazin.rgr1.ui;

import com.karazin.rgr1.core.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTextField paramField;
    private JTextField xMinField, xMaxField;
    private JTextArea resultArea;
    private FunctionPlotter plotter;
    
    public MainFrame() {
        setTitle("РГР №1 - Аналізатор функцій");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new TitledBorder("Параметри"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Параметр a:"), gbc);
        gbc.gridx = 1;
        paramField = new JTextField("1.0", 10);
        inputPanel.add(paramField, gbc);
        
        gbc.gridx = 2;
        inputPanel.add(new JLabel("x від:"), gbc);
        gbc.gridx = 3;
        xMinField = new JTextField("-3", 5);
        inputPanel.add(xMinField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("x до:"), gbc);
        gbc.gridx = 1;
        xMaxField = new JTextField("3", 5);
        inputPanel.add(xMaxField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton plotBtn = new JButton("Побудувати графік");
        plotBtn.addActionListener(e -> plotFunction());
        buttonPanel.add(plotBtn);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
        inputPanel.add(buttonPanel, gbc);
        
        add(inputPanel, BorderLayout.NORTH);
        
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        scrollPane.setBorder(new TitledBorder("Результати"));
        add(scrollPane, BorderLayout.CENTER);
        
        plotter = new FunctionPlotter();
        add(plotter, BorderLayout.SOUTH);
    }
    
    private void plotFunction() {
        try {
            double a = Double.parseDouble(paramField.getText());
            double xMin = Double.parseDouble(xMinField.getText());
            double xMax = Double.parseDouble(xMaxField.getText());
            
            Evaluatable f = new AnalyticFunction(a);
            plotter.setFunction(f, xMin, xMax);
            resultArea.append(String.format("Графік f(x)=exp(-%.1f*x²)*sin(x) на [%.1f, %.1f]\n", a, xMin, xMax));
        } catch (Exception ex) {
            resultArea.append("Помилка: " + ex.getMessage() + "\n");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {}
            new MainFrame().setVisible(true);
        });
    }
}
