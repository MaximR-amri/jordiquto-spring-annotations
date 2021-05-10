package be.syntra.cowsay;

public interface QuoteDao {
    Quote getRandomQuote();
    void likeQuote(Quote quote);
    void dislikeQuote(Quote quote);

}
