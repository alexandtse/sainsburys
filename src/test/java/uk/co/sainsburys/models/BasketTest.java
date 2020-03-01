package uk.co.sainsburys.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BasketTest {

    private static final String TITLE_1 = "title1";
    private static final String TITLE_2 = "title2";
    private static final double GROSS = 12.00;

    private List<Product> products;
    private ProductsSummary summary;

    @Before
    public void setup() {
        products = this.createTestProducts();
        summary = ProductsSummary.builder().gross(GROSS).build();
    }

    @Test
    public void testAllArgs() {
        Basket actualBasket = Basket.builder().products(products).summary(summary).build();
        assertEquals(products.size(),actualBasket.getProducts().size());
        assertEquals(TITLE_1,actualBasket.getProducts().get(0).getTitle());
        assertEquals(TITLE_2,actualBasket.getProducts().get(1).getTitle());
        assertEquals(summary, actualBasket.getSummary());

    }
    @Test
    public void testNullArg() {
        Basket actualBasket = Basket.builder().products(products).build();
        assertEquals(products.size(),actualBasket.getProducts().size());
        assertEquals(TITLE_1,actualBasket.getProducts().get(0).getTitle());
        assertEquals(TITLE_2,actualBasket.getProducts().get(1).getTitle());
        assertNull( actualBasket.getSummary());
    }

    private List<Product> createTestProducts() {
        List<Product> testProducts = new ArrayList<>();
        Product product1 = Product.builder().title(TITLE_1).build();
        Product product2 = Product.builder().title(TITLE_2).build();
        testProducts.add(product1);
        testProducts.add(product2);
        return testProducts;
    }


}
