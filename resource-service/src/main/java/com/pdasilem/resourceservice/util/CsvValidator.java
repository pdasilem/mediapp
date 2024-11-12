package com.pdasilem.resourceservice.util;

import com.pdasilem.resourceservice.exception.InvalidCsvException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CsvValidator {

    private static final int MAX_LENGTH = 200;
    private static final Pattern CSV_PATTERN = Pattern.compile("^\\d+(,\\d+)*$");

    public void validate(String text) {
        if (text.length() > MAX_LENGTH) {
            throw new InvalidCsvException("CSV length exceeds maximum of 200 symbols");
        }

        if (!CSV_PATTERN.matcher(text).matches()) {
            throw new InvalidCsvException("CSV content should have numeric value only and be divided by comma");
        }
    }

}
