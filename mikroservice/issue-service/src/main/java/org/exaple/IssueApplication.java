package org.exaple;


import org.exaple.api.IssueController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.exaple.api.Book;

@SpringBootApplication
public class IssueApplication {
    public static void main(String[] args) {
        SpringApplication.run(IssueApplication.class, args);
    }
}