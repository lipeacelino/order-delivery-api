package com.example.deliveryapi.entities;

import com.example.deliveryapi.enumerators.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "orders")
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    private Status status;

    private BigDecimal total;
}
