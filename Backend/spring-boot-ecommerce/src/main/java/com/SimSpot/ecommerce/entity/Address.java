package com.SimSpot.ecommerce.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="address")
@Getter
@Setter

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="city")
    private String city;

    @Column(name="country")
    private String country;

    @Column(name="county")
    private String county;

    @Column(name="post_code")
    private String postCode;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Order order;
}