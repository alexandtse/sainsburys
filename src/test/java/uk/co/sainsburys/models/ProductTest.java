package uk.co.sainsburys.models;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test the builder class
 */
public class ProductTest {

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final Integer CALORIES = 100;
    private static final double PRICE = 10;
    private static final String URL_STRING = "http://localhost";

    private URL htmlUrl;

    @Test
    public void testAllArgs() throws MalformedURLException{
        htmlUrl = new URL(URL_STRING);
        Product actualProduct = Product.builder()
                .title(TITLE)
                .description(DESCRIPTION)
                .calories(CALORIES)
                .price(PRICE)
                .url(htmlUrl)
                .build();

        assertEquals(TITLE, actualProduct.getTitle());
        assertEquals(DESCRIPTION, actualProduct.getDescription());
        assertEquals(CALORIES, actualProduct.getCalories());
        assertEquals(PRICE, actualProduct.getPrice(), 0.001);
        assertEquals(URL_STRING, actualProduct.getUrl().toString());

    }

    @Test
    public void testNullArgs() throws MalformedURLException {

        htmlUrl = new URL(URL_STRING);
        Product actualProduct = Product.builder()
                .title(TITLE)
                .price(PRICE)
                .url(htmlUrl)
                .build();

        assertEquals(TITLE, actualProduct.getTitle());
        assertNull(actualProduct.getDescription());
        assertNull(actualProduct.getCalories());
        assertEquals(PRICE, actualProduct.getPrice(), 0.001);
        assertEquals(URL_STRING, actualProduct.getUrl().toString());
    }
}
