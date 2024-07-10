package org.exaple.api;


import com.github.javafaker.Faker;
import org.exaple.BookProvider;
import org.exaple.ReaderProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/issue")
public class IssueController {
    private final List<Issue> issues;
    private final Faker faker;
    private final BookProvider bookProvider;
    private final ReaderProvider readerProvider;

    public IssueController(BookProvider bookProvider, ReaderProvider readerProvider) {
        this.faker = new Faker();
        this.bookProvider = bookProvider;
        this.readerProvider = readerProvider;
        this.issues = new ArrayList<>();

        refreshData();
    }

    @GetMapping
    public List<Issue> getAll(){
        return issues;
    }

    @GetMapping("/random")
    public Issue getRandom(){
        final int randomIndex = faker.number().numberBetween(0, issues.size());
        return issues.get(randomIndex);
    }

    @GetMapping("/refresh")
    public List<Issue> refresh() {
        refreshData();
        return issues;
    }


     private void refreshData() {
        issues.clear();
        for (int i = 0; i < 16; i++) {
            Issue issue = new Issue();
            issue.setId(UUID.randomUUID());

            Date between = faker.date().between(startOfYear(), endOfYear());
            issue.setIssueAt(between.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            issue.setBook(bookProvider.getRandomBook());
            issue.setReader(readerProvider.getRandomReader());

            issues.add(issue);
        }
    }

    private Date startOfYear() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, 2024);
        instance.set(Calendar.MONTH, 1);
        instance.set(Calendar.DAY_OF_MONTH, 1);
        return instance.getTime();
    }

    private Date endOfYear() {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, 2024);
        instance.set(Calendar.MONTH, 12);
        instance.set(Calendar.DAY_OF_MONTH, 31);
        return instance.getTime();
    }

}