package com.flylazo.bookmarket.service;

import com.flylazo.bookmarket.domain.BookItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    private final BookService bookService;
    private final String PATH_LIST = "cart.list";

    @Autowired
    public CartService(BookService bookService) {
        this.bookService = bookService;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<BookItem> getBookList(HttpSession session) {
        ArrayList<BookItem> list = (ArrayList<BookItem>) session.getAttribute(PATH_LIST);
        return (list != null) ? list : new ArrayList<>();
    }

    public boolean hasBook(HttpSession session, String bookId) {
        return getBookList(session).stream().anyMatch(e -> e.getId().equals(bookId));
    }

    public void addBook(HttpSession session, String bookId) {
        var book = bookService.getBook(bookId);

        if (book.isEmpty()) {
            throw new IllegalArgumentException("Unknown book: " + bookId);
        }

        ArrayList<BookItem> list = getBookList(session);
        Optional<BookItem> match = list.stream()
                .filter(e -> e.getId().equals(bookId))
                .findAny();

        if (match.isEmpty()) {
            list.add(new BookItem(book.get(), 1));
        } else {
            var mBook = match.get();
            mBook.setQuantity(mBook.getQuantity() + 1);
        }

        session.setAttribute(PATH_LIST, list);
    }

    public void removeBook(HttpSession session, String bookId) {
        var list = getBookList(session);
        list.removeIf(e -> e.getId().equals(bookId));
        session.setAttribute(PATH_LIST, list);
    }

}
