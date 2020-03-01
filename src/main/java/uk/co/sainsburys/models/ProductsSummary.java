package uk.co.sainsburys.models;

public class ProductsSummary {

    private double gross;
    private double vat;

    private ProductsSummary(double gross, double vat) {
        this.gross = gross;
        this.vat = vat;
    }

    public double getGross() {
        return gross;
    }

    public double getVat() {
        return vat;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private double gross;
        private double vat;

        public Builder gross(double gross) {
            this.gross = gross;
            return this;
        }
        public Builder vat(double vat) {
            this.vat = vat;
            return this;
        }
        public ProductsSummary build() {
            return new ProductsSummary(gross, vat);
        }
    }
}
