package com.naver.idealproduction.bookmarket.service;

import com.naver.idealproduction.bookmarket.domain.Member;
import com.naver.idealproduction.bookmarket.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository repository;

    @Autowired
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Optional<Member> getMember(HttpSession session) {
        return Optional.ofNullable(session.getAttribute("member-id"))
                .flatMap(obj -> repository.getOne((String) obj));
    }

    public Optional<Member> getMember(String id) {
        return repository.getOne(id);
    }

    public void supplyModelAttribute(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Member member = getMember(session).orElse(null);
        model.addAttribute("member", member);
    }

    public void register(Member member) {
        repository.add(member);
        String password = member.getPassword();
        String hash = produceHash(password);
        repository.addPasswordHash(member.getId(), hash);
    }

    public void updateProfile(Member member) {
        String id = member.getId();
        Member pastEntry = repository.getOne(id).orElse(null);

        if (pastEntry == null) {
            throw new IllegalArgumentException("Member not registered: " + member.getId());
        }

        if (member.getPassword().isBlank()) {
            member.setPassword(pastEntry.getPassword());
        } else {
            String hash = produceHash(member.getPassword());
            repository.removePasswordHash(id);
            repository.addPasswordHash(id, hash);
        }

        repository.remove(pastEntry);
        repository.add(member);
    }

    public boolean exists(String id) {
        Optional<Member> redundant = repository.getOne(id);
        return redundant.isPresent();
    }

    public String produceHash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512/256");
            byte[] bytes = password.getBytes(StandardCharsets.UTF_8);
            byte[] digest = md.digest(bytes);
            BigInteger bigInt = new BigInteger(1, digest);
            StringBuilder builder = new StringBuilder(bigInt.toString(16));

            while (builder.length() < 64) {
                builder.insert(0, '0');
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean matchPassword(String id, String password) {
        String hash = produceHash(password);
        return repository.matchHash(id, hash);
    }

}
