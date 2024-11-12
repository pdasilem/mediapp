package com.pdasilem.songservice.repository;

import com.pdasilem.songservice.model.SongMetadata;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends CrudRepository<SongMetadata, Integer> {

    @Query("select * from song_metadata where id in (:ids)")
    List<SongMetadata> findAllByIds(List<Integer> ids);

    SongMetadata getById(Integer id);

    @Modifying
    @Query("delete from song_metadata where id in (:ids)")
    void deleteAllByIds(List<Integer> ids);
}

