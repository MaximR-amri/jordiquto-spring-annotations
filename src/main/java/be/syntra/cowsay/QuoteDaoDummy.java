package be.syntra.cowsay;

import org.springframework.stereotype.Component;

public class QuoteDaoDummy implements QuoteDao {
    @Override
    public Quote getRandomQuote() {
        Quote quote = new Quote();
        quote.setId(0);
        quote.setAuthor("dummy author");
        quote.setQuote("dummy quote");
        quote.setLikes(0);
        quote.setDislikes(0);
        return quote;

    }

    @Override
    public void likeQuote(Quote quote) {

    }

    @Override
    public void dislikeQuote(Quote quote) {

    }
}
