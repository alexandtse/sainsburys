package uk.co.sainsburys.models;

import java.net.URL;

public class Product {

    private String title;
    private String description;
    private Integer calories;
    private double price;
    private URL url;

    private Product(String title, String description, Integer calories, double price, URL url) {
        this.title = title;
        this.description = description;
        this.calories = calories;
        this.price = price;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCalories() {
        return calories;
    }

    public double getPrice() {
        return price;
    }

    public URL getUrl() {
        return url;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private String title;
        private String description;
        private Integer calories;
        private double price;
        private URL url;

        public Builder title(String title) {
            this.title = title;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder calories(Integer calories) {
            this.calories = calories;
            return this;
        }
        public Builder price(double price) {
            this.price = price;
            return this;
        }
        public Builder url(URL url) {
            this.url = url;
            return this;
        }
        public Product build() {
            return new Product(title, description, calories, price, url);
        }
    }
}
