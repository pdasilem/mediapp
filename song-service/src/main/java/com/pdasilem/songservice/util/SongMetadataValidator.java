package com.pdasilem.songservice.util;

import com.pdasilem.songservice.dto.SongMetadataDto;
import com.pdasilem.songservice.dto.SongMetadataRequest;
import com.pdasilem.songservice.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class SongMetadataValidator {

    private static final Pattern TIME_PATTERN = Pattern.compile("^((\\d+):)?([0-5]?\\d):([0-5]?\\d)$");
    private static final Pattern YEAR_PATTERN = Pattern.compile("^(19\\d{2}|20\\d{2})$");
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("^[1-9]\\d*$");
    private static final Pattern STRING_PATTERN = Pattern.compile("^.{1,100}$");

    public SongMetadataDto validateMetadata(SongMetadataRequest songMetadataRequest) {

        Map<String, String> errorDetails = new HashMap<>();

        validateField(NUMERIC_PATTERN, songMetadataRequest.id(), "id", "Id must be positive numeric", errorDetails);
        validateField(STRING_PATTERN, songMetadataRequest.album(), "album", "Album must be 1-100 characters text", errorDetails);
        validateField(STRING_PATTERN, songMetadataRequest.name(), "name", "Name must be 1-100 characters text", errorDetails);
        validateField(STRING_PATTERN, songMetadataRequest.artist(), "artist", "Artist must be 1-100 characters text", errorDetails);
        validateField(YEAR_PATTERN, songMetadataRequest.year(), "year", "Year must be in YYYY format", errorDetails);

        String duration = parseDuration(songMetadataRequest.duration());
        validateField(TIME_PATTERN, duration, "duration", "Duration must be in the format MM:SS or HH:MM:SS", errorDetails);

        if (!errorDetails.isEmpty()) {
            throw new ValidationException("Validation error", errorDetails);
        }
        return new SongMetadataDto(
                Integer.parseInt(songMetadataRequest.id()),
                songMetadataRequest.name(),
                songMetadataRequest.artist(),
                songMetadataRequest.album(),
                duration,
                Integer.parseInt(songMetadataRequest.year())
        );
    }

    private String formatDuration(long fullSeconds) {

        int hours = (int) fullSeconds / 3600;
        int remSeconds = (int) fullSeconds % 3600;
        int minutes = remSeconds / 60;
        int seconds = remSeconds % 60;

        return hours > 0 ? String.format("%d:%02d:%02d", hours, minutes, seconds)
                : String.format("%02d:%02d", minutes, seconds);
    }

    private void validateField(Pattern pattern, String value, String fieldName, String errorMessage, Map<String, String> errorDetails) {
        if (!pattern.matcher(value).matches()) {
            errorDetails.put(fieldName, errorMessage);
        }
    }

    private String parseDuration(String duration) {
        try {
            long seconds = (long) Double.parseDouble(duration);
            return formatDuration(seconds);
        } catch (NumberFormatException ignored) {
            return duration;
        }
    }
}
