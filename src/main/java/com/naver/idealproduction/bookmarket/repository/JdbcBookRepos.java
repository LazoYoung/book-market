package com.naver.idealproduction.bookmarket.repository;

import com.naver.idealproduction.bookmarket.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Repository
public class JdbcBookRepos implements BookRepository{

    private final DataSource dataSource;

    @Autowired
    public JdbcBookRepos(DataSource dataSource) {
        this.dataSource = dataSource;
        try {
            Connection con = dataSource.getConnection();
            createTable(con);
            createBooks();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createBooks() {
        Book book1 = new Book("ISBN1234", "C# 교과서", 30000);
        book1.setAuthor("박용준");
        book1.setDescription("""
                        C# 교과서는 생애 첫 프로그래밍 언어로 C#을 시작하는 독자를 대상으로 한다.
                        특히 응용 프로그래머를 위한 C# 입문서로, C#을 사용하여 게임(유니티), 웹, 모바일, IoT 등을
                        개발할 때 필요한 C# 기초 문법을 익히고 기본기를 탄탄하게 다지는 것이 목적이다.
                        """);
        book1.setPublisher("길벗");
        book1.setCategory("IT전문서");
        book1.setStock(473);
        book1.setReleaseDate("2020/05/29");
        Book book2 = new Book("ISBN1235", "Node.js 교과서", 36000);
        book2.setAuthor("조현영");
        book2.setDescription("""
                        이 책은 프런트부터 서버, 데이터베이스, 배포까지 아우르는 광범위한 내용을 다룬다.
                        군더더기 없는 직관적인 설명으로 기본 개념을 확실히 이해하고, 노드의 기능과 생태계를
                        사용해보면서 실제로 동작하는 서버를 만들어보자. 예제와 코드는 최신 문법을 사용했고
                        실무에 참고하거나 당장 적용할 수 있다.
                        """);
        book2.setPublisher("길벗");
        book2.setCategory("IT전문서");
        book2.setStock(512);
        book2.setReleaseDate("2020/07/25");
        Book book3 = new Book("ISBN1236", "어도비 XD CC 2020 무작정 따라하기", 17500);
        book3.setAuthor("김두한");
        book3.setDescription("""
                        어도비 XD 시작부터 제대로 배운다. 최신 CC 2020 기능을 수록한 가장 완벽한 입문 활용서.
                        어도비 XD CC 2020 버전 출시에 맞춰 새롭게 선보이는 〈어도비 XD CC 2020 무작정 따라하기〉는
                        사용자가 단 한 권으로, 쉽고 빠르게 어도비 XD를 배울 수 있도록 구성한 책이다.
                        어도비 XD는 모바일 환경과 다양한 콘텐츠의 발달로 UI/UX 디자인을 쉽고 빠르게 구현하고자 만들어진 프로그램으로,
                        〈어도비 XD CC 2020 무작정 따라하기〉는 UI/UX 디자인 작업 전 미리 알아두어야 할 이론을 익히고,
                        기본 기능과 예제를 무작정 따라하여 기초를 탄탄히 쌓을 수 있으며,
                        실무 예제를 따라하여 어도비 XD를 제대로 활용할 수 있도록 도와준다.
                        """);
        book3.setPublisher("길벗");
        book3.setCategory("IT활용서");
        book3.setStock(350);
        book3.setReleaseDate("2019/05/29");

        saveOne(book1);
        saveOne(book2);
        saveOne(book3);
    }

    private void saveOne(Book book) {
        String sql_select = "select * from book where id = ?;";
        String sql_insert = "insert into book(id, name, price, author, description, publisher, category, stock, releaseDate) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection con = dataSource.getConnection()){
            PreparedStatement prst1 = con.prepareStatement(sql_select);
            prst1.setString(1, book.getId());
            ResultSet rs = prst1.executeQuery();
            if(!rs.next()){
                PreparedStatement prst2 = con.prepareStatement(sql_insert);
                prst2.setString(1, book.getId());
                prst2.setString(2, book.getName());
                prst2.setInt(3, book.getPrice());
                prst2.setString(4, book.getAuthor());
                prst2.setString(5, book.getDescription());
                prst2.setString(6, book.getPublisher());
                prst2.setString(7, book.getCategory());
                prst2.setLong(8, book.getStock());
                prst2.setString(9, book.getReleaseDate());
                prst2.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Book makeBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book(rs.getString("id"), rs.getString("name"), rs.getInt("price"));
        book.setAuthor(rs.getString("author"));
        book.setCategory(rs.getString("category"));
        book.setDescription(rs.getString("description"));
        book.setPublisher(rs.getString("publisher"));
        book.setStock(rs.getLong("stock"));
        book.setReleaseDate(rs.getString("releaseDate"));
        return book;
    }

    private void createTable(Connection con) throws SQLException {
        String sql = "create table if not exists book(id varchar(255) not null primary key, name nvarchar(255), price int, author nvarchar(100), " +
                "description nvarchar(2000), publisher nvarchar(100), category varchar(100), stock bigint, releaseDate varchar(50));";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }

    @Override
    public List<Book> getAll() {
        List<Book> bookList = new ArrayList<>();
        String sql = "select * from book;";
        try (Connection con = dataSource.getConnection()){
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while(resultSet.next()) {
                bookList.add(makeBookFromResultSet(resultSet));
            }
            return bookList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Book> getOne(String id) {
        String sql = "select * from book where id = ?;";
        try (Connection con = dataSource.getConnection()){
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, id);
            ResultSet resultSet = prst.executeQuery();
            if(resultSet.next()) {
                return Optional.of(makeBookFromResultSet(resultSet));
            }
            else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> getByCategory(String category) {
        List<Book> bookList = getAll();
        Predicate<Book> bookPredicate = (book -> book.getCategory().equals(category));
        return bookList.stream().filter(bookPredicate).toList();
    }

    @Override
    public List<Book> getByFilter(Map<String, List<String>> filter) {
        List<Book> bookList = getAll();
        Set<String> filterKeys = filter.keySet();
        List<Book> publisherSet = new ArrayList<>();
        List<Book> categorySet = new ArrayList<>();

        if (filterKeys.contains("publisher")) {
            for (String publisher : filter.get("publisher")) {
                Predicate<Book> p = (e -> e.getPublisher().equals(publisher));
                Stream<Book> stream = bookList.stream().filter(p);
                publisherSet.addAll(stream.toList());
            }
        } else {
            publisherSet.addAll(bookList);
        }

        if (filterKeys.contains("category")) {
            for (String category : filter.get("category")) {
                categorySet.addAll(getByCategory(category));
            }
        } else {
            categorySet.addAll(bookList);
        }

        publisherSet.retainAll(categorySet);
        return publisherSet;
    }
}
