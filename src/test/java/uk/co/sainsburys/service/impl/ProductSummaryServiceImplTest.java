package uk.co.sainsburys.service.impl;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.sainsburys.models.Product;
import uk.co.sainsburys.models.ProductsSummary;
import uk.co.sainsburys.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ProductSummaryServiceImplTest {

    private static final int VAT_RATE = 20;
    private static final double DELTA = 0.001;
    private static final double PRODUCT_PRICE = 50d;
    private static final double GROSS = 100d;
    private static final double VAT = 20d;

    private ProductsSummaryServiceImpl productsSummaryService;


    @Mock
    private ProductService mockProductService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        productsSummaryService = new ProductsSummaryServiceImpl(mockProductService);
        productsSummaryService.setVatRate(VAT_RATE);
    }
    @Test
    public void givenProductListIsEmptyThenGrossAndVatIsZero() {
        when(mockProductService.getProduct()).thenReturn(new ArrayList<Product>());

        ProductsSummary summary = productsSummaryService.getProductsSummary();
        assertEquals(0d, summary.getGross(), DELTA);
        assertEquals(0d, summary.getVat(), DELTA);

    }

    @Test
    public void givenProductListIsNotEmptyThenReturnNonZeroGrossAndVat() {
        when(mockProductService.getProduct()).thenReturn(this.setupProducts());

        ProductsSummary summary = productsSummaryService.getProductsSummary();
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
