package uk.co.sainsburys.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnitPriceExtractor {

    private static final String REGEX_PATTERN = "\\D*([0-9]+\\.\\d{2})\\D*";

    public Double value(String formattedPrice) {
        Double value = NumberUtils.DOUBLE_ZERO;
        Pattern pattern = Pattern.compile(REGEX_PATTERN);
        Matcher matcher = pattern.matcher(formattedPrice);
        if (matcher.find()) {
            value = Double.parseDouble(matcher.group(1));
        }
        return value;
    }
}
