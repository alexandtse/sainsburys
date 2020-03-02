package uk.co.sainsburys.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.co.sainsburys.models.Basket;

@Component
public class BasketToJsonParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasketToJsonParser.class);
    private ObjectMapper mapper;

    public BasketToJsonParser() {
        mapper = new ObjectMapper();
    }

    public String toJson(Basket basket) throws JsonProcessingException{
        String dataInJson = "";
        try {
            dataInJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(basket);
            System.out.println(dataInJson);
            LOGGER.info(dataInJson);
        } catch(JsonProcessingException je) {
            LOGGER.error("problem occur when parsing object to json string" + je.getMessage());
        }
        return dataInJson;
    }

    /**
     * This method is for unit test only
     */
    protected void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
