package uk.co.sainsburys.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.sainsburys.models.Basket;
import uk.co.sainsburys.models.Product;
import uk.co.sainsburys.models.ProductsSummary;
import uk.co.sainsburys.service.ProductService;
import uk.co.sainsburys.service.ProductsSummaryService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class BasketServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasketServiceImplTest.class);
    private static final String URL_STRING = "https://test.com";
    private static final String DESCRIPTION = "description";
    private static final int CALORIES = 100;
    private static final String TITLE = "title";
    private static final double PRODUCT_PRICE = 50d;
    private static final double GROSS = 100d;
    private static final double VAT = 20d;
    private static final double DELTA = 0.001;

    @Mock
    private ProductService mockProductService;
    @Mock
    private ProductsSummaryService mockProductsSummaryService;

    private BasketServiceImpl basketService;
    private URL url;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        basketService = new BasketServiceImpl(mockProductsSummaryService, mockProductService);
        try {
            url = new URL(URL_STRING);
        } catch (MalformedURLException e) {
            LOGGER.error("Fail to create url at unit test");
        }
        List<Product> products = this.setupProducts();
        when(mockProductService.getProduct()).thenReturn(products);
        when(mockProductsSummaryService.getProductsSummary(products)).thenReturn(this.setupSummary());
    }

    @Test
    public void givenProductsIsEmptyThenReturnBasketWithEmptyProductsAndEmptySummary() {
        when(mockProductService.getProduct()).thenReturn(new ArrayList<Product>());
        Basket basket = basketService.getBasket();
        assertTrue(basket.getProducts().isEmpty());
        assertEquals(0d, basket.getSummary().getGross(), DELTA);
        assertEquals(0d, basket.getSummary().getVat(), DELTA);
    }

    @Test
    public void givenProductsIsNotEmptyThenReturnBasketWithProductsAndSummary() {
        Basket basket = basketService.getBasket();
        assertFalse(basket.getProducts().isEmpty());
        assertEquals(GROSS, basket.getSummary().getGross(), DELTA);
        assertEquals(VAT, basket.getSummary().getVat(), DELTA);

    }


    private List<Product> setupProducts() {
        List<Product> products = new ArrayList<>();
        Product product = Product.builder()
                .price(PRODUCT_PRICE)
                .url(url)
                .title(TITLE)
                .description(DESCRIPTION)
                .calories(CALORIES)
                .build();
        products.add(product);
        products.add(product);

        return products;
    }

    private ProductsSummary setupSummary() {
        return ProductsSummary.builder()
                .gross(GROSS)
                .vat(VAT)
                .build();

    }}
