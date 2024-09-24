package Service.Implementation;

import Domain.Entity.Component;
import Domain.Entity.Material;
import Domain.Entity.Quote;
import Repository.Implementation.QuoteRepositoryImpl;
import Service.Interface.QuoteService;

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
