package com.naver.idealproduction.bookmarket.repository;
import com.naver.idealproduction.bookmarket.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class JdbcMemberRepos implements MemberRepository{

    private final DataSource dataSource;

    @Autowired
    public JdbcMemberRepos(DataSource dataSource) {
        this.dataSource = dataSource;
        try {
            Connection con = dataSource.getConnection();
            createTable(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public void createTable(Connection con) throws SQLException {
        String sql = "create table if not exists member(id varchar(255) not null primary key, passwordHash varchar(1000), residence varchar(255), sex int, hobbies varchar(255));";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }

    private String getStringFromList(Optional<List<String>> optionalList) {
        return optionalList.map(list -> String.join(",", list)).orElse(null);
    }

    private List<String> convertStringToList(String str) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, str.split(","));
        return list;
    }

    @Override
    public void add(Member member) {
        String sql = "insert into member(id, passwordHash, residence, sex, hobbies) values(?, ?, ?, ?, ?);";
        Optional<List<String>> optionalList = Optional.ofNullable(member.getHobbies());
        try (Connection con = dataSource.getConnection()){
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, member.getId());
            prst.setString(2, member.getPasswordHash());
            prst.setString(3, member.getResidence());
            prst.setInt(4, member.getSex());
            prst.setString(5, getStringFromList(optionalList));
            prst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Member member) {
        String sql = "delete from member where id = ?;";
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, member.getId());
            prst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Member> getOne(String id) {

        Member member = new Member();
        String sql = "select * from member where id = ?;";

        try (Connection con = dataSource.getConnection()){

            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, id);
            ResultSet resultSet = prst.executeQuery();

            if(resultSet.next()){
                member.setId(resultSet.getString("id"));
                member.setResidence(resultSet.getString("residence"));
                member.setSex(resultSet.getInt("sex"));
                String hobbies = resultSet.getString("hobbies");
                member.setHobbies(convertStringToList(hobbies));
                return Optional.of(member);
            }
            else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matchHash(String id, String hash) {
        String sql = "select passwordHash from member where id = ?;";
        try (Connection con = dataSource.getConnection()){
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, id);
            ResultSet resultSet = prst.executeQuery();
            if(resultSet.next()){
                String realHash = resultSet.getString("passwordHash");
                return hash.equals(realHash);
            }
            else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
