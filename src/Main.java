import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //The various commands users can enter in.
        final String FIB_COMMAND = "-fib";
        final String FAC_COMMAND = "-fac";
        final String E_COMMAND   = "-e";

        Scanner scanner = new Scanner(System.in);


        System.out.println("""
                Welcome to the program.
                Enter one or more of the following commands followed by the number you wish to be associated
                with that command. Split all arguments with a space.
                If you wish to quit, type 'q'.""");

        printHelp();

        while(true){
            System.out.println("Enter commands:");

            //Holds the user input which is split by space.
            String[] splitUserInput;
            //Holds the raw user input.
            String rawUserInput;

            rawUserInput = scanner.nextLine();

            //Check if user wants to quit.
            if(rawUserInput.equalsIgnoreCase("q")){
                System.out.println("Quitting...");
                break;
            }

            //Splitting user input by space.
            splitUserInput = rawUserInput.split(" ");

            //How many individual "words" there are.
            int argCount = splitUserInput.length;

            //Each command must be paired with a number. If there are too few arguments, then the command cannot be
            //executed successfully.
            for (int i = 0; i < splitUserInput.length; i+=2){
                if(argCount < 2){
                    System.out.println("Latest command not provided in format specified.");
                    printHelp();
                    break;
                }
                argCount -= 2;

                //The command.
                String command = splitUserInput[i];
                //The number associated with the given command.
                String potentialNumber = splitUserInput[i+1];

                Integer convertedNumber;
                //Checking if the given number is within the bounds of an Integer.
                if(!validIntegerSize(potentialNumber)){
                    System.out.printf("Number given must be between %d and %d.\n", Integer.MIN_VALUE, Integer.MAX_VALUE);
                    continue;
                } else {
                    convertedNumber = Integer.parseInt(potentialNumber);
                }

                switch (command) {
                    case FIB_COMMAND -> {

                        Integer fibNumber = getFib(convertedNumber);
                        if (fibNumber == null){
                            System.out.println("Fibonacci Error: Range is [0, 40].");
                            continue;
                        }

                        System.out.printf("Fibonacci of %s is %d.\n", potentialNumber, fibNumber);
                        continue;
                    }
                    case FAC_COMMAND -> {

                        BigInteger facNumber = getFac(convertedNumber);
                        if (facNumber == null){
                            System.out.printf("Factorial Error: Number must be between [0 and %d].\n", Integer.MAX_VALUE);
                            continue;
                        }

                        System.out.printf("Factorial Result of %s is %s.\n", potentialNumber, facNumber);
                        continue;
                    }
                    case E_COMMAND -> {

                        BigDecimal eNumber = getE(convertedNumber);
                        if(eNumber == null){
                            System.out.printf("E Number Error: Valid e iterations range is [1, %d].\n", Integer.MAX_VALUE);
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

    /**
     * Prints the help to screen.
     * @author Adam Sinclair
     **/
    private static void printHelp(){
        System.out.println(
                """
                        --- Assign 1 Help ---\s
                          -fib [n] : Compute the Fibonacci of [n]; valid range [0, 40]
                          -fac [n] : Compute the factorial of [n]; valid range, [0, 2147483647]
                          -e [n] : Compute the value of 'e' using [n] iterations; valid range [1, 2147483647]
                          """
        );
    }

    /**
     * Checks if a given String can be:
     * 1: Converted to an Integer.
     * 2: If the converted String's value is within the restrictions of the Integer type.
     * @author Adam Sinclair
     * */
    private static boolean validIntegerSize(String n) {
        if (n == null) return false;

        BigInteger maxInt = BigInteger.valueOf(Integer.MAX_VALUE);
        BigInteger minInt = BigInteger.valueOf(Integer.MIN_VALUE);
        BigInteger bigIntPotentialNumber;
        //If the provided String cannot be converted to a numerical data type, then it cannot become a valid Integer.
        try{
            bigIntPotentialNumber = BigInteger.valueOf(Long.parseLong(n));
        }catch(Exception exception){
            return false;
        }

        //If the converted number is greater than the maximum value of an Integer, it is not valid.
        if(bigIntPotentialNumber.compareTo(maxInt) > 0) {
            return false;
        }

        //If the converted number is smaller than the minimum value of an Integer, it is not valid.
        if(bigIntPotentialNumber.compareTo(minInt) < 0){
            return false;
        }

        return true;
    }


    /**
     * A method which returns the nth fibonacci number, where n is the Integer provided.
     * @author Adam Sinclair
     */
    private static Integer getFib(Integer n){
        if (n == null) return null;

        //Check range of given number
        //If given number is too large return null.
        if (n < 0 || n > 40) {
            return null;
        }

        int firstNum = 0;
        int secondNum = 1;
        int tempNum;

        //Performing the fibonacci additions.
        for(int i = 1; i < n; i++){
            tempNum = firstNum + secondNum;
            firstNum = secondNum;
            secondNum = tempNum;

        }

        return secondNum;
    }

    /**
     * A method which returns the factorial of n, where n is the Integer provided.
     * @author Adam Sinclair
     **/
    private static BigInteger getFac(Integer n){
        if (n == null) return null;
        if(n < 0) return null;

        BigInteger result = BigInteger.ONE;

        for(int i = 1; i <= n; i++)
        {
            result = result.multiply(BigInteger.valueOf(i));
        }

        return result;
    }

    /**
     * Returns an estimate of e based on n terms, where n is the Integer parameter.
     * @author Adam Sinclair
     **/
    private static BigDecimal getE(Integer n){
        if(n == null) return null;
        if(n < 1) return null;

        BigDecimal e = BigDecimal.valueOf(1);
        MathContext mc = new MathContext(10);

        for(int i = 1; i <= n; i++){
            BigInteger tempBigInt = getFac(i);
            BigDecimal factorialDecimal = new BigDecimal(tempBigInt);

            BigDecimal currentTermCalculation = BigDecimal.ONE.divide(factorialDecimal, mc);
            currentTermCalculation = currentTermCalculation.setScale(16, RoundingMode.HALF_UP);

            e = e.add(currentTermCalculation);
        }

        return e;
    }
}