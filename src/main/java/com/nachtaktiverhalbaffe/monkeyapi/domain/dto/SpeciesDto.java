package com.nachtaktiverhalbaffe.monkeyapi.domain.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpeciesDto {

    private String name;

    private Map<String, String> taxonomy;

    private List<String> locations;

    private Map<String, String> characteristics;

}
