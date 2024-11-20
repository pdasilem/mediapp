package com.pdasilem.songservice.controller.impl;

import com.pdasilem.songservice.controller.SongController;
import com.pdasilem.songservice.dto.SongIdResponse;
import com.pdasilem.songservice.dto.SongIdsResponse;
import com.pdasilem.songservice.dto.SongMetadataDto;
import com.pdasilem.songservice.dto.SongMetadataRequest;
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
    public ResponseEntity<SongIdResponse> postSong(SongMetadataRequest songMetadataRequest) {
        return ResponseEntity.ok(songService.saveSongMetadata(songMetadataRequest));
    }

    @Override
    public ResponseEntity<SongMetadataDto> getSongById(Integer id) {
        return ResponseEntity.ok(songService.getSongMetadataById(id));

    }

    @Override
    public ResponseEntity<SongIdsResponse> deleteSongByIds(String id) {
        csvValidator.validate(id);
        return ResponseEntity.ok(songService.deleteSongMetadataByIds(id));
    }
}
