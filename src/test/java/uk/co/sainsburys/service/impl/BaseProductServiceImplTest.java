package uk.co.sainsburys.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.sainsburys.models.Product;
import uk.co.sainsburys.parser.HtmlParser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class BaseProductServiceImplTest {


    private static final double UNIT_PRICE1 = 12.25;
    private static final double UNIT_PRICE2 = 22.25;
    private static final String PRODUCT_TITLE1 = "product1";
    private static final String PRODUCT_TITLE2 = "product2";
    private static final String HTML_FILE_NAME = "test.html";
    private static final String URL_STRING = "https://test.com";
    private static final String MALFORMED_URL = "www.test.com";
    private static final String URL_SUB_PATH = "/1/2/test.html";

    private static final String HTML_WITHOUT_PRODUCT_DIVS = "<html><head><title>title</title></head><body><p>test</p></body></html>";
    private static final String HTML_WITH_ONE_PRODUCT_DIVS = "<html><head><title>title</title></head><body><div class='product'><div class='productNameAndPromotions'><a href='../../"
            + HTML_FILE_NAME + "'>"
            + PRODUCT_TITLE1 + "</a></div><p class='pricePerUnit'>"
            + UNIT_PRICE1 + "<abbr title='per'>/</abbr><abbr title='unit'><span class='pricePerUnitUnit'>unit</span></abbr></p></div></body></html>";
    private static final String HTML_WITH_ONE_PRODUCT_DIVS_ABSOLUTE_PATH = "<html><head><title>title</title></head><body><div class='product'><div class='productNameAndPromotions'><a href='"
            + URL_STRING + "/" + HTML_FILE_NAME + "'>"
            + PRODUCT_TITLE1 + "</a></div><p class='pricePerUnit'>"
            + UNIT_PRICE1 + "<abbr title='per'>/</abbr><abbr title='unit'><span class='pricePerUnitUnit'>unit</span></abbr></p></div></body></html>";
    private static final String HTML_WITH_ONE_PRODUCT_DIVS_MALFORMED_PATH = "<html><head><title>title</title></head><body><div class='product'><div class='productNameAndPromotions'><a href='"
            + MALFORMED_URL + "/" + HTML_FILE_NAME + "'>"
            + PRODUCT_TITLE1 + "</a></div><p class='pricePerUnit'>"
            + UNIT_PRICE1 + "<abbr title='per'>/</abbr><abbr title='unit'><span class='pricePerUnitUnit'>unit</span></abbr></p></div></body></html>";
    private static final String HTML_WITH_TWO_PRODUCT_DIVS = "<html><head><title>title</title></head><body><div class='product'><div class='productNameAndPromotions'><a href='../../"
            + HTML_FILE_NAME+ "'>"
            + PRODUCT_TITLE1 + "</a></div><p class='pricePerUnit'>"
            + UNIT_PRICE1 + "<abbr title='per'>/</abbr><abbr title='unit'><span class='pricePerUnitUnit'>unit</span></abbr></p></div><div class='product'><div class='productNameAndPromotions'><a href='../../"
            + HTML_FILE_NAME+ "'>"
            + PRODUCT_TITLE2 + "</a></div><p class='pricePerUnit'>"
            + UNIT_PRICE2 + "<abbr title='per'>/</abbr><abbr title='unit'><span class='pricePerUnitUnit'>unit</span></abbr></p></div</body></html>";

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseProductServiceImplTest.class);
    private static final double DELTA = 0.001;

    @Mock
    private HtmlParser mockParser;

    private URL url;
    private BaseProductServiceImpl subject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        subject = new BaseProductServiceImpl(mockParser);
        try {
            url = new URL(URL_STRING + URL_SUB_PATH);
        } catch (MalformedURLException e) {
            LOGGER.error("Fail to create url at unit test");
        }
    }

    @Test
    public void givenHtmlParserCreatesDocumentWithoutProductDivsThenReturnEmptyProductList() throws IOException {
        subject.setProductListingUrl(URL_STRING + URL_SUB_PATH);
        Document doc = Jsoup.parse(HTML_WITHOUT_PRODUCT_DIVS);
        when(mockParser.retrieveHtmlDocument(url)).thenReturn(doc);
        assertTrue(subject.getBaseProduct().isEmpty());
    }

    @Test
    public void givenUrlIsMalformedUrlThenReturnEmptyProductList() {
        subject.setProductListingUrl("www.test.com");
        assertTrue(subject.getBaseProduct().isEmpty());
    }

    @Test
    public void givenHtmlParserIsThrowingIoExceptionWhenCreatingDocumentThenReturnEmptyProductList() throws IOException{
        subject.setProductListingUrl(URL_STRING + URL_SUB_PATH);
        when(mockParser.retrieveHtmlDocument(url)).thenThrow(new IOException());
        assertTrue(subject.getBaseProduct().isEmpty());
    }

    @Test
    public void givenHtmlParserCreatesDocumentWithOneProductDivThenReturnOneProductFromList() throws IOException{
        subject.setProductListingUrl(URL_STRING + URL_SUB_PATH);
        Document doc = Jsoup.parse(HTML_WITH_ONE_PRODUCT_DIVS);
        when(mockParser.retrieveHtmlDocument(url)).thenReturn(doc);
        List<Product> products = subject.getBaseProduct();
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals(PRODUCT_TITLE1, products.get(0).getTitle());
        assertEquals(UNIT_PRICE1, products.get(0).getPrice(), DELTA);
        assertEquals(URL_STRING + "/" + HTML_FILE_NAME, products.get(0).getUrl().toString());
    }

    @Test
    public void givenHtmlParserCreatesDocumentWithOneProductDivThenReturnTwoProductFromList() throws IOException{
        subject.setProductListingUrl(URL_STRING + URL_SUB_PATH);
        Document doc = Jsoup.parse(HTML_WITH_TWO_PRODUCT_DIVS);
        when(mockParser.retrieveHtmlDocument(url)).thenReturn(doc);
        List<Product> products = subject.getBaseProduct();
        assertFalse(products.isEmpty());
        assertEquals(2, products.size());
        assertEquals(PRODUCT_TITLE1, products.get(0).getTitle());
        assertEquals(UNIT_PRICE1, products.get(0).getPrice(), DELTA);
        assertEquals(URL_STRING + "/" + HTML_FILE_NAME, products.get(0).getUrl().toString());
        assertEquals(PRODUCT_TITLE2, products.get(1).getTitle());
        assertEquals(UNIT_PRICE2, products.get(1).getPrice(), DELTA);
        assertEquals(URL_STRING + "/" + HTML_FILE_NAME, products.get(1).getUrl().toString());
    }

    @Test
    public void givenHtmlParserCreatesDocumentWithOneProductDivWithAbsolutePathThenReturnOneProductFromList() throws IOException{
        subject.setProductListingUrl(URL_STRING + URL_SUB_PATH);
        Document doc = Jsoup.parse(HTML_WITH_ONE_PRODUCT_DIVS_ABSOLUTE_PATH);
        when(mockParser.retrieveHtmlDocument(url)).thenReturn(doc);
        List<Product> products = subject.getBaseProduct();
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals(PRODUCT_TITLE1, products.get(0).getTitle());
        assertEquals(UNIT_PRICE1, products.get(0).getPrice(), DELTA);
        assertEquals(URL_STRING + "/" + HTML_FILE_NAME, products.get(0).getUrl().toString());
    }

    @Test
    public void givenHtmlParserCreatesDocumentWithOneProductDivWithMalformedUrlThenReturnOneProductFromListWithNullUrl() throws IOException{
        subject.setProductListingUrl(URL_STRING + URL_SUB_PATH);
        Document doc = Jsoup.parse(HTML_WITH_ONE_PRODUCT_DIVS_MALFORMED_PATH);
        when(mockParser.retrieveHtmlDocument(url)).thenReturn(doc);
        List<Product> products = subject.getBaseProduct();
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals(PRODUCT_TITLE1, products.get(0).getTitle());
        assertEquals(UNIT_PRICE1, products.get(0).getPrice(), DELTA);
        assertNull(products.get(0).getUrl());
    }
}
