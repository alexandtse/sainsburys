package uk.co.sainsburys.service.impl;

import org.junit.Before;
import org.junit.Test;
import uk.co.sainsburys.models.Product;
import uk.co.sainsburys.models.ProductsSummary;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductSummaryServiceImplTest {

    private static final int VAT_RATE = 20;
    private static final double DELTA = 0.001;
    private static final double PRODUCT_PRICE = 50d;
    private static final double GROSS = 100d;
    private static final double VAT = 20d;

    private ProductsSummaryServiceImpl productsSummaryService;


    @Before
    public void setup() {
        productsSummaryService = new ProductsSummaryServiceImpl();
        productsSummaryService.setVatRate(VAT_RATE);
    }
    @Test
    public void givenProductListIsEmptyThenGrossAndVatIsZero() {

        ProductsSummary summary = productsSummaryService.getProductsSummary(new ArrayList<Product>());
        assertEquals(0d, summary.getGross(), DELTA);
        assertEquals(0d, summary.getVat(), DELTA);

    }

    @Test
    public void givenProductListIsNotEmptyThenReturnNonZeroGrossAndVat() {

        ProductsSummary summary = productsSummaryService.getProductsSummary(this.setupProducts());
        assertEquals(GROSS, summary.getGross(), DELTA);
        assertEquals(VAT, summary.getVat(), DELTA);
    }

    private List<Product> setupProducts() {
        List<Product> products = new ArrayList<>();
        Product product = Product.builder().price(PRODUCT_PRICE).build();
        products.add(product);
        products.add(product);

        return products;
    }
}
