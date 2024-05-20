package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.pojos.Genre;


public interface GenreRepository extends JpaRepository<Genre, Long>{

    Genre findByName(String name);
    
}
