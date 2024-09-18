package BatiCuisine.Service.Interface;

import BatiCuisine.Domain.Entity.Quote;

import java.util.List;
import java.util.Optional;

public interface QuoteService {
    public Quote save(Quote quote);
    public Quote update(Quote quote);
    public Boolean delete(int id);
    public Optional<Quote> findById(int id);
    public Optional<Quote> findByProjectId(int id);
    public List<Quote> findAll();
}
