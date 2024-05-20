package com.example.demo.pojos;

import java.util.List;

import org.hibernate.annotations.ColumnTransformer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Genre {

    public Genre(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 20)
    @Column(unique = true)
    @ColumnTransformer(write = "LOWER(?)")
    private String name;

    @JsonIgnoreProperties("genres")
    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;
}
