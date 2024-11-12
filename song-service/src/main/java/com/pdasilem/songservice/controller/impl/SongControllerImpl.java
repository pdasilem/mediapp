package com.pdasilem.songservice.controller.impl;

import com.pdasilem.songservice.controller.SongController;
import com.pdasilem.songservice.dto.SongIdResponse;
import com.pdasilem.songservice.dto.SongIdsResponse;
import com.pdasilem.songservice.dto.SongMetadataDto;
import com.pdasilem.songservice.exception.ResourceNotFoundException;
import com.pdasilem.songservice.service.SongService;
import com.pdasilem.songservice.util.CsvValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SongControllerImpl implements SongController {

    private final SongService songService;
    private final CsvValidator csvValidator;

    @Override
    public ResponseEntity<SongIdResponse> postSong(SongMetadataDto songMetadataDto) {
        return ResponseEntity.ok(songService.saveSongMetadata(songMetadataDto));
    }

    @Override
    public ResponseEntity<SongMetadataDto> getSongById(Integer id) {
        var result = songService.getSongMetadataById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String
                        .format("The resource with the specified %s does not exist", id.toString())));
        return ResponseEntity.ok(result);

    }

    @Override
    public ResponseEntity<SongIdsResponse> deleteSongByIds(String id) {
        csvValidator.validate(id);
        return ResponseEntity.ok(songService.deleteSongMetadataByIds(id));
    }
}
