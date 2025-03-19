package com.example.demo.controller;

import com.example.demo.model.Artist;
import com.example.demo.service.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
    
    private final ArtistService artistService;
    
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }
    
    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() {
        List<Artist> artists = artistService.getAllArtists();
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        return artistService.getArtistById(id)
                .map(artist -> new ResponseEntity<>(artist, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Artist>> getArtistsByName(@PathVariable String name) {
        List<Artist> artists = artistService.getArtistsByName(name);
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }
    
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Artist>> getArtistsByMusicGenre(@PathVariable String genre) {
        List<Artist> artists = artistService.getArtistsByMusicGenre(genre);
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
        Artist createdArtist = artistService.saveArtist(artist);
        return new ResponseEntity<>(createdArtist, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id, @RequestBody Artist artist) {
        return artistService.getArtistById(id)
                .map(existingArtist -> {
                    artist.setId(id);
                    Artist updatedArtist = artistService.saveArtist(artist);
                    return new ResponseEntity<>(updatedArtist, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        return artistService.getArtistById(id)
                .map(artist -> {
                    artistService.deleteArtist(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}