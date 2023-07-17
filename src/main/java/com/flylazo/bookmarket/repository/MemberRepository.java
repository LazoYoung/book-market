package com.flylazo.bookmarket.repository;

import com.flylazo.bookmarket.domain.Member;

import java.util.Optional;

public interface MemberRepository {

    void add(Member member);
    void remove(Member member);
    void addPasswordHash(String id, String hash);
    void removePasswordHash(String id);
    Optional<Member> getOne(String id);
    boolean matchHash(String id, String hash);

}
