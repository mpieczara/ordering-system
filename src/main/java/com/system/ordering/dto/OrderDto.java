package com.system.ordering.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private String name;
    private String customer;
    private LocalDate date;
}
