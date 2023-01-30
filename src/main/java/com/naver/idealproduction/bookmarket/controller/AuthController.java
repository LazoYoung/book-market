package com.naver.idealproduction.bookmarket.controller;

import com.naver.idealproduction.bookmarket.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class AuthController {

    private final MemberService memberService;

    @Autowired
    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/register/validate-id")
    @ResponseBody
    public String validateID(@RequestParam("id") String id) {
        boolean valid = !memberService.exists(id);
        return String.valueOf(valid);
    }

    @GetMapping("/register/query-id")
    @ResponseBody
    public String queryID(@RequestParam("id") String id) {
        boolean valid = memberService.exists(id);
        return String.valueOf(valid);
    }

    @GetMapping("/register/query-pwd")
    @ResponseBody
    public String queryPassword(
            @RequestParam("id") String id,
            @RequestParam("password") String password
    ) {
        boolean valid = memberService.matchPassword(id, password);
        return String.valueOf(valid);
    }

    @PostMapping("/logout")
    public String requestLogout(HttpServletRequest request) {
        var session = request.getSession();
        session.removeAttribute("member-id");
        return "redirect:/";
    }

}
