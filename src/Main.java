import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String[] splitUserInput;
        String rawUserInput;
        Scanner scanner = new Scanner(System.in);

        final String FIB_COMMAND = "-fib";
        final String FAC_COMMAND = "-fac";
        final String E_COMMAND   = "-e";


        System.out.println("""
                Welcome to the program.
                Enter one or more of the following commands followed by the number you wish to be associated
                with that command. Split all arguments with a space.
                If you wish to quit, type 'q'.""");

        printHelp();

        while(true){
            System.out.println("Enter commands:");

            rawUserInput = scanner.nextLine();

            //Check if user wants to quit.
            if(rawUserInput.equalsIgnoreCase("q")){
                System.out.println("Quitting...");
                break;
            }

            //Splitting user input by space.
            splitUserInput = rawUserInput.split(" ");

            int argCount = splitUserInput.length;
            for (int i = 0; i < splitUserInput.length; i+=2){
                if(argCount < 2){
                    System.out.println("Latest command not provided in format specified.");
                    break;
                }
                argCount -= 2;

                String potentialNumber = splitUserInput[i+1];

                Integer convertedNumber = stringToInteger(potentialNumber);
                if (convertedNumber == null) {
                    printCannotConvertToInteger(potentialNumber);
                    continue;
                }

                switch (splitUserInput[i]) {
                    case FIB_COMMAND -> {

                        Integer fibNumber = getFib(convertedNumber);
                        if (fibNumber == null){
                            printFibError();
                            continue;
                        }

                        System.out.printf("Fibonacci of %s is %d.\n", potentialNumber, fibNumber);
                        continue;
                    }
                    case FAC_COMMAND -> {

                        BigInteger facNumber = getFac(convertedNumber);
                        if (facNumber == null){
                            printFacError();
                            continue;
                        }

                        System.out.printf("Factorial Result of %s is %s.\n", potentialNumber, facNumber);
                        continue;
                    }
                    case E_COMMAND -> {

                        BigDecimal eNumber = getE(convertedNumber);
                        if(eNumber == null){
                            printEError();
                            continue;
                        }

                        System.out.printf("E Number Result of %s is %s.\n", potentialNumber, eNumber);
                        continue;
                    }
                }


                System.out.println("Unknown command entered.\n");
                printHelp();
                break;
            }
        }

        scanner.close();
    }

    public static Integer stringToInteger(String stringNumber){
        try{
            return Integer.parseInt(stringNumber);
        }catch(Exception e){
            return null;
        }
    }
    /**
     * Prints the commands to screen.
     **/
    public static void printHelp(){
        System.out.println(
                """
                        Commands:\s
                          -fib [n] : Compute the Fibonacci of [n]; valid range [0, 40]
                          -fac [n] : Compute the factorial of [n]; valid range, [0, 2147483647]
                          -e [n] : Compute the value of 'e' using [n] iterations; valid range [1, 2147483647]
                          """
        );
    }

    /**
     *
     * */
    public static void printCannotConvertToInteger(String potentialNumber){
        System.out.printf("%s is not a number. Skipping.\n", potentialNumber);
    }

    /**
     * Prints the range allowed for Fibonacci numbers.
     * */
    public static void printFibError(){
        System.out.println("Fibonacci Error: Range is [0, 40].");
    }
    /**
     * Prints the range allowed for Factorial numbers.
     * */
    public static void printFacError(){
        System.out.printf("Factorial Error: Number must be between 0 and %d.\n", Integer.MAX_VALUE);
    }
    /**
     * Prints the range allowed for E.
     * */
    public static void printEError(){
        System.out.printf("E Number Error: Valid e iterations range is [1, %d].\n", Integer.MAX_VALUE);
    }

    private static boolean validIntegerSize(Integer n) {
        BigInteger maxInt = BigInteger.valueOf(Integer.MAX_VALUE);
        BigInteger bigIntPotentialNumber;
        try{
            bigIntPotentialNumber = BigInteger.valueOf(n);
        }catch(Exception exception){
            return false;
        }

        if(bigIntPotentialNumber.compareTo(maxInt) > 0) {
            return false;
        }
        return true;
    }


    /**
     * A method which returns the nth fibonacci number, where n is the parameter provided.
     */
    public static Integer getFib(Integer n){
        //Check range of given number
        //If given number is too large return null.
        if (n < 1 || n > 40) {
            return null;
        }

        int firstNum = 0;
        int secondNum = 1;
        int tempNum;

        for(int i = 1; i < n; i++){
            tempNum = firstNum + secondNum;
            firstNum = secondNum;
            secondNum = tempNum;

        }

        return secondNum;
    }
    /**
     * A method which returns the factorial of n, where n is the parameter provided.
     **/
    public static BigInteger getFac(Integer n){
        if (!validIntegerSize(n)) return null;

        BigInteger result = BigInteger.ONE;

        for(int i = 1; i <= n; i++)
        {
            result = result.multiply(BigInteger.valueOf(i));
        }

        return result;
    }
    /**
     * Returns an estimate of e based on n terms, where n is the provided parameter.
     **/
    public static BigDecimal getE(Integer n){

        if(!validIntegerSize(n)) return null;

        BigDecimal e = BigDecimal.valueOf(1);
        MathContext mc = new MathContext(20);

        for(int i = 1; i <= n; i++){
            BigInteger tempBigInt = getFac(i);
            BigDecimal factorialDecimal = new BigDecimal(tempBigInt);

            BigDecimal currentTermCalculation = BigDecimal.ONE.divide(factorialDecimal, mc);
            e = e.add(currentTermCalculation);
        }

        return e;
    }
}