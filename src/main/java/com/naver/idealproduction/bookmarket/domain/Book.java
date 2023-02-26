package com.naver.idealproduction.bookmarket.domain;

import jakarta.annotation.Nonnull;

import java.util.Objects;

@SuppressWarnings("unused")
public class Book {

    private String id;
    private String name;
    private int price;
    private long stock;
    private String author;
    private String description;
    private String publisher;
    private String category;
    private String releaseDate;
    private String condition;

    public Book(@Nonnull String id, String name, int price, long stock, String author, String description, String publisher, String category, String releaseDate, String condition) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.author = author;
        this.description = description;
        this.publisher = publisher;
        this.category = category;
        this.releaseDate = releaseDate;
        this.condition = condition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String date) {
        this.releaseDate = date;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
