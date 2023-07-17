package com.flylazo.bookmarket.repository;

import com.flylazo.bookmarket.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private final List<Member> memberList = new ArrayList<>();
    private final Map<String, String> hashMap = new HashMap<>();

    @Override
    public void add(Member member) {
        member.setId(member.getId().toLowerCase());
        memberList.add(member);
    }

    @Override
    public void remove(Member member) {
        memberList.remove(member);
    }

    @Override
    public void addPasswordHash(String id, String hash) {
        hashMap.put(id, hash);
    }

    @Override
    public void removePasswordHash(String id) {
        hashMap.remove(id);
    }

    @Override
    public Optional<Member> getOne(String id) {
        return memberList.stream()
                .filter(m -> m.getId().equalsIgnoreCase(id))
                .findAny();
    }

    @Override
    public boolean matchHash(String id, String hash) {
        return hash.equals(hashMap.get(id));
    }

}
