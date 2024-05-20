package com.example.demo.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.pojos.Genre;
import com.example.demo.pojos.Movie;
import com.example.demo.repositories.GenreRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public Set<Genre> listGenres() {
       return genreRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Transactional
    public Genre addGenre(Genre newGenre) {
        String lowerCaseName = newGenre.getName().toLowerCase();
        Genre existingGenre = genreRepository.findByName(lowerCaseName);
        if (existingGenre != null) {
            return existingGenre;
        } else {
            newGenre.setName(lowerCaseName);
            return genreRepository.save(newGenre);
        }
    }

    @Transactional
    public void deleteGenre(String genreName){
        String lowerCaseName = genreName.toLowerCase();
        Genre genreToDelete = genreRepository.findByName(lowerCaseName);
                                    
        // Remove the genre from all movies
        List<Movie> movies = genreToDelete.getMovies();
        for (Movie movie : movies) {
            movie.getGenres().remove(genreToDelete);
        }

        // Delete the genre
        genreRepository.delete(genreToDelete);
    }
      
}
