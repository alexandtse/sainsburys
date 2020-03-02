package uk.co.sainsburys.utils;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitPriceExtractorTest {

    private static final String POUND_SIGN = "£";
    private static final String UNIT_SUFFIX = "/unit";
    private static final String BLANK_SPACE = " ";
    private static final double DELTA = 0.001;
    private static final String RANDOM_CHARS = "asdihukwhedkj";

    private UnitPriceExtractor extractor;

    @Before
    public void setup() {
        extractor = new UnitPriceExtractor();
    }

    @Test
    public void givenUnitPriceWithCurrencySymbolThenReturnExtractedValue() {
        Double testSubject = 12.01;
        Double actualValue = extractor.value(POUND_SIGN + testSubject + UNIT_SUFFIX);
        assertEquals(testSubject, actualValue, DELTA);

    }

    @Test
    public void givenUnitPriceWithLeadingSpaceThenReturnExtractedValue() {
        Double testSubject = 12.01;

        Double actualValue = extractor.value(BLANK_SPACE +POUND_SIGN + testSubject + UNIT_SUFFIX);
        assertEquals(testSubject, actualValue, DELTA);
    }

    @Test
    public void givenUnitPriceWithTailingSpaceThenReturnExtractedValue() {
        Double testSubject = 12.01;

        Double actualValue = extractor.value( POUND_SIGN + testSubject + UNIT_SUFFIX + BLANK_SPACE);
        assertEquals(testSubject, actualValue, DELTA);
    }

    @Test
    public void givenUnitPriceWithLeadingAndTailingSpaceThenReturnExtractedValue() {
        Double testSubject = 12.01;

        Double actualValue = extractor.value( BLANK_SPACE + POUND_SIGN + testSubject + UNIT_SUFFIX
                + BLANK_SPACE);
        assertEquals(testSubject, actualValue, DELTA);
    }

    @Test
    public void givenUnitPriceIsWholeNumberThenReturnZero() {
        int testSubject = 12;

        Double actualValue = extractor.value( POUND_SIGN + testSubject + UNIT_SUFFIX);
        assertEquals(NumberUtils.DOUBLE_ZERO, actualValue, DELTA);
    }

    @Test
    public void givenUnitPriceIsRandomCHaractersThenReturnZero() {
        Double actualValue = extractor.value( RANDOM_CHARS);
        assertEquals(NumberUtils.DOUBLE_ZERO, actualValue, DELTA);
    }

    @Test
    public void givenUnitPriceWithoutCurrencySymbolThenReturnExtractedValue() {
        Double testSubject = 12.01;
        Double actualValue = extractor.value(testSubject + UNIT_SUFFIX);
        assertEquals(testSubject, actualValue, DELTA);

    }

    @Test
    public void givenUnitPriceWithoutUnitSuffixThenReturnExtractedValue() {
        Double testSubject = 12.01;
        Double actualValue = extractor.value(POUND_SIGN + testSubject );
        assertEquals(testSubject, actualValue, DELTA);

    }

    @Test
    public void givenUnitPriceWithoutCurrencfySymbolAndUnitSuffixThenReturnExtractedValue() {
        Double testSubject = 12.01;
        Double actualValue = extractor.value(testSubject.toString() );
        assertEquals(testSubject, actualValue, DELTA);

    }
}
