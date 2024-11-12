package com.pdasilem.songservice.controller;

import com.pdasilem.songservice.dto.SongIdResponse;
import com.pdasilem.songservice.dto.SongIdsResponse;
import com.pdasilem.songservice.dto.SongMetadataDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/songs")
public interface SongController {

    @Operation(summary = "Create a new song", description = "Create a new song metadata record in database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Integer id – ID of the created song metadata",
            content = @Content(schema = @Schema(implementation = SongIdResponse.class))),
            @ApiResponse(responseCode = "400", description = "Song metadata missing validation error", content =
            @Content),
            @ApiResponse(responseCode = "500", description = "An internal server error has occurred",
                    content = @Content)
    })
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    ResponseEntity<SongIdResponse> postSong(
            @Parameter(description = "Song metadata record, referencing to resource id (mp3 file itself)")
            @Valid @RequestBody SongMetadataDto songMetadataDto);

    @Operation(summary = "Get song metadata", description = "Get song metadata")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(schema = @Schema(implementation = SongMetadataDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "The song metadata with the specified id does not exist",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "An internal server error has occurred", content = @Content)
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<SongMetadataDto> getSongById(@PathVariable("id") Integer id);

    @Operation(summary = "Delete a song(s) metadata",
            description = "Delete a song(s) metadata. If there is no song metadata for id, do nothing")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(schema = @Schema(implementation = SongIdsResponse.class))),
            @ApiResponse(responseCode = "500",
                    description = "An internal server error has occurred", content = @Content)
    })
    @DeleteMapping(value = "", produces = "application/json")
    ResponseEntity<SongIdsResponse> deleteSongByIds(
            @Parameter(description = "CSV of song metadata IDs to remove",
            content = @Content(mediaType = "text/csv"),
            schema = @Schema(type = "string")) @RequestParam("id") String id);
}
