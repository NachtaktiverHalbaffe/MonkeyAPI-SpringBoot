package com.nachtaktiverhalbaffe.monkeyapi.domain;

import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "species")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Species {

    @Id
    @Column(length = 50)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyJoinColumn(name = "TAXONOMY_ENTRY")
    private Map<String, String> taxonomy;

    private List<String> locations;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyJoinColumn(name = "CHARACTERISTICS_ENTRY")
    private Map<String, String> characteristics;

}
