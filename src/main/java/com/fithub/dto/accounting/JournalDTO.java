package com.fithub.dto.accounting;

import com.fithub.model.accounting.Journal;
import lombok.Data;

@Data
public class JournalDTO {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Journal.Type type;
    private AccountDTO account;
}
