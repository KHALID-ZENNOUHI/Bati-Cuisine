package BatiCuisine.Core;

import BatiCuisine.Domain.Entity.Project;
import BatiCuisine.Domain.Entity.Quote;
import BatiCuisine.Util.InputValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class QuoteManagement {
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;
    private InputValidator validator;

    public QuoteManagement() {
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.validator = new InputValidator();
    }

    public Quote createQuote() {
        System.out.println("--- Project Quotation ---");
        Quote quote = new Quote();

        String issueDate, validityDate;
        LocalDate issueDateFormatted = null, validityDateFormatted = null;

        InputValidator validator = new InputValidator();

        do {
            System.out.print("Enter the issue date (dd/MM/yyyy): ");
            issueDate = scanner.nextLine();
            if (!validator.validateDateFormat(issueDate)) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            } else {
                issueDateFormatted = LocalDate.parse(issueDate, this.dateFormatter);
            }
        } while (issueDateFormatted == null);

        quote.setIssueDate(issueDateFormatted);

        do {
            System.out.print("Enter the validity date (dd/MM/yyyy): ");
            validityDate = scanner.nextLine();
            if (!validator.validateDateFormat(validityDate)) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            } else if (!validator.validateStartAndEndDate(issueDate, validityDate, this.dateFormatter)) {
                System.out.println("The validity date must be after the issue date.");
            } else {
                validityDateFormatted = LocalDate.parse(validityDate, this.dateFormatter);
            }
        } while (validityDateFormatted == null);

        quote.setValidityDate(validityDateFormatted);

        return quote;
    }

}
