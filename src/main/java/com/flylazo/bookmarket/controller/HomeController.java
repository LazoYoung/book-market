package com.flylazo.bookmarket.controller;

import com.flylazo.bookmarket.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final MemberService memberService;

    @Autowired
    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String getHome(HttpServletRequest request, Locale locale, Model model) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        String time = dateFormat.format(new Date());

        logger.info("Welcome home! The client locale is {}.", locale);
        memberService.supplyModelAttribute(request, model);
        model.addAttribute("serverTime", time);
        return "home";
    }

}