package com.flylazo.bookmarket.repository;

import com.flylazo.bookmarket.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Repository
public class MemoryBookRepository implements BookRepository {

    private final List<Book> list = new ArrayList<>();

    public MemoryBookRepository() {
        createBooks();
    }

    @Override
    public List<Book> getAll() {
        return list;
    }

    @Override
    public Optional<Book> getOne(String id) {
        return list.stream()
                .filter(e -> (e.getId().equals(id)))
                .findAny();
    }

    @Override
    public List<Book> getByCategory(String category) {
        Predicate<Book> p = (book -> book.getCategory().equals(category));
        return list.stream().filter(p).toList();
    }

    @Override
    public List<Book> getByFilter(Map<String, List<String>> filter) {
        Set<String> filterKeys = filter.keySet();
        List<Book> publisherSet = new ArrayList<>();
        List<Book> categorySet = new ArrayList<>();

        if (filterKeys.contains("publisher")) {
            for (String publisher : filter.get("publisher")) {
                Predicate<Book> p = (e -> e.getPublisher().equals(publisher));
                Stream<Book> stream = list.stream().filter(p);
                publisherSet.addAll(stream.toList());
            }
        } else {
            publisherSet.addAll(list);
        }

        if (filterKeys.contains("category")) {
            for (String category : filter.get("category")) {
                categorySet.addAll(getByCategory(category));
            }
        } else {
            categorySet.addAll(list);
        }

        publisherSet.retainAll(categorySet);
        return publisherSet;
    }

    private void createBooks() {
        Book book1 = new Book("ISBN1234", "C# 교과서", 30000, 473, "박용준",
                """
                        C# 교과서는 생애 첫 프로그래밍 언어로 C#을 시작하는 독자를 대상으로 한다.
                        특히 응용 프로그래머를 위한 C# 입문서로, C#을 사용하여 게임(유니티), 웹, 모바일, IoT 등을
                        개발할 때 필요한 C# 기초 문법을 익히고 기본기를 탄탄하게 다지는 것이 목적이다.
                        """, "길벗", "IT전문서", "2020/05/29", "신품");
        Book book2 = new Book("ISBN1235", "Node.js 교과서", 36000, 512, "조현영",
                """
                        이 책은 프런트부터 서버, 데이터베이스, 배포까지 아우르는 광범위한 내용을 다룬다.
                        군더더기 없는 직관적인 설명으로 기본 개념을 확실히 이해하고, 노드의 기능과 생태계를
                        사용해보면서 실제로 동작하는 서버를 만들어보자. 예제와 코드는 최신 문법을 사용했고
                        실무에 참고하거나 당장 적용할 수 있다.
                        """, "길벗", "IT전문서", "2020/07/25", "신품");
        Book book3 = new Book("ISBN1236", "어도비 XD CC 2020 무작정 따라하기", 17500, 350, "김두한",
                """
                        어도비 XD 시작부터 제대로 배운다. 최신 CC 2020 기능을 수록한 가장 완벽한 입문 활용서.
                        어도비 XD CC 2020 버전 출시에 맞춰 새롭게 선보이는 〈어도비 XD CC 2020 무작정 따라하기〉는
                        사용자가 단 한 권으로, 쉽고 빠르게 어도비 XD를 배울 수 있도록 구성한 책이다.
                        어도비 XD는 모바일 환경과 다양한 콘텐츠의 발달로 UI/UX 디자인을 쉽고 빠르게 구현하고자 만들어진 프로그램으로,
                        〈어도비 XD CC 2020 무작정 따라하기〉는 UI/UX 디자인 작업 전 미리 알아두어야 할 이론을 익히고,
                        기본 기능과 예제를 무작정 따라하여 기초를 탄탄히 쌓을 수 있으며,
                        실무 예제를 따라하여 어도비 XD를 제대로 활용할 수 있도록 도와준다.
                        """, "길벗", "IT활용서", "2019/05/29", "신품");
        list.add(book1);
        list.add(book2);
        list.add(book3);
    }

}
