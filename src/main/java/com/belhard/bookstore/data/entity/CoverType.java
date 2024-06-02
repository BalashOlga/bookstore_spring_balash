package com.belhard.bookstore.data.entity;

import jakarta.persistence.Table;

import javax.persistence.Entity;

@Entity
@Table(name = "covertypes")
public enum CoverType {
    HARD, SOFT, HINGED, SPRING;
}
