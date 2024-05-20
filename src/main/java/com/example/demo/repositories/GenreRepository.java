package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.pojos.Genre;


public interface GenreRepository extends JpaRepository<Genre, Long>{

    Optional<Genre> findByName(String name);
    
}
