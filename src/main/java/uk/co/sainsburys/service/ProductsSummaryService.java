package uk.co.sainsburys.service;

import uk.co.sainsburys.models.Product;
import uk.co.sainsburys.models.ProductsSummary;

import java.util.List;

public interface ProductsSummaryService {
    ProductsSummary getProductsSummary(List<Product> products);
}
