package uk.co.sainsburys.models;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class ProductsSummaryTest {

    private static final double GROSS = 5.01;
    private static final double VAT = 0.20;
    private static final double DELTA = 0.01;

    @Test
    public void testAllArgs() {
        ProductsSummary actualSummary = ProductsSummary.builder()
                .gross(GROSS)
                .vat(VAT)
                .build();
        assertEquals(GROSS, actualSummary.getGross(), DELTA);
        assertEquals(VAT, actualSummary.getVat(), DELTA);

    }

    @Test
    public void testNullArgs() {
        ProductsSummary actualSummary = ProductsSummary.builder()
                .gross(GROSS)
                .build();

        assertEquals(GROSS, actualSummary.getGross(), DELTA);
        assertEquals(NumberUtils.DOUBLE_ZERO, actualSummary.getVat(), DELTA);
    }
}
