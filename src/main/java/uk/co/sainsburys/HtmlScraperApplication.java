package uk.co.sainsburys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.co.sainsburys.models.Basket;
import uk.co.sainsburys.parser.BasketToJsonParser;
import uk.co.sainsburys.service.BasketService;

@SpringBootApplication
public class HtmlScraperApplication implements CommandLineRunner {

    private BasketService basketService;
    private BasketToJsonParser parser;

    @Autowired
    public HtmlScraperApplication(BasketService basketService, BasketToJsonParser parser) {
        this.basketService = basketService;
        this.parser = parser;
    }

    public static void main(String[] args) {
        SpringApplication.run(HtmlScraperApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {
        Basket basket = basketService.getBasket();
        parser.toJson(basket);
    }
}
