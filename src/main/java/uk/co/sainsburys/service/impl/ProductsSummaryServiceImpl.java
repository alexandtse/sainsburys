package uk.co.sainsburys.service.impl;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.co.sainsburys.models.Product;
import uk.co.sainsburys.models.ProductsSummary;
import uk.co.sainsburys.service.ProductsSummaryService;

import java.util.List;

@Service
public class ProductsSummaryServiceImpl implements ProductsSummaryService {


    @Value("${product.vatRate:20}")
    private int vatRate;

    public ProductsSummary getProductsSummary(List<Product> products) {
        Double gross = this.calculateGross(products);
        Double vat = this.calculateVat(gross);

        return ProductsSummary.builder()
                .gross(gross)
                .vat(vat)
                .build();
    }

    /**
     * This method is for unit test only.
     */
    protected void setVatRate(int vatRate) {
        this.vatRate = vatRate;
    }

    private Double calculateGross(List<Product> products) {
        return products.stream()
                .mapToDouble(p -> p.getPrice())
                .sum();
    }
    private Double calculateVat(Double gross) {
        Double vat = NumberUtils.DOUBLE_ZERO;
        if(vatRate != 0) {
            vat = gross * (vatRate /100d);
        }
        return vat;
    }
}
