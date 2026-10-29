package com.codegym.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
@Table(name = "giao_dich")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Transaction {
    @Id
    @Column(name = "ma_giao_dich")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_khach_hang")
    private Customer customer;

    @Column(name = "ngay_giao_dich")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;

    @Column(name = "loai_dich_vu")
    private String serviceType;

    @Column(name = "don_gia")
    private Double price;

    @Column(name = "dien_tich")
    private Double area;
}