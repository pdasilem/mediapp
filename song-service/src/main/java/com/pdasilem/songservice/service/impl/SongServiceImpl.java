package com.pdasilem.songservice.service.impl;

import com.pdasilem.songservice.dto.SongIdResponse;
import com.pdasilem.songservice.dto.SongIdsResponse;
import com.pdasilem.songservice.dto.SongMetadataDto;
import com.pdasilem.songservice.mapper.SongIdMapper;
import com.pdasilem.songservice.mapper.SongMetadataMapper;
import com.pdasilem.songservice.model.SongMetadata;
import com.pdasilem.songservice.repository.SongRepository;
import com.pdasilem.songservice.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final SongMetadataMapper songMetadataMapper;
    private final SongIdMapper songIdMapper;

    @Override
    public SongIdResponse saveSongMetadata(SongMetadataDto songMetadataDto) {
        var result = songRepository.save(songMetadataMapper.dtoToModel(songMetadataDto));
        return songIdMapper.modelToDto(result.getId());
    }

    @Override
    public Optional<SongMetadataDto> getSongMetadataById(Integer id) {
        return Optional.ofNullable(songMetadataMapper.modelToDto(songRepository.getById(id)));
    }

    @Override
    public SongIdsResponse deleteSongMetadataByIds(String ids) {

        List<Integer> idsList = Arrays.stream(ids.split(","))
                .map(Integer::valueOf)
                .toList();

        var songsToDelete = songRepository.findAllByIds(idsList);
        var songIdsToDelete = songsToDelete.stream()
                        .map(SongMetadata::getId)
                        .toList();

        songRepository.deleteAllByIds(idsList);

        return songIdMapper.idListToDto(songIdsToDelete);
    }
}
