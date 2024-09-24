package Core;

import Domain.Entity.Project;
import Domain.Entity.Quote;
import Domain.Enum.ProjectStatus;
import Service.Implementation.ProjectServiceImpl;
import Service.Implementation.QuoteServiceImpl;
import Util.InputValidator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class QuoteManagement {
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter;
    private final InputValidator validator;
    private final QuoteServiceImpl quoteService;
    private final ProjectServiceImpl projectService;

    public QuoteManagement() throws SQLException {
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.validator = new InputValidator();
        this.quoteService = new QuoteServiceImpl();
        this.projectService = new ProjectServiceImpl();
    }

    public Quote createQuote(Project savedProject) {
        System.out.println("--- Project Quotation ---");
        Quote quote = new Quote();

        String issueDate, validityDate;
        LocalDate issueDateFormatted = null, validityDateFormatted = null;


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
            if (!this.validator.validateDateFormat(validityDate)) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            } else if (!this.validator.validateStartAndEndDate(issueDate, validityDate, this.dateFormatter)) {
                System.out.println("The validity date must be after the issue date.");
            } else {
                validityDateFormatted = LocalDate.parse(validityDate, this.dateFormatter);
            }
        } while (validityDateFormatted == null);

        quote.setValidityDate(validityDateFormatted);
        quote.setProject(savedProject);
        quote.setEstimatedAmount(savedProject.getTotalCost());
        String saveQuoteDecision;
        do {
            saveQuoteDecision = validator.validateString("Do you accept this Quote (y/n) ?  ");
        } while (!saveQuoteDecision.equalsIgnoreCase("y") && !saveQuoteDecision.equalsIgnoreCase("n"));

        if (saveQuoteDecision.equalsIgnoreCase("y")) {
            quote.setAccepted(true);
            this.quoteService.save(quote);
            System.out.println("Project saved successfully!");
        } else {
            quote.setAccepted(false);
            savedProject.setProjectStatus(ProjectStatus.suspended);
            this.projectService.update(savedProject);
            this.quoteService.save(quote);
            System.out.println("Quote is canceled!");
        }
        return quote;
    }

}
