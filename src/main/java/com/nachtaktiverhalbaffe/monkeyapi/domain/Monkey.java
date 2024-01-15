package com.nachtaktiverhalbaffe.monkeyapi.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    private String description;

    private String strength;

    private String weaknesses;

    private int attack;

    private int defense;

    private int specialAttack;

    private int specialDefense;

    private int speed;

    private int healthPoints;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "images_name")
    @JsonIdentityReference(alwaysAsId = true)
    private ImageData image;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "species_name")
    private Species species;
}
