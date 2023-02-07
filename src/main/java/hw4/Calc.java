package hw4;


import java.util.Scanner;

/**
 * A program for an RPN calculator that uses a stack.
 */
public final class Calc {

  private final LinkedStack<Integer> stack;
  private final String[] userOptions;
  private final String[] operations;

  /**
   * A class that builds a simple calculator along with its helper methods for use in the command-line app.
   */
  public Calc() {
    stack = new LinkedStack<>();
    userOptions = new String[]{"?", ".", "!"};
    operations = new String[]{"+", "-", "*", "/", "%"};
  }


  public static class DecimalTokenException extends RuntimeException {
    /**
     * An exception that will catch instances where a user attempts to push non-integer values to the stack.
     */
    public DecimalTokenException() {
    }

  }

  //
  public static class InvalidCharException extends RuntimeException {
    /**
     * This exception will catch instances where the user enters a character that will not represent a special option
     * ('.', '?', '!'), an operator, or an integer operand.
     */
    public InvalidCharException() {
    }
  }

  public static class NoOperandsException extends RuntimeException {
    /**
     * This exception catches instances where an operation cannot be completed because there are not enough operands.
     */
    public NoOperandsException() {
    }
  }

  public static class DivideByZeroException extends RuntimeException {
    /**
     * This exception catches instances where the user attempts to divide or do modulus where the denominator is zero.
     */
    public DivideByZeroException() {
    }
  }

  /**
   * This function is indicating whether a String not representing an int is a valid token.
   *
   * @param input represents the current token that is being evaluated from user input
   * @return A float value indicating whether a String not representing an int is a valid token.
   */
  //A float rather than a boolean is being used in this instance because this value will be loaded
  //into the same variable as the float value of a String that does indeed represent an integer. I used
  //a float instead of an integer to distinguish this value from an integer-representing token inputted by the user.
  private float checkUserOptions(String input) {
    for (String userOption : userOptions) {
      if (input.equals(userOption)) {
        return (float) -1.5;
      }
    }
    return (float) -2.5;
  }

  /**
   * Checking if the input truly represents a String or if a number was inputted.
   *
   * @param input represents the current token that is being evaluated from user input
   * @return the value of the integer token or a float representing the type of valid String inputted
   * @throws DecimalTokenException if the token represents a decimal rather than an integer
   */
  private float checkInputType(String input) throws DecimalTokenException {
    try {
      float result = Float.parseFloat(input);//try to parse the token as a float to see if it represents a number
      if (result != Math.round(result)) { //check if the float represents a whole number
        throw new DecimalTokenException();
      } else {
        for (int i = 0; i < input.length(); i++) { //check if the value inputted is a whole decimal like 1.0 or 5.00.
          if ('.' == input.charAt(i)) {
            throw new DecimalTokenException();
          }
        }
        return result;
      }
    } catch (NumberFormatException ex) { //identify non-numeric Strings
      return checkUserOptions(input);
    }
  }

  /**
   * Display the values of either the stack or the value at the top of the stack.
   *
   * @param input represents the current token that is being evaluated from user input, given the
   *              input is a valid user option.
   */
  private void display(String input) {
    if (".".equals(input)) {
      if (stack.empty()) { //ensure that there exists a value to display from the stack
        System.out.println("ERROR: no values provided");
      } else {
        System.out.println(stack.top());
      }
    } else if (!("!".equals(input))) {
      System.out.println(stack);
    }
  }

  /**
   * Determine if a string inputted is an operator or an invalid token.
   *
   * @param input represents the current token that is being evaluated from user input, given the token
   *              is not a user option.
   * @throws InvalidCharException if the token is not an operator.
   */
  private void checkSpecialSymbol(String input) throws InvalidCharException {
    if (input.length() > 1) {
      throw new InvalidCharException();
    }
    for (String operation : operations) {
      if (input.equals(operation)) {
        return;
      }
    }
    throw new InvalidCharException();
  }

  /**
   * Given an inputted token is a valid operator, complete the operation with the top two operands on the stack.
   *
   * @param input represents the operator
   * @param operandA represents the operand on the left-hand side of an infix operation
   * @param operandB represents the operand on the right-hand side of an infix operation
   * @return the integer result of the operation completed
   * @throws DivideByZeroException if the user attempts to divide or do modulus where the denominator is zero.
   */
  private int applyOperator(String input, int operandA, int operandB) throws DivideByZeroException {
    switch (input) {
      case "+": return operandA + operandB;
      case "-": return operandA - operandB;
      case "*": return operandA * operandB;
      case "/":
        if (operandB == 0) {
          throw new DivideByZeroException();
        }
        return operandA / operandB;
      case "%":
        if (operandB == 0) {
          throw new DivideByZeroException();
        }
        return operandA % operandB;
      default:
        return 0;
    }
  }

