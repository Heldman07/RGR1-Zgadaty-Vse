package com.karazin.rgr1.core;

/**
 * Базовий інтерфейс для всіх функцій
 * Після рефакторингу: додано default методи
 */
@FunctionalInterface
public interface Evaluatable {
    
    /** Обчислює значення функції в точці x */
    double evalf(double x);
    
    /** Обчислює похідну функції в точці x */
    default double derivative(double x) {
        return NumericalMethods.derivative(x, this);
    }
    
    /** Обчислює похідну з заданою точністю */
    default double derivative(double x, double tolerance) {
        return NumericalMethods.derivative(x, tolerance, this);
    }
    
    /** Перевіряє, чи функція визначена в точці */
    default boolean isDefinedAt(double x) {
        try {
            evalf(x);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
