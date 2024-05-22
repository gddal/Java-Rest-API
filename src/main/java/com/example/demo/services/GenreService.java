package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.pojos.Genre;
import com.example.demo.pojos.Movie;
import com.example.demo.repositories.GenreRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
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
        Optional<Genre> existingGenreOptional = genreRepository.findByName(lowerCaseName);
        if (existingGenreOptional.isPresent()) {
            throw new EntityExistsException("Genre already exists: "+ lowerCaseName);
        } else {
            newGenre.setName(lowerCaseName);
            try {
               return genreRepository.save(newGenre);
            } catch (ConstraintViolationException e) {
                throw new ConstraintViolationException("Genre name is invalid: " + lowerCaseName, e.getConstraintViolations());
            } catch (DataIntegrityViolationException e) {
                throw new DataIntegrityViolationException("Database error: " + e.getMessage());
            }
        }
    }

    @Transactional
    public void deleteGenre(String genreName){
        String lowerCaseName = genreName.toLowerCase();
        Optional<Genre> genreToDeleteOptional = genreRepository.findByName(lowerCaseName);
        if(!genreToDeleteOptional.isPresent())
            throw new EntityNotFoundException("The following genre doesn't exist: "+ genreName);
                                    
        Genre genre = genreToDeleteOptional.get();
        List<Movie> movies = genre.getMovies();
        for (Movie movie : movies) {
            movie.getGenres().remove(genre);
        }

        genreRepository.delete(genre);
    }
      
}
