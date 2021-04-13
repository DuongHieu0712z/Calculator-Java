
package calculator;

import symbol.Symbol;

/**
 *
 * @author DuongHieu
 */
public class Calculator {
    private double firstNumber;
    private double secondNumber;
    private String operator;

    public Calculator() {
        firstNumber = 0;
        secondNumber = 0;
        operator = "";
    }

    public double getFirstNumber() {
        return this.firstNumber;
    }

    public void setFirstNumber(double firstNumber) {
        this.firstNumber = firstNumber;
    }

    public void setFirstNumber(String firstNumberStr) {
        firstNumberStr = firstNumberStr.replace(Symbol.minus, "-");
        this.firstNumber = Double.parseDouble(firstNumberStr);
    }

    public double getSecondNumber() {
        return this.secondNumber;
    }

    public void setSecondNumber(double secondNumber) {
        this.secondNumber = secondNumber;
    }

    public void setSecondNumber(String secondNumberStr) {
        secondNumberStr = secondNumberStr.replace(Symbol.minus, "-");
        this.secondNumber = Double.parseDouble(secondNumberStr);
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
