// Cấu trúc thư mục dự án
// music-app/
//  ├── src/main/java/com/example/musicapp/
//  │   ├── MusicAppApplication.java
//  │   ├── model/
//  │   │   ├── Song.java
//  │   │   ├── Album.java
//  │   │   └── Artist.java
//  │   ├── repository/
//  │   │   ├── SongRepository.java
//  │   │   ├── AlbumRepository.java
//  │   │   └── ArtistRepository.java
//  │   ├── service/
//  │   │   ├── SongService.java
//  │   │   ├── AlbumService.java
//  │   │   └── ArtistService.java
//  │   └── controller/
//  │       ├── SongController.java
//  │       ├── AlbumController.java
//  │       └── ArtistController.java
//  └── src/main/resources/
//      └── application.properties

// File: MusicAppApplication.java
package com.example.musicapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusicAppApplication.class, args);
    }
}

// File: model/Song.java
package com.example.musicapp.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private Integer duration; // in seconds
    
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
    
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
    
    // Constructors
    public Song() {
    }
    
    public Song(String title, Integer duration) {
        this.title = title;
        this.duration = duration;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Integer getDuration() {
        return duration;
    }
    
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    public Album getAlbum() {
        return album;
    }
    
    public void setAlbum(Album album) {
        this.album = album;
    }
    
    public Artist getArtist() {
        return artist;
    }
    
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}

// File: model/Album.java
package com.example.musicapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(name = "release_date")
    private LocalDate releaseDate;
    
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
    
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();
    
    // Constructors
    public Album() {
    }
    
    public Album(String title, LocalDate releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public Artist getArtist() {
        return artist;
    }
    
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    
    public List<Song> getSongs() {
        return songs;
    }
    
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
    
    // Helper methods
    public void addSong(Song song) {
        songs.add(song);
        song.setAlbum(this);
    }
    
    public void removeSong(Song song) {
        songs.remove(song);
        song.setAlbum(null);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(id, album.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}

// File: model/Artist.java
package com.example.musicapp.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<Album> albums = new ArrayList<>();
    
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
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

// File: repository/SongRepository.java
package com.example.musicapp.repository;

import com.example.musicapp.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByTitle(String title);
    List<Song> findByAlbumId(Long albumId);
    List<Song> findByArtistId(Long artistId);
}

// File: repository/AlbumRepository.java
package com.example.musicapp.repository;

import com.example.musicapp.model.Album;
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

// File: repository/ArtistRepository.java
package com.example.musicapp.repository;

import com.example.musicapp.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByName(String name);
    List<Artist> findByMusicGenre(String musicGenre);
}

// File: service/SongService.java
package com.example.musicapp.service;

import com.example.musicapp.model.Song;
import com.example.musicapp.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {
    
    private final SongRepository songRepository;
    
    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }
    
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }
    
    public Optional<Song> getSongById(Long id) {
        return songRepository.findById(id);
    }
    
    public List<Song> getSongsByTitle(String title) {
        return songRepository.findByTitle(title);
    }
    
    public List<Song> getSongsByAlbumId(Long albumId) {
        return songRepository.findByAlbumId(albumId);
    }
    
    public List<Song> getSongsByArtistId(Long artistId) {
        return songRepository.findByArtistId(artistId);
    }
    
    public Song saveSong(Song song) {
        return songRepository.save(song);
    }
    
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }
}

// File: service/AlbumService.java
package com.example.musicapp.service;

import com.example.musicapp.model.Album;
import com.example.musicapp.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    
    private final AlbumRepository albumRepository;
    
    @Autowired
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

// File: service/ArtistService.java
package com.example.musicapp.service;

import com.example.musicapp.model.Artist;
import com.example.musicapp.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    
    private final ArtistRepository artistRepository;
    
    @Autowired
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

// File: controller/SongController.java
package com.example.musicapp.controller;

import com.example.musicapp.model.Song;
import com.example.musicapp.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {
    
    private final SongService songService;
    
    @Autowired
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

// File: controller/AlbumController.java
package com.example.musicapp.controller;

import com.example.musicapp.model.Album;
import com.example.musicapp.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
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

// File: controller/ArtistController.java
package com.example.musicapp.controller;

import com.example.musicapp.model.Artist;
import com.example.musicapp.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
    
    private final ArtistService artistService;
    
    @Autowired
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

// File: application.properties
# Cấu hình cơ sở dữ liệu
spring.datasource.url=jdbc:mysql://localhost:3306/musicdb
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Cấu hình Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true

# Cấu hình server
server.port=8080
