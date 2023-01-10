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
@Table(name = "concession_order")
public class ConcessionOrder implements Serializable {
    @Id
    @Column(name = "concession_order_id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concessionOrderId;

    @Column(name = "booking_id")
    private Long booking_id;

    @Column(name = "concessions")
    private String concessions;

    @Column(name = "description")
    private String description;

    @Column(name = "total_concession_price")
    private BigDecimal total_concession_price;

    public ConcessionOrder() {
    }

    public ConcessionOrder(Long concessionOrderId, Long booking_id, String concessions, String description,
            BigDecimal total_concession_price) {
        this.concessionOrderId = concessionOrderId;
        this.booking_id = booking_id;
        this.concessions = concessions;
        this.description = description;
        this.total_concession_price = total_concession_price;
    }

    public Long getConcessionOrderId() {
        return concessionOrderId;
    }

    public void setConcessionOrderId(Long concessionOrderId) {
        this.concessionOrderId = concessionOrderId;
    }

    public Long getConcessionBookingId() {
        return booking_id;
    }

    public void setConcessionBookingId(Long booking_id) {
        this.booking_id = booking_id;
    }

    public String getConcessions() {
        return concessions;
    }

    public void setConcessions(String concessions) {
        this.concessions = concessions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalPrice() {
        return total_concession_price;
    }

    public void setTotalPrice(BigDecimal total_concession_price) {
        this.total_concession_price = total_concession_price;
    }

    @Override
    public String toString() {
        return "Concession [concessionId=" + concessionOrderId + ", bookingId=" + booking_id
                + ", concessions=" + concessions + ", description=" + description + ", totalPrice="
                + total_concession_price + "]";
    }
}