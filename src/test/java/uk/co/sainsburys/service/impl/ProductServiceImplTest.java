package uk.co.sainsburys.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.sainsburys.models.Product;
import uk.co.sainsburys.parser.HtmlParser;
import uk.co.sainsburys.service.ProductService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class ProductServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImplTest.class);
    private static final String VALID_URL = "https://test.com";
    private static final String TITLE1 = "title1";
    private static final String DESCRIPTION = "description";
    private static final Double PRICE1 = 12.05;
    private static final String TITLE2 = "title2";
    private static final Double PRICE2 = 22.05;
    private static final Integer CALORIES = 300;
    private static final String HTML_WITHOUT_DESCRIPTION_CALORIES = "<html><head><title>title</title></head><body><p>test</p></body></html>";
    private static final String HTML_WITH_DESCRIPTION_CALORIES = "<html><head><title>title</title></head><body><div class='productText'><p>"
            + DESCRIPTION + "</p></div><div class='productText'><p>something</p></div><table><tr class='tableRow0'><td>"
            + CALORIES + "kcal</td><td>something</td></tr></table></body></html>";


    @Mock
    private ProductService mockBaseProductService;
    @Mock
    private HtmlParser mockParser;

    private ProductService subject;
    private URL url;
    private Product baseProduct1;
    private Product baseProduct2;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        subject = new ProductServiceImpl(mockBaseProductService, mockParser);
        try {
            url = new URL(VALID_URL);
        } catch (MalformedURLException e) {
            LOGGER.error("Fail to create url at unit test");
        }
        baseProduct1 = Product.builder().title(TITLE1).price(PRICE1).url(url).build();
        baseProduct2 = Product.builder().title(TITLE2).price(PRICE2).url(url).build();

        when(mockBaseProductService.getProduct()).thenReturn(this.setupBaseProducts());
    }

    @Test
    public void givenHtmlParserCreatesDocumentWithoutDivsForDescriptionAndCaloriesThenReturnBaseProductList()
            throws IOException {
        Document doc = Jsoup.parse(HTML_WITHOUT_DESCRIPTION_CALORIES);
        when(mockParser.retrieveHtmlDocument(url)).thenReturn(doc).thenReturn(doc);
        List<Product> products = subject.getProduct();
        assertEquals(this.setupBaseProducts().size(), products.size());
        assertEquals(baseProduct1.getTitle(), products.get(0).getTitle());
        assertEquals(baseProduct2.getTitle(), products.get(1).getTitle());

    }

    @Test
    public void givenHtmlParseThrowsIOExceptionThenReturnBaseProducts() throws IOException{
        Document doc = Jsoup.parse(HTML_WITHOUT_DESCRIPTION_CALORIES);
        when(mockParser.retrieveHtmlDocument(url)).thenThrow(new IOException());
        List<Product> products = subject.getProduct();
        assertEquals(this.setupBaseProducts().size(), products.size());
        assertEquals(baseProduct1, products.get(0));
        assertEquals(baseProduct2, products.get(1));
    }

    @Test
    public void givenHtmlParserCreatesDocumentWithDivsForDescriptionAndCaloriesThenReturnProductsWirhDescriptionAndCalories()
        throws IOException {
        Document doc = Jsoup.parse(HTML_WITH_DESCRIPTION_CALORIES);
        when(mockParser.retrieveHtmlDocument(url)).thenReturn(doc).thenReturn(doc);
        List<Product> products = subject.getProduct();
        assertEquals(this.setupBaseProducts().size(), products.size());
        assertEquals(DESCRIPTION, products.get(0).getDescription());
        assertEquals(CALORIES, products.get(0).getCalories());
        assertEquals(DESCRIPTION, products.get(1).getDescription());
        assertEquals(CALORIES, products.get(1).getCalories());
    }

    private List<Product> setupBaseProducts() {

        List<Product> products = new ArrayList<>();
        products.add(baseProduct1);
        products.add(baseProduct2);
        return products;

    }

}
