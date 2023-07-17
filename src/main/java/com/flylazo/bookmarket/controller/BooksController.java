package com.flylazo.bookmarket.controller;

import com.flylazo.bookmarket.domain.Book;
import com.flylazo.bookmarket.service.BookService;
import com.flylazo.bookmarket.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final MemberService memberService;

    @Autowired
    public BooksController(BookService bookService, MemberService memberService) {
        this.bookService = bookService;
        this.memberService = memberService;
    }

    @GetMapping
    public String getBooks(HttpServletRequest request, Model model) {
        List<Book> list = bookService.getAllBooks();
        model.addAttribute("bookList", list);
        memberService.supplyModelAttribute(request, model);
        return "books";
    }

    @GetMapping("/{category}")
    public String getBooksByCategory(
            @PathVariable("category") String category,
            HttpServletRequest request,
            Model model
    ) {
        List<Book> list = bookService.getBooks(category);
        model.addAttribute("bookList", list);
        memberService.supplyModelAttribute(request, model);
        return "books";
    }

    @GetMapping("/filter")
    public String getBooksByFilter(
            @MatrixVariable Map<String, List<String>> filter,
            HttpServletRequest request,
            Model model
    ) {
        List<Book> list = bookService.getBooks(filter);
        model.addAttribute("bookList", list);
        memberService.supplyModelAttribute(request, model);
        return "books";
    }

}
