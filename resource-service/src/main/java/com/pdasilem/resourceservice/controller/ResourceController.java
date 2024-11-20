package com.pdasilem.resourceservice.controller;

import com.pdasilem.resourceservice.dto.DeletedResourceIdsResponse;
import com.pdasilem.resourceservice.dto.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/resources")
public interface ResourceController {

    @Operation(summary = "Upload new resource",
            description = "Returns Integer id — ID of the created resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
            content = @Content(schema = @Schema(implementation = IdResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed or request body is invalid MP3",
            content = @Content),
            @ApiResponse(responseCode = "500", description = "An internal server error has occurred",
            content = @Content)
    })
    @PostMapping(consumes = "audio/mpeg", produces = "application/json")
    ResponseEntity<IdResponse> uploadResource(@RequestBody byte[] audioData);

    @Operation(summary = "Get resource by id",
            description = "Returns audio bytes of the resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "An internal server error has occurred",
                    content = @Content)
    })
    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    ResponseEntity<byte[]> getResourceById(@PathVariable Integer id);

        @Operation(summary = "Delete resources by ids",
                description = "Returns ids of deleted resources")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Ok",
                        content = @Content(schema = @Schema(implementation = DeletedResourceIdsResponse.class))),
                @ApiResponse(responseCode = "500", description = "An internal server error has occurred",
                        content = @Content)
        })
        @DeleteMapping(produces = "application/json")
        ResponseEntity<DeletedResourceIdsResponse> deleteResourcesByIds(
                @Parameter(description = "CSV of song metadata IDs to remove",
                content = @Content(mediaType = "text/csv"),
                schema = @Schema(type = "string")) @RequestParam String id);
}
