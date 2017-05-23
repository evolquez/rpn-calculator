package com.oletob.rpncalc.model;

import java.util.Arrays;

/**
 * This class includes some methods to support the logic of the RPN calc.
 * @author Algenis Eduardo Volquez <evolquez@gmail.com>
 *         https://github.com/oletob/
 * @version 1.0
 */
public final class Rpn {

    public Rpn(){

    }

    /**
     * Format the input on calculator
     * @param input
     * @return String
     */
    public String formatInput(String[] input){

        String inputFormatted = "";

        int cont = 0;
        double number;

        for (String value : input){
            number = Double.parseDouble(value);
            cont += 1;
            inputFormatted += number;

            if(cont < input.length){
                inputFormatted += "\n";
            }
        }
        return inputFormatted;
    }

    /**
     * Changes the symbol of the last input (- +)
     * @param values
     * @return String
     */
    public String changeInputSymbol(String[] values){
        String input = "";

        double value = Double.parseDouble(values[values.length - 1]);

        if( value > 0 || value < 0){
            value = (value * -1);
            values[values.length - 1] = String.valueOf(value);

            input = this.formatInput(values);
        }

        return input;
    }

    /**
     * Remove the last number input by the user
     * @param input
     * @return String
     * */
    public String delete(String input){

        input = input.substring(0, input.length() - 1);

        return input;
    }

    /**
     * Proccess the operation taken, then return the formatted text for the input text view
     * @param input
     * @param operatorSymbol
     * @return String
     * */
    public String proccess(String[] input, String operatorSymbol){

        // First, format the input
        input = this.formatInput(input).split("\n");

        if(input.length > 1){

            double num1 = Double.parseDouble(input[input.length - 2]);
            double num2 = Double.parseDouble(input[input.length - 1]);
            double rs   = 0;

            switch (operatorSymbol){
                case "/":
                    rs = ((num1) / (num2));
                    break;
                case "X":
                    rs = ((num1) * (num2));
                    break;
                case "-":
                    rs = ((num1) - (num2));
                    break;
                case "+":
                    rs = ((num1) + (num2));
                    break;
            }
            rs = Math.round(rs);

            // Add the result
            input = Arrays.copyOf(input, (input.length - 1));
            input[input.length - 1] = String.valueOf(rs);
        }

        return this.formatInput(input);
    }
}