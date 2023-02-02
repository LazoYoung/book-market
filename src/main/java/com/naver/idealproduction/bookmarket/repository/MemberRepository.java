package com.naver.idealproduction.bookmarket.repository;

import com.naver.idealproduction.bookmarket.domain.Member;

import java.util.Optional;

public interface MemberRepository {

    void add(Member member);
    void remove(Member member);
    Optional<Member> getOne(String id);
    boolean matchHash(String id, String hash);

}
