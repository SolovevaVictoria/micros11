package org.exaple.api;

import lombok.Data;

import java.util.UUID;


@Data
public class Reader {
    private UUID id;
    private String firstName;
    private String lastName;
}
