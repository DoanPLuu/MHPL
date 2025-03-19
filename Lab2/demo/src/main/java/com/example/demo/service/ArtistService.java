package com.example.demo.service;

import com.example.demo.model.Artist;
import com.example.demo.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    
    private final ArtistRepository artistRepository;
    
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }
    
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }
    
    public Optional<Artist> getArtistById(Long id) {
        return artistRepository.findById(id);
    }
    
    public List<Artist> getArtistsByName(String name) {
        return artistRepository.findByName(name);
    }
    
    public List<Artist> getArtistsByMusicGenre(String musicGenre) {
        return artistRepository.findByMusicGenre(musicGenre);
    }
    
    public Artist saveArtist(Artist artist) {
        return artistRepository.save(artist);
    }
    
    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }
}
