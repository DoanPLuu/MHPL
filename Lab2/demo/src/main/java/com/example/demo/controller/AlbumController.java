package com.example.demo.controller;

import com.example.demo.model.Album;
import com.example.demo.service.AlbumService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {
    
    private final AlbumService albumService;
    
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }
    
    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albums = albumService.getAllAlbums();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
        return albumService.getAlbumById(id)
                .map(album -> new ResponseEntity<>(album, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Album>> getAlbumsByTitle(@PathVariable String title) {
        List<Album> albums = albumService.getAlbumsByTitle(title);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Album>> getAlbumsByReleaseDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Album> albums = albumService.getAlbumsByReleaseDate(date);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }
    
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<Album>> getAlbumsByArtistId(@PathVariable Long artistId) {
        List<Album> albums = albumService.getAlbumsByArtistId(artistId);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Album> createAlbum(@RequestBody Album album) {
        Album createdAlbum = albumService.saveAlbum(album);
        return new ResponseEntity<>(createdAlbum, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @RequestBody Album album) {
        return albumService.getAlbumById(id)
                .map(existingAlbum -> {
                    album.setId(id);
                    Album updatedAlbum = albumService.saveAlbum(album);
                    return new ResponseEntity<>(updatedAlbum, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        return albumService.getAlbumById(id)
                .map(album -> {
                    albumService.deleteAlbum(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
