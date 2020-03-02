package uk.co.sainsburys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uk.co.sainsburys.models.Basket;
import uk.co.sainsburys.models.Product;
import uk.co.sainsburys.models.ProductsSummary;
import uk.co.sainsburys.service.BasketService;
import uk.co.sainsburys.service.ProductService;
import uk.co.sainsburys.service.ProductsSummaryService;

import java.util.List;

@Service
public class BasketServiceImpl implements BasketService {

    private ProductsSummaryService productsSummaryService;
    private ProductService productService;

    @Autowired
    public BasketServiceImpl(ProductsSummaryService productsSummaryService,
                                  @Qualifier("productService") ProductService productService) {
        this.productsSummaryService = productsSummaryService;
        this.productService = productService;
    }

    public Basket getBasket() {
        List<Product> products =  productService.getProduct();
        ProductsSummary summary = ProductsSummary.builder().build();
        if (!products.isEmpty()) {
            summary = productsSummaryService.getProductsSummary(products);
        }
        return Basket.builder()
                .products(products)
                .summary(summary)
                .build();
    }
}
