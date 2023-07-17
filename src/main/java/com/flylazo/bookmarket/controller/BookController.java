package com.flylazo.bookmarket.controller;

import com.flylazo.bookmarket.domain.Book;
import com.flylazo.bookmarket.service.BookService;
import com.flylazo.bookmarket.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BookController {

    private final BookService bookService;
    private final MemberService memberService;

    @Autowired
    public BookController(BookService bookService, MemberService memberService) {
        this.bookService = bookService;
        this.memberService = memberService;
    }

    @GetMapping("/book")
    public String getBookByID(
            @RequestParam String id,
            HttpServletRequest request,
            Model model
    ) {
        Optional<Book> book = bookService.getBook(id);

        if (book.isEmpty()) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("book", book.get());
        memberService.supplyModelAttribute(request, model);
        return "book";
    }

}
