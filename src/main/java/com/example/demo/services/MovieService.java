package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.pojos.Genre;
import com.example.demo.pojos.Movie;
import com.example.demo.repositories.MovieRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MovieService {
    
    private final MovieRepository movieRepository;
    private final GenreService genreService;

    @Transactional
    public Movie addMovie(Movie movie) {
        movie.setGenres(this.checkGenres(movie));
        return movieRepository.save(movie);
    }

    private List<Genre> checkGenres(Movie movie) {
        List<Genre> finalGenres = new ArrayList<>();
        for (Genre genre : movie.getGenres()) {
            finalGenres.add(genreService.addGenre(genre));
        }
        return finalGenres;
    }

    public Iterable<Movie> listMovies() {
        return movieRepository.findAll();
    }

    public Movie updateMovie(String id, Movie updatedMovie) {
        
        Movie existingMovie = movieRepository.findById(Long.valueOf(id))
        .orElseThrow(() -> new EntityNotFoundException("Movie not found with id " + id));

        existingMovie.setTitle(updatedMovie.getTitle());
        existingMovie.setDescription(updatedMovie.getDescription());
        existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
        existingMovie.setGenres(checkGenres(updatedMovie));

        return movieRepository.save(existingMovie);
    }

    @Transactional
    public void deleteMovie(String id){
        movieRepository.deleteById(Long.valueOf(id));
    }

    public List<Movie> searchMovies(String searchTerm) {
        String lowerCaseSearchTerm = searchTerm.toLowerCase();
        // Search by title
        List<Movie> moviesByTitle = movieRepository.findByTitleContainingIgnoreCase(lowerCaseSearchTerm);
        // Search by genre name
        List<Movie> moviesByGenre = movieRepository.findByGenresNameContainingIgnoreCase(lowerCaseSearchTerm);
        // Merge and remove duplicates if necessary
        // You can implement a method to remove duplicates based on movie IDs or use a Set
        moviesByTitle.addAll(moviesByGenre);
        return moviesByTitle;
    }
    
}
