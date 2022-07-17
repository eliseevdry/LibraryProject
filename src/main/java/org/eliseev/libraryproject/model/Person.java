package org.eliseev.libraryproject.model;

public class Person {
    private int id;
    private String fullName;
    private int yearOfBirth;
    private String address;

    public Person() {
    }

    public Person(int id, String fullName, int yearOfBirth, String address) {
        this.id = id;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
