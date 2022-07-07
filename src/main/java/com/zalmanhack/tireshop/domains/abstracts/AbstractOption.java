package com.zalmanhack.tireshop.domains.abstracts;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@MappedSuperclass
@ToString(includeFieldNames = true)
public abstract class AbstractOption {

    public AbstractOption() { }

    public AbstractOption(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(length = 32)
    @Size(min = 1, max = 32)
    private String name;

}
