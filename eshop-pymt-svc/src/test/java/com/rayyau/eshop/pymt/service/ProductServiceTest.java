package com.rayyau.eshop.pymt.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductServiceTest {

    @Test
    void extractMsftStockPrice_shouldReturnPriceFromYahooResponse() throws IOException {
        String responseBody = """
                {
                  "quoteResponse": {
                    "result": [
                      {
                        "symbol": "MSFT",
                        "regularMarketPrice": 512.34
                      }
                    ]
                  }
                }
                """;

        BigDecimal result = ProductService.extractMsftStockPrice(responseBody);

        assertEquals(new BigDecimal("512.34"), result);
    }

    @Test
    void extractMsftStockPrice_shouldReturnNullWhenPriceMissing() throws IOException {
        String responseBody = """
                {
                  "quoteResponse": {
                    "result": [
                      {
                        "symbol": "MSFT"
                      }
                    ]
                  }
                }
                """;

        BigDecimal result = ProductService.extractMsftStockPrice(responseBody);

        assertNull(result);
    }
}
