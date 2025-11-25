package edu.ccrm.domain;

import java.time.LocalDate;

public abstract class Person {
    protected final String id;
    protected String fullName;
    protected String email;
    protected final LocalDate createdAt;

    public Person(String id, String fullName, String email) {
        assert id != null;
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = LocalDate.now();
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public abstract String profile();

    @Override
    public String toString() {
        return fullName + " (" + id + ")";
    }
}
