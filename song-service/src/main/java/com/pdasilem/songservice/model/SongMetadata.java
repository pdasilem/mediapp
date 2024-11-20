package com.pdasilem.songservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "song_metadata")
@Getter
@Setter
public class SongMetadata {

    @Id
    Integer id;
    String name;
    String artist;
    String album;
    String duration;
    Integer year;
}
