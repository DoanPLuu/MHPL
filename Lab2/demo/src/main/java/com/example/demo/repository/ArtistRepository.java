package com.example.demo.repository;

import com.example.demo.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByName(String name);
    List<Artist> findByMusicGenre(String musicGenre);
}
