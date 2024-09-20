package BatiCuisine.Repository.Implementation;

import BatiCuisine.Config.DataBaseConnection;
import BatiCuisine.Domain.Entity.Quote;
import BatiCuisine.Domain.Entity.Project;  // Importing Project
import BatiCuisine.Repository.Interface.QuoteRepository;
import BatiCuisine.Service.Implementation.ProjectServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuoteRepositoryImpl implements QuoteRepository {

    private Connection connection;
    private ProjectServiceImpl projectService;

    public QuoteRepositoryImpl() throws SQLException {
        this.connection = DataBaseConnection.getInstance().getConnection();
        this.projectService = new ProjectServiceImpl();
    }

    @Override
    public Quote save(Quote quote) {
        String query = "INSERT INTO quote (estimated_amount, issue_date, validity_date, accepted, project_id) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, quote.getEstimatedAmount());
            preparedStatement.setDate(2, Date.valueOf(quote.getIssueDate()));
            preparedStatement.setDate(3, Date.valueOf(quote.getValidityDate()));
            preparedStatement.setBoolean(4, quote.getAccepted());
            preparedStatement.setInt(5, quote.getProject().getId());
            preparedStatement.executeUpdate();
            return quote;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Quote update(Quote quote) {
        String query = "UPDATE quote SET estimated_amount = ?, issue_date = ?, validity_date = ?, accepted = ?, project_id = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, quote.getEstimatedAmount());
            preparedStatement.setDate(2, Date.valueOf(quote.getIssueDate()));
            preparedStatement.setDate(3, Date.valueOf(quote.getValidityDate()));
            preparedStatement.setBoolean(4, quote.getAccepted());
            preparedStatement.setInt(5, quote.getProject().getId());
            preparedStatement.setInt(6, quote.getId());
            preparedStatement.executeUpdate();
            return quote;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean delete(int id) {
        String query = "DELETE FROM quote WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Quote> findByProjectId(int projectId) {
        String query = "SELECT * FROM quote WHERE project_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Quote(
                        resultSet.getInt("id"),
                        resultSet.getDouble("estimated_amount"),
                        resultSet.getDate("issue_date").toLocalDate(),
                        resultSet.getDate("validity_date").toLocalDate(),
                        resultSet.getBoolean("accepted"),
                        this.projectService.findById(resultSet.getInt("project_id")).get()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Quote> findById(int id) {
        String query = "SELECT * FROM quote WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Quote(
                        resultSet.getInt("id"),
                        resultSet.getDouble("estimated_amount"),
                        resultSet.getDate("issue_date").toLocalDate(),
                        resultSet.getDate("validity_date").toLocalDate(),
                        resultSet.getBoolean("accepted"),
                        this.projectService.findById(resultSet.getInt("project_id")).get()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Quote> findAll() {
        String query = "SELECT * FROM quote";
        List<Quote> quotes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                quotes.add(new Quote(
                        resultSet.getInt("id"),
                        resultSet.getDouble("estimated_amount"),
                        resultSet.getDate("issue_date").toLocalDate(),
                        resultSet.getDate("validity_date").toLocalDate(),
                        resultSet.getBoolean("accepted"),
                        this.projectService.findById(resultSet.getInt("project_id")).get()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quotes;
    }
}
