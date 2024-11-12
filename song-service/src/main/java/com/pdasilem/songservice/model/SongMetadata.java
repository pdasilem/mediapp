package com.pdasilem.songservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("song_metadata")
@Data
public class SongMetadata {

    @Id
    Integer id;
    String name;
    String artist;
    String album;
    String length;
    Integer resourceId;
    Integer year;
}
