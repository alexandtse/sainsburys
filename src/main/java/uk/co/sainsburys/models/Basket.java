package uk.co.sainsburys.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Basket {
    private List<Product> products;
    private ProductsSummary summary;

    private Basket(List<Product> products, ProductsSummary summary) {
        this.products = products;
        this.summary = summary;
    }
    @JsonProperty("results")
    public List<Product> getProducts() {
        return products;
    }
    @JsonProperty("total")
    public ProductsSummary getSummary() {
        return summary;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private List<Product> products;
        private ProductsSummary summary;

        public Builder products(List<Product> products) {
            this.products = products;
            return  this;
        }

        public Builder summary(ProductsSummary summary) {
            this.summary = summary;
            return this;
        }

        public Basket build() {
            return new Basket(products, summary);
        }
    }

}
