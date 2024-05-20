package com.example.demo.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojos.Genre;
import com.example.demo.pojos.Movie;
import com.example.demo.services.GenreService;
import com.example.demo.services.MovieService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController 
@RequestMapping(path = "/movies") 
public class MainController {

  private final MovieService movieService;
  private final GenreService genreService;


  @PostMapping("/add")
  public Movie addMovie(@Valid @RequestBody Movie movie) {
    return movieService.addMovie(movie);
  }

  @GetMapping("/all")
  public Iterable<Movie> listMovies() {
    return movieService.listMovies();
  }

  @PutMapping("/update")
  public Movie updateMovie(@RequestParam String id, @Valid @RequestBody Movie updatedMovie) {
    return movieService.updateMovie(id, updatedMovie);
  }

  @DeleteMapping("/delete")
  public void deleteMovie(@RequestParam String id){
    movieService.deleteMovie(id);
  }

  @GetMapping("/genres")
  public Set<Genre> listGenres() {
      return genreService.listGenres();
  }

  @PostMapping("/genres/add")
  public ResponseEntity<Genre> addGenre(@RequestBody Genre newGenre) {
    Genre savedGenre = genreService.addGenre(newGenre);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
  }
  
  @DeleteMapping("/genres/delete")
  public void deleteGenre(@RequestParam String genre){
    genreService.deleteGenre(genre);
  }

  @GetMapping("/search")
  public List<Movie> searchMovies(@RequestParam(value = "searchTerm", required = false) String searchTerm) {
     if (searchTerm != null && !searchTerm.isEmpty()) {
            return movieService.searchMovies(searchTerm);
        }
        return Collections.emptyList();
  }
}
