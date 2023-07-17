package com.flylazo.bookmarket.domain;

public class BookItem extends Book {
    private int quantity;

    public BookItem(Book book, int quantity) {
        super(book.getId(), book.getName(), book.getPrice(), book.getStock(),
                book.getAuthor(), book.getDescription(), book.getPublisher(),
                book.getCategory(), book.getReleaseDate(), book.getCondition());
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
