package BatiCuisine.Core;

import BatiCuisine.Domain.Entity.Project;
import BatiCuisine.Domain.Entity.Quote;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class QuoteManagement {
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;

    public QuoteManagement() {
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public Quote createQuote(Project project) {
        System.out.println("--- Project Quotation ---");
        Quote quote = new Quote();
        System.out.print("Enter the issue date (dd/MM/yyyy): ");
        String issueDate = scanner.nextLine();
        LocalDate issueDateFormatted = LocalDate.parse(issueDate, this.dateFormatter);
        quote.setIssueDate(issueDateFormatted);

        System.out.print("Enter the validity date (dd/MM/yyyy): ");
        String validityDate = scanner.nextLine();
        LocalDate validityDateFormatted = LocalDate.parse(validityDate, this.dateFormatter);
        quote.setValidityDate(validityDateFormatted);
        quote.setEstimatedAmount(project.getProfitMargin());
        return quote;
    }
}
