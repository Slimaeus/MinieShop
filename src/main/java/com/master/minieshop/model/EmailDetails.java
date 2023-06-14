package com.master.minieshop.model;

import lombok.Data;

@Data
public class EmailDetails {
    private String recipient;
    private String content;
    private String subject;
}

