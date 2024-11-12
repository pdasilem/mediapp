package com.pdasilem.songservice.util;
import com.pdasilem.songservice.exception.InvalidCsvException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvValidatorTest {

    private final CsvValidator csvValidator = new CsvValidator();

    @Test
    void testValidate_ValidCsv() {
        String validCsv = "1,2,3,4,5";
        csvValidator.validate(validCsv);
    }

    @Test
    void testValidate_InvalidCsv_NonNumeric() {
        String invalidCsv = "1,2,three,4,5";
        assertThrows(InvalidCsvException.class, () -> csvValidator.validate(invalidCsv));
    }

    @Test
    void testValidate_InvalidCsv_ExceedsMaxLength() {
        StringBuilder longCsv = new StringBuilder();
        for (int i = 0; i < 201; i++) {
            longCsv.append(i).append(",");
        }
        assertThrows(InvalidCsvException.class, () -> csvValidator.validate(longCsv.toString()));
    }

    @Test
    void testValidate_InvalidCsv_EmptyString() {
        String emptyCsv = "";
        assertThrows(InvalidCsvException.class, () -> csvValidator.validate(emptyCsv));
    }
}
