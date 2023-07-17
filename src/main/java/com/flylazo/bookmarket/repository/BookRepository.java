package com.flylazo.bookmarket.repository;

import com.flylazo.bookmarket.domain.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookRepository {

    List<Book> getAll();

    Optional<Book> getOne(String id);

    List<Book> getByCategory(String category);

    List<Book> getByFilter(Map<String, List<String>> filter);

}
