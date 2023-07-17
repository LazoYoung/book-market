package com.flylazo.bookmarket.service;

import com.flylazo.bookmarket.domain.Member;
import com.flylazo.bookmarket.repository.MemberRepository;
import com.flylazo.bookmarket.repository.MemoryMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceTest {

    private MemberService service;

    @BeforeEach
    void setup() {
        MemberRepository repository = new MemoryMemberRepository();
        service = new MemberService(repository);
    }

    @Test
    void doValidRegister() {
        Member member = new Member();
        member.setId("user1");
        member.setPassword("mypass#1234");
        member.setHobbies(List.of("reading", "gaming"));
        member.setSex(Member.SEX_FEMALE);
        member.setResidence("Gyeonggi");

        boolean isDuplicate = service.exists(member.getId());
        assertThat(isDuplicate).isFalse();
        service.register(member);
    }
}