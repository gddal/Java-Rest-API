package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.pojos.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    public List<Movie> findByTitleContainingIgnoreCase(String lowerCaseSearchTerm);

    public List<Movie> findByGenresNameContainingIgnoreCase(String lowerCaseSearchTerm);

    public List<Movie> findByTitleContaining(String title);

    public List<Movie> findByGenresNameContaining(String genre);

    public List<Movie> findByTitleContainingAndGenresNameContaining(String title, String genre);

}