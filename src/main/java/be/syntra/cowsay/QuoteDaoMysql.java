package be.syntra.cowsay;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class QuoteDaoMysql implements QuoteDao {
    private static String url;
    private static String usr;
    private static String pass;

    static {
        try (InputStream in = QuoteDaoMysql.class.getResourceAsStream("/app.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            url = properties.getProperty("url");
            usr = properties.getProperty("usr");
            pass = properties.getProperty("pass");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Quote getRandomQuote() {
        String sql = "SELECT * FROM jordiquotes ORDER BY RAND() LIMIT 1";
        try (Connection con = DriverManager.getConnection(url, usr, pass);
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Quote quote = new Quote();
                    quote.setId(rs.getInt("id"));
                    quote.setAuthor(rs.getString("author"));
                    quote.setQuote(rs.getString("quote"));
                    quote.setLikes(rs.getInt("likes"));
                    quote.setDislikes(rs.getInt("dislikes"));
                    return quote;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void likeQuote(Quote quote) {
            String sql = "UPDATE jordiquotes SET likes = likes + 1 WHERE ID = ?";
            try (Connection con = DriverManager.getConnection(url,usr,pass);
                 PreparedStatement ps = con.prepareStatement(sql)){
                ps.setInt(1, quote.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void dislikeQuote(Quote quote) {
        String sql = "UPDATE jordiquotes SET dislikes = dislikes + 1 WHERE ID = ?";
        try (Connection con = DriverManager.getConnection(url,usr,pass);
             PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, quote.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
