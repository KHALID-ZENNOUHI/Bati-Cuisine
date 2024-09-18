package BatiCuisine.Service.Implementation;

import BatiCuisine.Domain.Entity.Component;
import BatiCuisine.Domain.Entity.Material;
import BatiCuisine.Domain.Entity.Quote;
import BatiCuisine.Repository.Implementation.QuoteRepositoryImpl;
import BatiCuisine.Service.Interface.QuoteService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class QuoteServiceImpl implements QuoteService {
    private final QuoteRepositoryImpl quoteRepository;

    public QuoteServiceImpl() throws SQLException {
        this.quoteRepository = new QuoteRepositoryImpl();
    }

    @Override
    public Quote save(Quote quote) {
        return this.quoteRepository.save(quote);
    }

    @Override
    public Quote update(Quote quote) {
        return this.quoteRepository.update(quote);
    }

    @Override
    public Boolean delete(int id) {
        return this.quoteRepository.delete(id);
    }

    @Override
    public Optional<Quote> findById(int id) {
        return this.quoteRepository.findById(id);
    }

    @Override
    public List<Quote> findAll() {
        return this.quoteRepository.findAll();
    }
     @Override
    public Optional<Quote> findByProjectId(int id) {
        return this.quoteRepository.findByProjectId(id);
    }
}
