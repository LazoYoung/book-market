package com.flylazo.bookmarket.service;

import com.flylazo.bookmarket.domain.Book;
import com.flylazo.bookmarket.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAllBooks() {
        return repository.getAll();
    }

    public List<Book> getBooks(String category) {
        return repository.getByCategory(category);
    }

    public List<Book> getBooks(Map<String, List<String>> filter) {
        return repository.getByFilter(filter);
    }

    public Optional<Book> getBook(String id) {
        return repository.getOne(id);
    }

}
