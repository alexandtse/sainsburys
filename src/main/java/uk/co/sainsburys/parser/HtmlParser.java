package uk.co.sainsburys.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;

public class HtmlParser {

    public Document retrieveHtmlDocument(URL url) throws IOException {
        return Jsoup.connect(url.toString()).get();
    }
}