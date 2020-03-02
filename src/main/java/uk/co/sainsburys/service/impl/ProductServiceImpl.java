package uk.co.sainsburys.service.impl;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uk.co.sainsburys.models.Product;
import uk.co.sainsburys.parser.HtmlParser;
import uk.co.sainsburys.service.ProductService;
import uk.co.sainsburys.utils.CaloriesExtractor;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    private static final String DESCRIPTION_P_TAG_SELECTOR = "div.productText > p";
    private static final String CALORIES_TD_TAG_SELECTOR = "tr.tableRow0 >td:first-child";

    private ProductService baseProductService;
    private HtmlParser parser;
    private CaloriesExtractor extractor;

    @Autowired
    public ProductServiceImpl(@Qualifier("baseProductService") ProductService baseProductService, HtmlParser parser)  {
        this.baseProductService = baseProductService;
        this.parser = parser;
    }

    @Override
    public List<Product> getProduct() {
        List<Product> baseProducts = baseProductService.getProduct();
        return this.populateAdditionalProductDetails(baseProducts);
    }

    private List<Product> populateAdditionalProductDetails(List<Product> baseProducts) {
        return baseProducts.stream()
                .map(this::retrieveAdditionalDetails)
                .collect(Collectors.toList());
    }

    private Product retrieveAdditionalDetails(Product baseProduct) {
        URL url = baseProduct.getUrl();
        Product product = baseProduct;
        try {
            Document doc = parser.retrieveHtmlDocument(url);
            String description = this.retrieveDescription(doc);
            int calories = this.retrieveCalories(doc);
            product = Product.builder()
                    .description(description)
                    .calories(calories)
                    .url(baseProduct.getUrl())
                    .title(baseProduct.getTitle())
                    .price(baseProduct.getPrice()).build();
        } catch (IOException e) {
            LOGGER.error("Cannot retrieve document : " + e.getMessage());
        }
        return product;

    }
    private String retrieveDescription(Document doc) {
        Element pTag = doc.selectFirst(DESCRIPTION_P_TAG_SELECTOR);
        String description = "";
        if (null != pTag) {
            description = pTag.text();
        }
        return description;
    }
    private int retrieveCalories(Document doc) {
        Element tdTag = doc.selectFirst(CALORIES_TD_TAG_SELECTOR);
        int calories = NumberUtils.INTEGER_ZERO;
        if (null != tdTag) {
            extractor = new CaloriesExtractor();
            calories = extractor.value(tdTag.text());
        }
        return calories;
    }
}
