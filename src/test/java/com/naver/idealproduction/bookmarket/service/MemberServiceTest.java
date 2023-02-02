package com.naver.idealproduction.bookmarket.service;

import com.naver.idealproduction.bookmarket.domain.Member;
import com.naver.idealproduction.bookmarket.repository.JdbcMemberRepos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceTest {

    private MemberService service;
    private final DataSource dataSource;
    @Autowired // DI 안됨
    MemberServiceTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @BeforeEach
    void setup() {
        JdbcMemberRepos repository = new JdbcMemberRepos(dataSource);
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