package uk.co.sainsburys.parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public class HtmlParserTest {

    private static final String VALID_URL = "https://google.com";
    private static final String TEST_CONTENT = "test";

    @Mock
    private Connection mockConnection;
    @Mock
    private Document mockDocument;


    private HtmlParser parser;

    private Document doc;

    @Before
    public void setup() throws IOException{
        MockitoAnnotations.initMocks(this);

        mockStatic(Jsoup.class);

        when(Jsoup.connect(VALID_URL)).thenReturn(mockConnection);
        when(mockConnection.get()).thenReturn(mockDocument);
    }

    @Test
    public void testRetrieveDocument() throws IOException {
        URL validUrl = new URL(VALID_URL);

        parser = new HtmlParser();
        assertEquals(mockDocument, parser.retrieveHtmlDocument(validUrl));
    }
}
