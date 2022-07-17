package org.eliseev.libraryproject.dao;

import org.eliseev.libraryproject.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT* FROM person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(full_name, year_of_birth, address) VALUES (?, ?, ?)",
                person.getFullName(),
                person.getYearOfBirth(),
                person.getAddress()
        );
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET full_name=?, year_of_birth=?, address=? WHERE id=?",
                person.getFullName(),
                person.getYearOfBirth(),
                person.getAddress(),
                id
        );
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}
