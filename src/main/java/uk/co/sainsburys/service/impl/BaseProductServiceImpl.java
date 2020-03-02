package uk.co.sainsburys.service.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.co.sainsburys.models.Product;
import uk.co.sainsburys.parser.HtmlParser;
import uk.co.sainsburys.service.ProductService;
import uk.co.sainsburys.utils.UnitPriceExtractor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("baseProductService")
public class BaseProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseProductServiceImpl.class);
    private static final String HREF_KEY = "href";
    private static final String PRODUCTS_SELECTOR = "div.product";
    private static final String PRODUCT_DETAILS_ANCHOR_SELECTOR = "div.productNameAndPromotions a";
    private static final String UNIT_PRICE_SELECTOR = "p.pricePerUnit";

    private HtmlParser parser;
    private UnitPriceExtractor extractor;

    @Value("${productListing.url}")
    private String productListingUrl;

    @Autowired
    public BaseProductServiceImpl(HtmlParser parser) {
        this.parser = parser;
    }

    public List<Product> getProduct() {
        Document doc = this.retrieveDocument();
        List<Product> products = new ArrayList<>();
        if(null != doc) {
            products = createProduct(doc);
        }
        return products;
    }

    /**
     * This method is only use in unit test for setting the url.
     */
    protected void setProductListingUrl(String productListingUrl) {
        this.productListingUrl = productListingUrl;
    }

    private Document retrieveDocument() {
        Document doc = null;
        try {
            URL url = new URL(productListingUrl);
            doc = parser.retrieveHtmlDocument(url);
        } catch (MalformedURLException e) {
            LOGGER.error("Cannot create a url object: Invalid url");
        } catch (IOException ie) {
            LOGGER.error("Cannot access the html content of the url");
        }
        return doc;
    }

    private List<Product> createProduct(Document doc) {
        Elements products = doc.select(PRODUCTS_SELECTOR);
        return products.stream().map(this::populateProductData).collect(Collectors.toList());
    }

    private Product populateProductData(Element productElement) {

        Element anchorTag = productElement.select(PRODUCT_DETAILS_ANCHOR_SELECTOR).first();

        String href = anchorTag.attr(HREF_KEY);
        String title = anchorTag.text();

        String formattedUnitPrice = productElement.selectFirst(UNIT_PRICE_SELECTOR).text();
        extractor = new UnitPriceExtractor();
        Double unitPrice = extractor.value(formattedUnitPrice);

        URL url = null;
        try {
            if(isRelativePath(href)) {
                url = new URL(new URL(productListingUrl), href);
            } else {
                url = new URL(href);
            }
        } catch (MalformedURLException e) {
            LOGGER.error("Cannot create a url of the product: " + title + " url: " + href);
        }

        return Product.builder().title(title).url(url).price(unitPrice).build();

    }

    private boolean isRelativePath(String path) {
        boolean returnValue = false;
        if(path.startsWith("/") || path.startsWith("./") || path.startsWith("../")) {
            returnValue = true;
        }
        return returnValue;
    }
}
