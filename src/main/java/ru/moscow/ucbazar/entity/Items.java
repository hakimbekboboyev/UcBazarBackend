package ru.moscow.ucbazar.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name="items")
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String bannerImage;


    @ElementCollection
    @CollectionTable(name = "my_entity_title", joinColumns = @JoinColumn(name = "my_entity_id"))
    @MapKeyColumn(name = "title_lang")
    @Column(name = "title_value")
    private Map<String,String> title;

    private Boolean isActive = true;

    @OneToMany
    private List<UcEntity> uc;
}
