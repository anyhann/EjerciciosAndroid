package com.anabellolguin.calculadoraconfragmentos;

public class Calculadora {
	public Calculadora() {

	}

	public double sum(double a, double b) {
		return a + b;
	}

	public String devide(double a, double b) throws ArithmeticException {
		if (b == 0) {
			
			throw new ArithmeticException("Division by zero!");
		} else {
			Double c = a / b;
			return c.toString();
		}
	}

	public double difference(double a, double b) {
		return a - b;
	}

	public double product(double a, double b) {
		return a * b;
	}

	

}
