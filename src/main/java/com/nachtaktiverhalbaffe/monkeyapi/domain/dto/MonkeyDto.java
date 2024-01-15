package com.nachtaktiverhalbaffe.monkeyapi.domain.dto;

import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Monkey;
import com.nachtaktiverhalbaffe.monkeyapi.domain.Species;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonkeyDto {

    private Long id;

    private String name;

    private String known_from;

    private String description;

    private String strength;

    private String weaknesses;

    private int attack;

    private int defense;

    private int special_attack;

    private int special_defense;

    private int speed;

    private int health_points;

    private String image;

    private String species_name;

    private Species species_content;

    public MonkeyDto(String jsonAsString) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MonkeyDto serialiedDto = objectMapper.readValue(jsonAsString, MonkeyDto.class);
        // if (Optional.ofNullable(serialiedDto.getId()).isPresent())
        setId(serialiedDto.getId());
        setName(serialiedDto.getName());
        setKnown_from(serialiedDto.getKnown_from());
        setDescription(serialiedDto.getDescription());
        setStrength(serialiedDto.getStrength());
        setWeaknesses(serialiedDto.getWeaknesses());
        setAttack(serialiedDto.getAttack());
        setDefense(serialiedDto.getDefense());
        setSpecial_attack(serialiedDto.getSpecial_attack());
        setSpecial_defense(serialiedDto.getSpecial_defense());
        setSpeed(serialiedDto.getSpeed());
        setHealth_points(serialiedDto.getHealth_points());
        setImage(serialiedDto.getImage());
        setSpecies_name(serialiedDto.getSpecies_name());
        setSpecies_content(serialiedDto.getSpecies_content());

    }

    public MonkeyDto(Monkey monkey, Environment environment) {

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        setId(monkey.getId());
        setName(monkey.getName());
        setKnown_from(monkey.getKnown_from());
        setDescription(monkey.getDescription());
        setStrength(monkey.getStrength());
        setWeaknesses(monkey.getWeaknesses());
        setAttack(monkey.getAttack());
        setDefense(monkey.getDefense());
        setSpecial_attack(monkey.getSpecialAttack());
        setSpecial_defense(monkey.getSpecialDefense());
        setSpeed(monkey.getSpeed());
        setHealth_points(monkey.getHealthPoints());
        setImage(Optional.ofNullable(monkey.getImage()).isPresent()
                ? baseUrl + "/api/v1/imagepool/" + monkey.getImage().getName()
                : null);
        setSpecies_name(Optional.ofNullable(monkey.getSpecies()).isPresent() ? monkey.getSpecies().getName() : null);
        setSpecies_content(monkey.getSpecies());
    }

    public Monkey mapToEntity() {

        return Monkey.builder()
                .id(id)
                .name(name)
                .known_from(known_from)
                .description(description)
                .strength(strength)
                .weaknesses(weaknesses)
                .attack(attack)
                .defense(defense)
                .specialAttack(attack)
                .specialDefense(defense)
                .speed(speed)
                .healthPoints(health_points)
                .species(species_content)
                .build();
    }
}
