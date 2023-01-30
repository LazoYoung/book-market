package com.naver.idealproduction.bookmarket.controller;

import com.naver.idealproduction.bookmarket.domain.Book;
import com.naver.idealproduction.bookmarket.service.BookService;
import com.naver.idealproduction.bookmarket.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    private final BookService bookService;
    private final MemberService memberService;

    @Autowired
    public CartController(BookService bookService, MemberService memberService) {
        this.bookService = bookService;
        this.memberService = memberService;
    }

    @GetMapping("/cart")
    public String getCart(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        List<Book> bookList = new ArrayList<>();
        int count = Optional.ofNullable(session.getAttribute("cart.count"))
                .map(obj -> (int) obj)
                .orElse(0);

        for (int i = 1; i <= count; ++i) {
            String id = (String) session.getAttribute("cart.book." + i);
            bookService.getBook(id).ifPresent(bookList::add);
        }

        memberService.supplyModelAttribute(request, model);
        model.addAttribute("bookList", bookList);
        return "cart";
    }

    @PostMapping("/cart/add")
    public void addCartItem(HttpServletRequest request, @RequestParam("id") String id) {
        Optional<Book> book = bookService.getBook(id);

        if (book.isEmpty()) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
        }

        HttpSession session = request.getSession();
        int index = Optional.ofNullable(session.getAttribute("cart.count"))
                .map(obj -> ((int) obj) + 1)
                .orElse(1);
        session.setAttribute("cart.count", index);
        session.setAttribute("cart.book." + index, id);
    }

}
