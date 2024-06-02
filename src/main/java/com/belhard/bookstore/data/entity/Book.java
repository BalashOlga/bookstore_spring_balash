package com.belhard.bookstore.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name ="books")
@Data
public class Book {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "year")
    private Integer year;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "covertypes_id")
    private CoverType coverType;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;
}

