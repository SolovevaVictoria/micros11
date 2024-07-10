package org.exaple.api;

import lombok.Data;
import java.util.Date;
import java.time.LocalDate;
import org.exaple.api.Reader;
import java.util.UUID;


@Data
public class Issue {
    private UUID id;
    private LocalDate issueAt;
    private Book book;
    private Reader reader;
}
