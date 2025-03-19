package com.example.demo.controller;

import com.example.demo.model.Song;
import com.example.demo.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    
    private final SongService songService;
    
    public SongController(SongService songService) {
        this.songService = songService;
    }
    
    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songService.getAllSongs();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        return songService.getSongById(id)
                .map(song -> new ResponseEntity<>(song, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Song>> getSongsByTitle(@PathVariable String title) {
        List<Song> songs = songService.getSongsByTitle(title);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }
    
    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<Song>> getSongsByAlbumId(@PathVariable Long albumId) {
        List<Song> songs = songService.getSongsByAlbumId(albumId);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }
    
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<Song>> getSongsByArtistId(@PathVariable Long artistId) {
        List<Song> songs = songService.getSongsByArtistId(artistId);
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        Song createdSong = songService.saveSong(song);
        return new ResponseEntity<>(createdSong, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id, @RequestBody Song song) {
        return songService.getSongById(id)
                .map(existingSong -> {
                    song.setId(id);
                    Song updatedSong = songService.saveSong(song);
                    return new ResponseEntity<>(updatedSong, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        return songService.getSongById(id)
                .map(song -> {
                    songService.deleteSong(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}