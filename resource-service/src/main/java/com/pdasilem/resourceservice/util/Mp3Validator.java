package com.pdasilem.resourceservice.util;

import com.pdasilem.resourceservice.exception.InvalidMp3Exception;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mp3Validator {

    private final Tika tika;

    public void validate(byte[] audioData) {
        if (audioData == null || audioData.length == 0) {
            throw new InvalidMp3Exception("File is empty");
        }

        var mimeType = tika.detect(audioData);
        if (!"audio/mpeg".equals(mimeType)) {
            throw new InvalidMp3Exception("Invalid MIME type");
        }
    }
}