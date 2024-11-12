package com.pdasilem.resourceservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "resource_table")
@Getter
@Setter
public class Mp3Model {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resource_table_seq")
    @SequenceGenerator(name = "resource_table_seq", sequenceName = "resource_table_seq", allocationSize = 1)
    Integer id;
    byte[] data;
}
