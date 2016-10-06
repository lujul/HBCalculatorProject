package laurentesp.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SOEOSSA on 04/10/2016.
 */

public class Calculator {

    // The 2 attributes valOperandX are used to keep the value of the operands in case of a change in an activity
    private static StringBuffer valOperand1 = new StringBuffer("0");
    private static StringBuffer valOperand2 = new StringBuffer("0");

    // The attribute operator is used to keep the value of the operator in case of a change in a activity
    private static StringBuffer operator = new StringBuffer("");

    // The attribute curOperand is used to determine during the calculation if the user is entering the first or the second operand
    private static int curOperand = 0;

    // This attribute is used to clear the StringBuffer for the display after the choice of an operator as soon as a new operand is written
    private static boolean takeStringIn = true;


    static String getValOutToShowtoUser(String stringIn, String stringInTextView) {
        StringBuffer stringBufOut = new StringBuffer("");
        switch (stringIn) {
            // The "C" button is used to clear the 2 operands and the operator
            case "C":
                valOperand1.setLength(0);
                valOperand2.setLength(0);
                curOperand = 0; // Go Back to first operand
                stringBufOut.setLength(0);
                stringBufOut.append("0");
                break;

            case "+":
                // Test if we are already entered the second operand, in this case we have to show the result of the first operation
                if (curOperand == 1) {
                    stringBufOut.append(getResultFromOperatorOnOperands(valOperand1.toString(), valOperand2.toString(), operator.toString()));
                    valOperand1 = stringBufOut;
                } else {
                    stringBufOut.append(stringInTextView);
                }
                prepareOperator("addFunction");
                break;

            case "-":
                stringBufOut.append(stringInTextView);
                prepareOperator("minusFunction");
                break;

            case "x":
                stringBufOut.append(stringInTextView);
                prepareOperator("multFunction");
                break;

            case "/":
                stringBufOut.append(stringInTextView);
                prepareOperator("divFunction");
                break;

            case "=":
                curOperand = 0;
                stringBufOut.setLength(0);
                if (valOperand2.length() == 0) {
                    //In case of the user clicks on an operator and clicks on equal just after, we have to clone the first operand
                    valOperand2 = valOperand1;
                }
                stringBufOut.append(getResultFromOperatorOnOperands(valOperand1.toString(), valOperand2.toString(), operator.toString()));
                valOperand1 = stringBufOut;
                break;

            case ".":
                // If there is no character already typed before a point we have to put a 0 before the point
                if (stringInTextView.equals("0")) {
                    stringBufOut.append("0");
                    stringBufOut.append(stringIn);
                } else {
                    // There can't be two points in a Double
                    if (!(stringInTextView.contains("."))) {
                        stringBufOut.append(stringInTextView);
                        stringBufOut.append(stringIn);
                    } else {
                        stringBufOut.append(stringInTextView);
                    }
                }
                break;

            default:
                // The default case is for 0..9 buttons / Only requirement, if a 0 has been entered previously we won't show it
                if ((stringInTextView.equals("0")) || (!(takeStringIn))) {
                    stringBufOut.append(stringIn);
                } else {
                    stringBufOut.append(stringInTextView);
                    stringBufOut.append(stringIn);
                }

                takeStringIn = true;

                if (curOperand == 0) {
                    valOperand1 = stringBufOut;
                } else {
                    valOperand2 = stringBufOut;
                }
        }

        return stringBufOut.toString();
    }

    static String getLastOperand() {
        StringBuffer stringBufOut = new StringBuffer("");
        if (curOperand == 0) {
            stringBufOut = valOperand1;
        } else {
            stringBufOut = valOperand2;
        }

        return stringBufOut.toString();
    }

    static String getResultFromOperatorOnOperands(String operand1, String operand2, String operator) {
        double valOperand1 = Double.valueOf(operand1);
        double valOperand2 = Double.valueOf(operand2);
        double valOut = 0;
        boolean errorOnOperation = false;
        String stringOut;
        switch (operator) {
            case "addFunction":
                valOut = valOperand1 + valOperand2;
                break;
            case "minusFunction":
                valOut = valOperand1 - valOperand2;
                break;
            case "multFunction":
                valOut = valOperand1 * valOperand2;
                break;
            case "divFunction":
                if (valOperand2 == 0) {
                    errorOnOperation = true;
                } else {
                    valOut = valOperand1 / valOperand2;
                }
                break;
            default:
                valOut = valOperand1;
        }

        if (errorOnOperation) {
            stringOut = new String("Error can't divide by zero");
        } else {
            stringOut = new String(removeFractionalPartFromDoubleIfNotNecessary(valOut));
        }
        return stringOut;
    }

    static String removeFractionalPartFromDoubleIfNotNecessary(double valIn) {
        double fractionalPart = valIn % 1;
        double integralPart = valIn - fractionalPart;
        int intPart = (int) integralPart;
        String valOut;

        if ((valIn - integralPart) != 0.0) {
            valOut = new String(Double.toString(valIn));
        } else {
            valOut = new String(Integer.toString(intPart));
        }
        return valOut;
    }


    static void prepareOperator(String functionName) {


        // Because we have entered an operator this means that we are entering the second operand
        curOperand = 1;

        operator.setLength(0);
        operator.append(functionName);

        // Put this flag to false will make a reset on the display when the user will enter the next operand
        takeStringIn = false;
    }


}
