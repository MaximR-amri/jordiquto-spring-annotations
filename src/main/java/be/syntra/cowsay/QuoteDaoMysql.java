package be.syntra.cowsay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

@Component
public class QuoteDaoMysql implements QuoteDao {
    @Value("${db.url}")
    private String url;
    @Value("${db.usr}")
    private String usr;
    @Value("${db.pass}")
    private String pass;



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
