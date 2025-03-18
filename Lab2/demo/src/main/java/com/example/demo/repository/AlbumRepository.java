package com.example.demo.repository;

import com.example.demo.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByTitle(String title);
    List<Album> findByReleaseDate(LocalDate releaseDate);
    List<Album> findByArtistId(Long artistId);
}