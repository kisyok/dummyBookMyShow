package com.dummy.bookmyshow.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "concession")
public class Concession implements Serializable {
    @Id
    @Column(name = "concession_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concessionId = null;

    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    public Concession() {}

    public Concession(Long concessionId, String name, String description, BigDecimal price) {
        this.concessionId = concessionId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getConcessionId() {
        return concessionId;
    }

    public void setConcessionId(Long concessionId) {
        this.concessionId = concessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Concession [concessionId=" + concessionId + ", name=" + name 
                + ", description=" + description + ", price=" + price + "]";
    }
}