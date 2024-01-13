package com.nachtaktiverhalbaffe.monkeyapi.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "monkeys")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Monkey {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "monkey_id_sequence")
    private Long id;

    private String name;

    private String known_from;

    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "species_id")
    private Species species;
}
