package com.absat.springcourse.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Name field should not be empty")
    private String name;
    @Column(name = "year")
    private int year;
    @Column(name = "author")
    private String author;

    @Column(name = "subscription_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date subscriptionDate;
    @ManyToOne
    @JoinColumn(name = "reader_id", referencedColumnName = "id")
    private Person currentReader;

    @Transient
    private boolean isExpired;

    public Book() {
    }

    public Book(String name, int year, String author) {
        this.name = name;
        this.year = year;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Person getCurrentReader() {
        return currentReader;
    }

    public void setCurrentReader(Person currentReader) {
        this.currentReader = currentReader;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public boolean isExpired() {
        return 10 <= Period.between(Instant.ofEpochMilli(subscriptionDate.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                , LocalDate.now()).getDays();
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", year=" + year +
                ", author='" + author + '\'' +
                ", currentReader=" + currentReader;
    }
}
