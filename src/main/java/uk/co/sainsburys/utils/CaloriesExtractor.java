package uk.co.sainsburys.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaloriesExtractor {

    private static final String REGEX_PATTERN = "\\D*([0-9]+)\\D*";

    public int value(String formattedCalories) {
        int value = NumberUtils.INTEGER_ZERO;
        Pattern pattern = Pattern.compile(REGEX_PATTERN);
        Matcher matcher = pattern.matcher(formattedCalories);
        if (matcher.find()) {
            value = Integer.parseInt(matcher.group(1));
        }
        return value;
    }
}
