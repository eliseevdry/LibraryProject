package org.eliseev.libraryproject.dao;

import org.eliseev.libraryproject.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT* FROM book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, year) VALUES (?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getYear()
        );
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET person_id=?, title=?, author=?, year=? WHERE id=?",
                book.getPersonId(),
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                id
        );
    }

    public void setPerson(int id, Integer personId) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", personId, id);
    }

    public List<Book> personBook(Integer personId) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?", new Object[]{personId}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public void batchSave(List<Book> books) {
        jdbcTemplate.batchUpdate("INSERT INTO book(title, author, year) VALUES (?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, books.get(i).getTitle());
                ps.setString(2, books.get(i).getAuthor());
                ps.setInt(3, books.get(i).getYear());
            }

            @Override
            public int getBatchSize() {
                return books.size();
            }
        });
    }


}
