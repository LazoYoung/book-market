package com.naver.idealproduction.bookmarket.controller;

import com.naver.idealproduction.bookmarket.domain.Member;
import com.naver.idealproduction.bookmarket.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService service;

    @Autowired
    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String getRegisterForm() {
        return "member-register";
    }

    @PostMapping("/register")
    public String submitRegisterForm(@ModelAttribute Member member) {
        service.register(member);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "member-login";
    }

    @PostMapping("/login")
    public String submitLoginForm(
            HttpServletRequest request,
            @RequestParam("id") String id,
            @RequestParam("password") String password
    ) {
        if (!service.exists(id)) {
            throw new ErrorResponseException(HttpStatus.NOT_FOUND);
        }

        if (!service.matchPassword(id, password)) {
            throw new ErrorResponseException(HttpStatus.UNAUTHORIZED);
        }

        HttpSession session = request.getSession();
        session.setAttribute("member-id", id);
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String getProfile(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        Member member = null;

        if (session != null) {
            member = service.getMember(session).orElse(null);
        }

        if (member == null) {
            if (session != null) {
                session.invalidate();
            }
            return "redirect:/";
        }

        model.addAttribute("member", member);
        return "member-profile";
    }

    @PostMapping("/profile")
    public String submitProfileForm(HttpServletRequest request, @ModelAttribute Member member) {
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("member-id");
        Optional<Member> pastEntry = service.getMember(member.getId());
        member.setId(id);

        if (pastEntry.isEmpty()) {
            throw new ErrorResponseException(HttpStatus.GONE);
        }

        service.updateProfile(member);
        return "redirect:/";
    }

}
