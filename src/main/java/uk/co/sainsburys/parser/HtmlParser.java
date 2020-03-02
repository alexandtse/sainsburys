package uk.co.sainsburys.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class HtmlParser {

    public Document retrieveHtmlDocument(URL url) throws IOException {
        return Jsoup.connect(url.toString()).get();
    }
}