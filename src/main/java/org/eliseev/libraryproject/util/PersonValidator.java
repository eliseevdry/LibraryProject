package org.eliseev.libraryproject.util;

import org.eliseev.libraryproject.dao.PersonDao;
import org.eliseev.libraryproject.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDao personDao;

    @Autowired
    public PersonValidator(PersonDao personDAO) {
        this.personDao = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personDao.show(person.getName()).isPresent()) {
            errors.rejectValue("name", "", "Это имя уже используется");
        }
    }
}
