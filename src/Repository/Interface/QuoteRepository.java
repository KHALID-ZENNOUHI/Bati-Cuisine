package Repository.Interface;

import Domain.Entity.Quote;

import java.util.List;
import java.util.Optional;

public interface QuoteRepository {
    public Quote save(Quote quote);
    public Quote update(Quote quote);
    public Boolean delete(int id);
    public Optional<Quote> findById(int id);
    public Optional<Quote> findByProjectId(int id);
    public List<Quote> findAll();
}
