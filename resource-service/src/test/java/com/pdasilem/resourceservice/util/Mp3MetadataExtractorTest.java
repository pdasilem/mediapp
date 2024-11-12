package com.pdasilem.resourceservice.util;

import com.pdasilem.resourceservice.exception.DataExtractionException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class Mp3MetadataExtractorTest {

    @Mock
    private Mp3Parser mp3Parser;

    @InjectMocks
    private Mp3MetadataExtractor mp3MetadataExtractor;

    private byte[] mp3File;

    @BeforeEach
    void setUp() {
        mp3File = "test mp3 data".getBytes();
        Metadata metadata = new Metadata();
        metadata.set("xmpDM:artist", "Artist");
        metadata.set("xmpDM:album", "Album");
        metadata.set("dc:title", "Song");
        metadata.set("xmpDM:duration", "225.0");
        metadata.set("xmpDM:releaseDate", "2021");
    }

    @Test
    void testExtractMetadata_Success() throws Exception {
        doAnswer(invocation -> {
            Metadata metadata = invocation.getArgument(2);

            metadata.set("xmpDM:artist", "Artist");
            metadata.set("xmpDM:album", "Album");
            metadata.set("dc:title", "Song");
            metadata.set("xmpDM:duration", "225.0");
            metadata.set("xmpDM:releaseDate", "2021");

            return null;
        }).when(mp3Parser).parse(any(ByteArrayInputStream.class), any(ContentHandler.class), any(Metadata.class), any(ParseContext.class));

        Metadata result = mp3MetadataExtractor.extractMetadata(mp3File);

        assertNotNull(result);
        assertEquals("Artist", result.get("xmpDM:artist"));
        assertEquals("Album", result.get("xmpDM:album"));
        assertEquals("Song", result.get("dc:title"));
        assertEquals("225.0", result.get("xmpDM:duration"));
        assertEquals("2021", result.get("xmpDM:releaseDate"));

        verify(mp3Parser).parse(any(ByteArrayInputStream.class), any(ContentHandler.class), any(Metadata.class), any(ParseContext.class));
    }

    @Test
    void testExtractMetadata_Failure() throws TikaException, IOException, SAXException {
        doThrow(new DataExtractionException("Failed to extract metadata from MP3 file"))
                .when(mp3Parser).parse(any(ByteArrayInputStream.class), any(ContentHandler.class), any(Metadata.class), any(ParseContext.class));

        assertThrows(DataExtractionException.class, () -> mp3MetadataExtractor.extractMetadata(mp3File));

        verify(mp3Parser).parse(any(ByteArrayInputStream.class), any(ContentHandler.class), any(Metadata.class), any(ParseContext.class));
    }
}
