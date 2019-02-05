package com.airwallex.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.airwallex.exceptions.ApplicationException;

public class CalculatorApplication {
    public static void main(String[] args) throws IOException {
        Calculator calculator = new Calculator();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        while (!"".equals(input)) {
            try {
                calculator.perform(input);
            } catch (ApplicationException e) {
                System.out.println(e.getMessage());
			} finally {
                calculator.print();
            }
            input = br.readLine();
        }
    }
}