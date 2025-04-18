package com.example.demo.service;

import com.example.demo.model.Album;
import com.example.demo.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    
    private final AlbumRepository albumRepository;
    
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }
    
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }
    
    public Optional<Album> getAlbumById(Long id) {
        return albumRepository.findById(id);
    }
    
    public List<Album> getAlbumsByTitle(String title) {
        return albumRepository.findByTitle(title);
    }
    
    public List<Album> getAlbumsByReleaseDate(LocalDate releaseDate) {
        return albumRepository.findByReleaseDate(releaseDate);
    }
    
    public List<Album> getAlbumsByArtistId(Long artistId) {
        return albumRepository.findByArtistId(artistId);
    }
    
    public Album saveAlbum(Album album) {
        return albumRepository.save(album);
    }
    
    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }
}