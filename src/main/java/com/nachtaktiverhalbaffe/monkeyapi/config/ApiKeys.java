package com.nachtaktiverhalbaffe.monkeyapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(prefix = "apikeys")
@ConfigurationPropertiesScan
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiKeys {

    @NotBlank
    private String animalsApi;

    @NotBlank
    private String pexels;
}
