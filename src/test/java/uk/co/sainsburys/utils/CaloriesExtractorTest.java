package uk.co.sainsburys.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CaloriesExtractorTest {

    private static final int CALORIES = 300;
    private static final String CALORIES_SUFFIX = "kcal";
    private static final String BLANK_SPACE = " ";
    private static final double CALORIES_IN_DOUBLE = 300.01;
    private static final String RANDOM_STRING = "asdaasda";

    private CaloriesExtractor extractor;

    @Before
    public void setup() {
        extractor = new CaloriesExtractor();
    }

    @Test
    public void givenCaloriesStringInRightFormatThenReturnCorrectIntValue() {
        String formattedCalories = CALORIES + CALORIES_SUFFIX;
        assertEquals(CALORIES, extractor.value(formattedCalories));
    }

    @Test
    public void givenCaloriesStringContainLeadingSpaceThenReturnCorrectIntValue() {
        String formattedCalories = BLANK_SPACE + CALORIES + CALORIES_SUFFIX;
        assertEquals(CALORIES, extractor.value(formattedCalories));
    }

    @Test
    public void givenCaloriesStringContainLeadingAndTailingSpaceThenReturnCorrectIntValue() {
        String formattedCalories = BLANK_SPACE + CALORIES + CALORIES_SUFFIX + BLANK_SPACE;
        assertEquals(CALORIES, extractor.value(formattedCalories));
    }

    @Test
    public void givenCaloriesStringContainTailingSpaceThenReturnCorrectIntValue() {
        String formattedCalories = CALORIES + CALORIES_SUFFIX + BLANK_SPACE;
        assertEquals(CALORIES, extractor.value(formattedCalories));
    }
    @Test
    public void givenCaloriesStringInRightFormatWithDoubleValueThenReturnCorrectIntValue() {
        String formattedCalories = CALORIES_IN_DOUBLE + CALORIES_SUFFIX;
        assertEquals(CALORIES, extractor.value(formattedCalories));
    }
    @Test
    public void givenCaloriesIsRandomStringThenReturnCorrectIntValue() {
        String formattedCalories = RANDOM_STRING + CALORIES_SUFFIX;
        assertEquals(0, extractor.value(formattedCalories));
    }
}