  /** Catches an error thrown due to a case of an invalid String token given the token
   * does not represent a valid integer.
   *
   * @param input The current token inputted by the user.
   * @return Indicates whether a token is a valid String
   */
  private boolean testInput(String input) {
    try {
      checkSpecialSymbol(input);//function that determines if a string inputted is an operator or an invalid token
      return true;
    } catch (InvalidCharException ex) {
      System.out.println("ERROR: bad token");
      return false;
    }
  }

  /** A function that pushes the result of an operation
   * to the stack or catches an instance of a zero in the denominator.
   *
   * @param input The current String token inputted by the user.
   * @param operandA represents the operand on the left-hand side of an infix operation
   * @param operandB represents the operand on the right-hand side of an infix operation
   */
  private void doArithmetic(String input, int operandA, int operandB) {
    try {
      stack.push(applyOperator(input, operandA, operandB));//applyOperator() completes an operation between the top two
      // values of the stack
    } catch (DivideByZeroException ex) {
      //return to the operands to the stack since operation was not completed
      stack.push(operandA);
      stack.push(operandB);
      System.out.println("ERROR: zero in the denominator");
    }
  }

  /** Removes values from the stack and calls the appropriate operation to complete an operation on them.
   *
   * @param input The current String token inputted by the user.
   * @throws NoOperandsException if there are less than two operands available to satisfy the operation
   */
  private void selectOperator(String input) throws NoOperandsException {
    if (!testInput(input)) { //ensure that values are not popped from the stack if the operand is invalid
      return;
    }
    if (!stack.empty()) {
      int operandB = stack.top(); //store the value of the top element of the stack before removing it from the stack
      stack.pop();
      if (!stack.empty()) { //ensure that the stack had at least two elements so an operation can be completed
        int operandA = stack.top(); //store the value of the top element of the stack before removing it from the stack
        stack.pop();
        doArithmetic(input, operandA, operandB); //push the resulting value from the operation to the stack
      } else {
        stack.push(operandB);
        throw new NoOperandsException(); //an operation cannot be done if there is only one value available
      }
    } else {
      throw new NoOperandsException();//an operation cannot be done if there are no values available
    }
  }

  /** Catches an instance of not enough operands being available and informs the user.
   *
   * @param input The current String token inputted by the user.
   */
  private void checkOperands(String input) {
    try {
      selectOperator(input);
    } catch (NoOperandsException ex) {
      System.out.println("ERROR: less than two operands provided");
    }
  }

  /** Fulfills all the features of the calculator at a high level. Depending on the type of token provided, the
   * calculator will either complete the correct operation, fulfill the correct task, or inform the user that their
   * token or attempted operation is invalid.
   *
   * @param token The current String token inputted by the user.
   */
  private void calculate(String token) {
    try {
      //check if the token represents a number, operator, String command, or invalid token
      float numToken = checkInputType(token);
      //checkInputType returns -1.5 if the value is a String command, -2.5 if the value is a potential operand,
      //or the current float equivalent of a valid integer provided.
      if (numToken == (float) -1.5) {
        display(token);//display either the top of the stack or the whole stack
      } else if (numToken == (float) -2.5) {
        //check to make sure that the operator is valid and that there are two valid operands available so that
        //an operation can be completed and pushed to the stack
        checkOperands(token);
      } else {
        stack.push((int) numToken);
      }
    } catch (DecimalTokenException ex) {
      System.out.println("ERROR: bad token");
    }
  }

  /** Main method.
   *
   * @param args Not used
   */
  public static void main(String[] args) {
    Calc calculator = new Calc();
    Scanner input = new Scanner(System.in);
    String token;
    while (input.hasNext()) {
      //collect the user input while there are tokens being inputted
      token = input.next();
      if ("!".equals(token)) { //end the program if the "!" command is inputted
        break;
      }
      //activate the calculator and complete the appropriate task/print the appropriate error based on the
      //current token being evaluated by the Scanner
      calculator.calculate(token);
    }
  }
}
