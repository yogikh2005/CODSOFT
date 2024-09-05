import java.util.Random;
import java.util.Scanner;

public class GuessNoGame{
    private static final int MIN_VAL = 1;
    private static final int MAX_VAL = 100;
    private static final int MAX_ATTEMPTS = 10;
    private static int roundsWon = 0;
    private static int totalAttempts = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        while (true) {
            int number = generateNumber(MIN_VAL, MAX_VAL, random);
            int attempts = playRound(number, scanner);

            if (attempts <= MAX_ATTEMPTS) {
                roundsWon++;
                totalAttempts += attempts;
            } else {
                totalAttempts += MAX_ATTEMPTS;
            }

            System.out.println("Rounds won: " + roundsWon);
            System.out.println("Total attempts: " + totalAttempts);
            System.out.printf("Average attempts per round: %.2f%n", 
                (roundsWon > 0 ? (double) totalAttempts / roundsWon : 0));

            System.out.print("Do you want to play another round? (yes/no): ");
            String playAgain = scanner.next().trim().toLowerCase();
            if (!playAgain.equals("yes")) {
                System.out.println("Thanks for playing!");
                break;
            }
        }
        scanner.close();
    }
////////////////////////////////////////////////////////////////
//
//Fucntion Name:generateNumber
//Description:This function generate random Number and return it 
//Parameter:minimum value and maximum value random object is pass
//Return value: int
//
//////////////////////////////////////////////////////////////////
    private static int generateNumber(int minVal, int maxVal, Random random) {
        return random.nextInt(maxVal - minVal + 1) + minVal;
    }
////////////////////////////////////////////////////////////////
//
//Fucntion Name:playRound
//Description:This function display the guess no bet'n and low ,high value
//Parameter:it have two parameter gusses number and scanner class object
//Return value: int
//
//////////////////////////////////////////////////////////////////
    private static int playRound(int number, Scanner scanner) {
        int attempts = 0;
        System.out.println("Guess the number between " + MIN_VAL + " and " + MAX_VAL + ". You have " + MAX_ATTEMPTS + " attempts.");
        
        while (attempts < MAX_ATTEMPTS) {
            int guess = getUserGuess(scanner);
            attempts++;
            
            if (guess < number) {
                System.out.println("Too low!");
            } else if (guess > number) {
                System.out.println("Too high!");
            } else {
                System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                return attempts;
            }
        }
        
        System.out.println("Sorry, you've used all your attempts. The number was " + number + ".");
        return MAX_ATTEMPTS + 1;
    }
////////////////////////////////////////////////////////////////
//
//Fucntion Name:getUserGuess
//Description:This function get User Gusses number 
//Parameter:scanner class object
//Return value: int
//
//////////////////////////////////////////////////////////////////
    private static int getUserGuess(Scanner scanner) {
        while (true) {
            System.out.print("Enter your guess: ");
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Please enter a valid integer.");
                scanner.next(); // Clear the invalid input
            }
        }
    }
}
