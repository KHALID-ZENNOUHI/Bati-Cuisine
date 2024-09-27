package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {

    private final Scanner scanner;

    public InputValidator() {
        this.scanner = new Scanner(System.in);
    }

    public String validateString(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();

            if (input == null || input.trim().isEmpty()) {
                System.out.println("Invalid input! The value cannot be empty.");
            } else {
                return input;
            }
        }
    }

    public int validateInteger(String prompt) {
        int number;
        while (true) {
            System.out.print(prompt);
            try {
                number = scanner.nextInt();
                scanner.nextLine();
                return number;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.next();
            }
        }
    }

    public float validateFloat(String prompt) {
        float number;
        while (true) {
            System.out.print(prompt);
            try {
                number = scanner.nextFloat();
                scanner.nextLine();
                return number;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid float.");
                scanner.next();
            }
        }
    }

    public double validateDouble (String prompt) {
        double number;
        while (true) {
            System.out.print(prompt);
            try {
                number = scanner.nextDouble();
                scanner.nextLine();
                return number;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid double.");
                scanner.next();
            }
        }
    }

    public int validateIntegerInRange(String prompt, int min, int max) {
        int number;
        while (true) {
            System.out.print(prompt);
            try {
                number = scanner.nextInt();
                scanner.nextLine();

                if (number >= min && number <= max) {
                    return number;
                } else {
                    System.out.println("Invalid input! Please enter a number between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.next();
            }
        }
    }

    public Double validateDoubleInRange(String prompt, Double min, Double max) {
        double number;
        while (true) {
            System.out.print(prompt);
            try {
                number = scanner.nextDouble();
                scanner.nextLine();

                if (number >= min && number <= max) {
                    return number;
                } else {
                    System.out.println("Invalid input! Please enter a number between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.next();
            }
        }
    }

    public boolean validateDateFormat(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean validateStartAndEndDate(String startDate, String endDate, DateTimeFormatter dateFormatter) {
        try {
            LocalDate start = LocalDate.parse(startDate, dateFormatter);
            LocalDate end = LocalDate.parse(endDate, dateFormatter);

            return !start.isAfter(end);
        } catch (DateTimeParseException e) {
            return false;
        }
    }



    public void closeScanner() {
        scanner.close();
    }
}
