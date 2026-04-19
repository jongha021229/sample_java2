package com.example.samplejava2.controller;

import com.example.samplejava2.model.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final Map<Long, Movie> movies = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @GetMapping
    public List<Map<String, Object>> listMovies() {
        List<Map<String, Object>> result = new ArrayList<>();
        movies.forEach((id, movie) -> result.add(movieToMap(id, movie)));
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getMovie(@PathVariable Long id) {
        Movie movie = movies.get(id);
        if (movie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "not found"));
        }
        return ResponseEntity.ok(movieToMap(id, movie));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createMovie(@Valid @RequestBody Movie movie) {
        long id = nextId.getAndIncrement();
        movies.put(id, movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieToMap(id, movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteMovie(@PathVariable Long id) {
        Movie removed = movies.remove(id);
        if (removed == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "not found"));
        }
        return ResponseEntity.ok(Map.of("status", "deleted"));
    }

    @GetMapping("/search")
    public List<Map<String, Object>> searchMovies(
            @RequestParam(defaultValue = "") @Size(max = 100) String q) {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = q.toLowerCase();
        movies.forEach((id, movie) -> {
            if (movie.getTitle().toLowerCase().contains(query)) {
                result.add(movieToMap(id, movie));
            }
        });
        return result;
    }

    private Map<String, Object> movieToMap(Long id, Movie movie) {
        Map<String, Object> map = new java.util.LinkedHashMap<>();
        map.put("id", id);
        map.put("title", movie.getTitle());
        map.put("rating", movie.getRating());
        map.put("synopsis", movie.getSynopsis());
        return map;
    }
}
