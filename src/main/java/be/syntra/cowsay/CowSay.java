package be.syntra.cowsay;

public interface CowSay {
    void showQuote();
    void voteUp();
    void voteDown();
    void setQuoteDao(QuoteDao quoteDao);
}

