package com.pdasilem.songservice.repository;

import com.pdasilem.songservice.model.SongMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends CrudRepository<SongMetadata, Integer> {

    List<SongMetadata> findAllByIdIn(List<Integer> ids);

    void deleteByIdIn(List<Integer> ids);
}

