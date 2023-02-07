# Discussion

**Document all error conditions you determined and why they are error
 conditions. Do this by including the inputs that you used to test your
  program and what error conditions they exposed:**

1. Attempting to perform a division operation when the right-most operator (the denominator) is zero. 

    Division by zero is undefined. For example, I inputted the following tokens as a test case: "9" "0" "/". 
    Prior to accounting for this error condition, the program would terminate because the compiler throws 
    the following exception: ArithmeticException: / by zero. After accounting for the error, my program 
    does not terminate and the following is printed to System.out: "ERROR: zero in the denominator". 9 and 0
    remain in the stack. 

2. Attempting to perform a modulus operation when the right-most operator (the denominator) is zero.

   Modulus by zero is undefined (it is a division operation). For example, I ran my program and inputted: "1" "0" "%".
   Prior to accounting for this error condition, the program would terminate because the compiler throws
   the following exception: ArithmeticException: / by zero. After accounting for the error, my program
   does not terminate and the following is printed to System.out: "ERROR: zero in the denominator." 1 and 0
   remain in the stack.

3. Attempting to perform any operation when there are not enough operands to complete the operation.
   
    The first instance of this error condition appears when there are no operands in the stack. All operations
    supported by the calculator (addition, subtraction, multiplication, division, and modulus) require two
    operands to successfully perform the operation. For example, I inputted the only following token into my 
    program: "*". Prior to accounting for this error condition, the program would terminate because the LinkedStack
    class throws the following exception: Exception in thread "main" exceptions.EmptyException. This occurs because, 
    in order to perform an operation, you must identify the top two values of the stack. Top() was called even
    though the stack was empty, resulting in an EmptyException. After accounting for the error, my program
    does not terminate and the following is printed to System.out: "ERROR: less than two operands provided."

    The second instance of this error condition appears when there is only one operand in the stack. All operations
    supported by the calculator (addition, subtraction, multiplication, division, and modulus) require two
    operands to successfully perform the operation. For example, I ran my program and inputted "8" "*". Prior to
    accounting for this error condition, the program would terminate because the LinkedStack
    class throws the following exception: Exception in thread "main" exceptions.EmptyException. This occurs because,
    in order to perform an operation, you must identify the top two values of the stack. Top() was called in order
    to acquire the second most recently added value. However, the stack is now empty since the first and only
    value was removed to serve as the first operand. After accounting for the error, my program does not terminate and 
    the following is printed to System.out: "ERROR: less than two operands provided."

4. Attempting to view the top of the stack when there are no values in the stack. 

    When the stack has no values, no top value can possibly be identified and returned to the user. For example, 
    I ran my program and inputted "." Prior to accounting for this error condition, the program would terminate because the LinkedStack
    class throws the following exception: Exception in thread "main" exceptions.EmptyException. This occurs because
    the "." command signals the program to call top() on the stack, which throws an unchecked EmptyException if the
    stack is empty. After accounting for the error, my program does not terminate and the following is printed to System.out:
    "ERROR: no values provided."

5. Attempting to input an invalid String token rather than an approved command or operator.
    
    A string token representing a word, unapproved symbol, or even a nonsense combination of those two is
    an irrelevant and inappropriate input for a calculator. The calculator can only perform arithmetic operations
    on integers and display the integers in the stack. For example, I ran my program and inputted "blah" "bloop" "+".
    Prior to accounting for this error condition, the program would terminate because the compiler outputs the
    following message: "java: incompatible types: java.lang.String cannot be converted to int." This occurs because
    the program attempts to cast the concatenation of "blah" and "bloop" to an int. Even if the "blah" + "bloop"
    successfully executed, the program would try to push "blahbloop" to the stack, which results in the following
    error: "java: incompatible types: java.lang.String cannot be converted to java.lang.Integer". This is because
    a LinkedStack<Integer> rather than a LinkedStack<String> is an attribute of the Calc class. After accounting for the
    error, my program does not terminate and the following is printed to System.out: "ERROR: bad token".

6. Attempting to input a non-integer rather than an integer. 

    The specifications of the calculator require that only integers are accepted. Prior to accounting for this 
    specification, the program would attempt to push a double value resulting from an operation to the stack, 
    even though the stack being manipulated by the Calc object was declared as a LinkedStack<Integer>. 
    For instance, I inputted the following test case "5.0" "10.5" "+". This results in the following error:
    "java: incompatible types: possible lossy conversion from double to int." After accounting for this error
    condition, my program does not terminate and the following is printed to System.out: "ERROR: bad token".
