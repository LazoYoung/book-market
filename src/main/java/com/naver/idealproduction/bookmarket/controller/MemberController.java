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
        String id = member.getId();
        String pwd = member.getPassword();

        if (id.length() < 6 || pwd.length() < 6 || service.exists(id)) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
        }

        service.register(member);
        return "redirect:/member/login";
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
        HttpSession session = request.getSession();
        Optional<Member> member = service.getMember(session);

        if (member.isEmpty()) {
            session.setAttribute("member", null);
            return "redirect:/";
        }

        model.addAttribute("member", member.get());
        return "member-profile";
    }

    @PostMapping("/profile")
    public String submitProfileForm(HttpServletRequest request, @ModelAttribute Member member) {
        HttpSession session = request.getSession();
        Object id = session.getAttribute("member-id");
        Optional<Member> pastEntry = service.getMember(member.getId());

        if (id == null || pastEntry.isEmpty()) {
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
        }

        member.setId((String) id);
        service.updateProfile(member);
        return "redirect:/";
    }

}
