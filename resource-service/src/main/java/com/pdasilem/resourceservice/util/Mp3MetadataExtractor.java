package com.pdasilem.resourceservice.util;

import com.pdasilem.resourceservice.exception.DataExtractionException;
import lombok.RequiredArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.springframework.stereotype.Component;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;

@Component
@RequiredArgsConstructor
public class Mp3MetadataExtractor {

    private final Mp3Parser mp3Parser;

    public Metadata extractMetadata(byte[] mp3File) {
        try {
            var inputStream = new ByteArrayInputStream(mp3File);

            ContentHandler contentHandler = new DefaultHandler();
            Metadata metadata = new Metadata();
            ParseContext parseContext = new ParseContext();

            mp3Parser.parse(inputStream, contentHandler, metadata, parseContext);
            return metadata;
        } catch (Exception ex) {
            throw new DataExtractionException("Failed to extract metadata from MP3 file");
        }
    }
}
