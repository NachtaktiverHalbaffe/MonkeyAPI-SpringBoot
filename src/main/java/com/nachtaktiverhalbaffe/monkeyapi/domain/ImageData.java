package com.nachtaktiverhalbaffe.monkeyapi.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "images")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageData {

    @Id
    private String name;

    private String type;

    private Long fileSize;

    private int width;

    private int heigt;

    @Lob
    @Column(name = "image_data", length = 1000)
    private byte[] data;
}
