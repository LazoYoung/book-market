package com.naver.idealproduction.bookmarket.domain;

import jakarta.annotation.Nonnull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class Member {

    public static final int SEX_MALE = 0;
    public static final int SEX_FEMALE = 1;
    public static final int SEX_OTHER = 2;

    @Nonnull
    private String id;
    // TODO Storing real password is risky
    private String password = null;
    private String residence = null;
    private int sex = SEX_MALE;
    private List<String> hobbies = Collections.emptyList();

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    private String passwordHash = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(@Nonnull String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }
}
