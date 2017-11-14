package com.github.dmitritim.app;

import com.github.dmitritim.app.domain.model.StockItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@RestController
@RequestMapping(value = "/stock", produces = MediaType.APPLICATION_JSON_VALUE)
public class StockResource {

    private static final Logger logger = LoggerFactory.getLogger(StockResource.class);

    @Autowired
    private Environment environment;

    @Autowired
    private StockItemRepository stockItemRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public StockItem stockItem(@PathVariable("id") Long id, HttpServletResponse response) {
        logger.info("Starting search of stock item(id={}) search", id);

        StockItem stockItem = stockItemRepository.findOne(id);

        if (stockItem == null) {
            logger.info("Stock item(id={}) has not been found", id);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }

        logger.info("Finishing search of stock item(id={}) search", id);
        return stockItem;

    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public StockItem storeInStock(@RequestBody StockItem stockItem, HttpServletResponse response) {
        StockItem storedItem = stockItemRepository.save(stockItem);

        response.setStatus(HttpStatus.CREATED.value());

        return storedItem;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public StockItem update(@RequestBody StockItem stockItem, HttpServletResponse response) {
        if (!stockItemRepository.exists(stockItem.getId())) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return stockItem;
        }

        return stockItemRepository.save(stockItem);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeFromStock(@PathVariable("id") Long id, HttpServletResponse response) {
        StockItem stockItemToRemove = stockItemRepository.findOne(id);

        if (stockItemToRemove == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }

        stockItemRepository.delete(stockItemToRemove);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<StockItem> items() {
        return stockItemRepository.findAll();
    }


    @RequestMapping(value = "/get-env", method = RequestMethod.GET)
    public String getEnv(@RequestParam String env) {
        return "Env " + environment.getProperty(env);
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        return "Pong pong";
    }

    @RequestMapping(value = "/mnogabukav", method = RequestMethod.GET)
    public String getMnogoTeksta() {
        String output = "Start ";
        String somestring = "";
        for (int i = 0; i < 100000; i++) {
            output += getRandomChar();
            somestring += getRandomChar();
            if ( i%100==0 ) {
                logger.info("Otkr6vaj boroda is here{} ", somestring);
                somestring="";
            }
        }

        output += " End";

        return output;
    }

    private String getRandomChar () {
        Random rnd = new Random();
        char chr = (char)(rnd.nextInt(60));
        return String.valueOf(chr);
    }
}
