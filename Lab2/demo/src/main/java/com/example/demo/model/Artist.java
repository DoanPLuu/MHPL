package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "music_genre")
    private String musicGenre;
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
     @JsonManagedReference("artist-albums")
    private List<Album> albums = new ArrayList<>();
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("artist-songs")
    private List<Song> songs = new ArrayList<>();
    
    // Constructors
    public Artist() {
    }
    
    public Artist(String name, String musicGenre) {
        this.name = name;
        this.musicGenre = musicGenre;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMusicGenre() {
        return musicGenre;
    }
    
    public void setMusicGenre(String musicGenre) {
        this.musicGenre = musicGenre;
    }
    
    public List<Album> getAlbums() {
        return albums;
    }
    
    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
    
    public List<Song> getSongs() {
        return songs;
    }
    
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
    
    // Helper methods
    public void addAlbum(Album album) {
        albums.add(album);
        album.setArtist(this);
    }
    
    public void removeAlbum(Album album) {
        albums.remove(album);
        album.setArtist(null);
    }
    
    public void addSong(Song song) {
        songs.add(song);
        song.setArtist(this);
    }
    
    public void removeSong(Song song) {
        songs.remove(song);
        song.setArtist(null);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(id, artist.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", musicGenre='" + musicGenre + '\'' +
                '}';
    }
}