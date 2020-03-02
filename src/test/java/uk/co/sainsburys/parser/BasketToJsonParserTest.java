package uk.co.sainsburys.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.sainsburys.models.Basket;
import uk.co.sainsburys.models.Product;
import uk.co.sainsburys.models.ProductsSummary;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * TODO: need to think about how to throw protected JsonProcessingException
 */
public class BasketToJsonParserTest {

    private static final String TEST_JSON_STRING = "{\"results\":[],\"total\":{\"gross\":0.00,\"vat\":0.00}}";

    @Mock
    private ObjectMapper mockMapper;
    @Mock
    private ObjectWriter mockWriter;

    private BasketToJsonParser parser;
    private Basket basket;
    private ProductsSummary summary;

    @Before
    public void setup() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        parser = new BasketToJsonParser();
        parser.setMapper(mockMapper);

        summary = ProductsSummary.builder()
                .gross(0d)
                .vat(0d)
                .build();

        basket = Basket.builder()
                .products(new ArrayList<Product>())
                .summary(summary)
                .build();

        when(mockWriter.writeValueAsString(basket)).thenReturn(TEST_JSON_STRING);
    }

    @Test
    public void testSunnyDayScenario() throws JsonProcessingException{
        when(mockMapper.writerWithDefaultPrettyPrinter()).thenReturn(mockWriter);
        assertEquals(TEST_JSON_STRING, parser.toJson(basket));
    }

}
