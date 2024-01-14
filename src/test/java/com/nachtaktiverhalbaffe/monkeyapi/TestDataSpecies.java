package com.nachtaktiverhalbaffe.monkeyapi;

import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;
import com.nachtaktiverhalbaffe.monkeyapi.domain.dto.SpeciesDto;

public class TestDataSpecies {

        private TestDataSpecies() {

        }

        public static Species createTestSpecies() {
                return Species.builder()
                                .characteristics(Map.ofEntries(
                                                entry("prey", "Gazelle, Wildebeest, Hare"),
                                                entry("name_of_young", "Cub"),
                                                entry("group_behavior", "Solitary/Pairs"),
                                                entry("estimated_population_size", "8.500"),
                                                entry("biggest_threat", "Habitat loss"),
                                                entry("most_distinctive_feature",
                                                                "Yellowish fur covered in small black spots"),
                                                entry("gestation_period", "90 days"),
                                                entry("habitat", "Open grassland"),
                                                entry("diet", "carnivore"),
                                                entry("average_litter_size", "3"),
                                                entry("lifestyle", "Diurnal"),
                                                entry("common_name", "Cheetah"),
                                                entry("number_of_species", "5"),
                                                entry("location", "Asia and Africa"),
                                                entry("slogan", "The fastest land mammal in the world!"),
                                                entry("group", "Mammal"),
                                                entry("color", "BrownYellowBlackTan"),
                                                entry("skin_tyow", "Fur"),
                                                entry("top_speed", "70 mph"),
                                                entry("lifespan", "10 - 12 years"),
                                                entry("weight", "40 kg - 65 kg (99lbs - 1402lbs)"),
                                                entry("height", "115cm-136cm (45in - 53in)"),
                                                entry("age_of_sexual_maturity", "20 - 24 months"),
                                                entry("age_of_weaning", "3 months")))
                                .name("Cheetah")
                                .taxonomy(Map.ofEntries(
                                                entry("kingdom", "Animalia"),
                                                entry("phylum", "Chordata"),
                                                entry("class", "Mammalia"),
                                                entry("order", "Carnivora"),
                                                entry("family", "Felidae"),
                                                entry("genus", "Acinonyx"),
                                                entry("scientific_name", "Acinonyx jubatus")))
                                .locations(List.of("Africa", "Asia", "Eurasia"))
                                .build();
        }

        public static SpeciesDto createTestSpeciesDto() {
                return SpeciesDto.builder()
                                .characteristics(Map.ofEntries(
                                                entry("prey", "Gazelle, Wildebeest, Hare"),
                                                entry("name_of_young", "Cub"),
                                                entry("group_behavior", "Solitary/Pairs"),
                                                entry("estimated_population_size", "8.500"),
                                                entry("biggest_threat", "Habitat loss"),
                                                entry("most_distinctive_feature",
                                                                "Yellowish fur covered in small black spots"),
                                                entry("gestation_period", "90 days"),
                                                entry("habitat", "Open grassland"),
                                                entry("diet", "carnivore"),
                                                entry("average_litter_size", "3"),
                                                entry("lifestyle", "Diurnal"),
                                                entry("common_name", "Cheetah"),
                                                entry("number_of_species", "5"),
                                                entry("location", "Asia and Africa"),
                                                entry("slogan", "The fastest land mammal in the world!"),
                                                entry("group", "Mammal"),
                                                entry("color", "BrownYellowBlackTan"),
                                                entry("skin_tyow", "Fur"),
                                                entry("top_speed", "70 mph"),
                                                entry("lifespan", "10 - 12 years"),
                                                entry("weight", "40 kg - 65 kg (99lbs - 1402lbs)"),
                                                entry("height", "115cm-136cm (45in - 53in)"),
                                                entry("age_of_sexual_maturity", "20 - 24 months"),
                                                entry("age_of_weaning", "3 months")))
                                .name("Cheetah")
                                .taxonomy(Map.ofEntries(
                                                entry("kingdom", "Animalia"),
                                                entry("phylum", "Chordata"),
                                                entry("class", "Mammalia"),
                                                entry("order", "Carnivora"),
                                                entry("family", "Felidae"),
                                                entry("genus", "Acinonyx"),
                                                entry("scientific_name", "Acinonyx jubatus")))
                                .locations(List.of("Africa", "Asia", "Eurasia"))
                                .build();
        }

}
