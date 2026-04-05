package com.karazin.rgr1.core;

/**
 * Клас з чисельними методами.
 * Після рефакторингу: додано документацію та клас для результату
 */
public final class NumericalMethods {
    
    private NumericalMethods() {
        // Забороняємо створення екземплярів
    }
    
    /**
     * Результат обчислення похідної
     */
    public static class DerivativeResult {
        private final double value;
        private final double estimatedError;
        private final int iterations;
        
        public DerivativeResult(double value, double error, int iterations) {
            this.value = value;
            this.estimatedError = error;
            this.iterations = iterations;
        }
        
        public double getValue() { return value; }
        public double getEstimatedError() { return estimatedError; }
        public int getIterations() { return iterations; }
        
        @Override
        public String toString() {
            return String.format("value=%.10f, error=%.2e, iterations=%d", 
                value, estimatedError, iterations);
        }
    }
    
    /**
     * Центральна різницева формула для похідної
     */
    private static double centralDifference(double x, double h, Evaluatable f) {
        return (f.evalf(x + h) - f.evalf(x - h)) / (2 * h);
    }
    
    /**
     * Обчислює похідну з заданою точністю
     */
    public static double derivative(double x, double tolerance, Evaluatable f) {
        return derivativeWithInfo(x, tolerance, f).getValue();
    }
    
    /**
     * Обчислює похідну з точністю за замовчуванням
     */
    public static double derivative(double x, Evaluatable f) {
        return derivative(x, 1e-8, f);
    }
    
    /**
     * Обчислює похідну з додатковою інформацією
     */
    public static DerivativeResult derivativeWithInfo(double x, double tolerance, Evaluatable f) {
        double h = 0.1;
        double prev = centralDifference(x, h, f);
        double curr = centralDifference(x, h / 10, f);
        int iterations = 1;
        
        for (; iterations < 100; iterations++) {
            h /= 10;
            double next = centralDifference(x, h, f);
            
            if (Math.abs(next - curr) >= Math.abs(curr - prev) ||
                Math.abs(curr - prev) < tolerance) {
                return new DerivativeResult(curr, Math.abs(curr - prev), iterations);
            }
            
            prev = curr;
            curr = next;
        }
        
        return new DerivativeResult(curr, Math.abs(curr - prev), iterations);
    }
}
