package com.naver.idealproduction.bookmarket.controller;

import com.naver.idealproduction.bookmarket.domain.Book;
import com.naver.idealproduction.bookmarket.domain.BookItem;
import com.naver.idealproduction.bookmarket.service.BookService;
import com.naver.idealproduction.bookmarket.service.CartService;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    private final CartService cartService;
    private final BookService bookService;
    private final MemberService memberService;

    @Autowired
    public CartController(
            CartService cartService,
            BookService bookService,
            MemberService memberService
    ) {
        this.cartService = cartService;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    @GetMapping("/cart")
    public String getCart(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        List<BookItem> list = cartService.getBookList(session);
        memberService.supplyModelAttribute(request, model);
        model.addAttribute("bookList", list);
        return "cart";
    }

    @PostMapping("/cart/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCartItem(HttpServletRequest request, @RequestParam("id") String id) {
        if (bookService.getBook(id).isEmpty()) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
        }
        cartService.addBook(request.getSession(), id);
    }

    @PostMapping("/cart/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCartItem(HttpServletRequest request, @RequestParam("id") String id) {
        HttpSession session = request.getSession();
        Optional<Book> book = bookService.getBook(id);

        if (book.isEmpty() || !cartService.hasBook(session, id)) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
        }
        cartService.removeBook(session, id);
    }

}
