package com.pdasilem.resourceservice.repository;

import com.pdasilem.resourceservice.model.Mp3Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Mp3Repository extends JpaRepository<Mp3Model, Integer> {

    List<Mp3Model> deleteByIdIn(List<Integer> ids);
}
