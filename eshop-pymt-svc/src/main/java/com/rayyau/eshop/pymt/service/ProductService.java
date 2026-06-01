package com.rayyau.eshop.pymt.service;

import com.rayyau.eshop.pymt.entity.ProductEntity;
import com.rayyau.eshop.pymt.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private static final String MSFT_QUOTE_URL = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=MSFT";
    private static final String STOCK_API_USER_AGENT = "Mozilla/5.0";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private final ProductRepository productRepository;

    public List<ProductEntity> getAllProductsOnSell() {
        List<ProductEntity> productEntities = productRepository.findAllByIsOnSell(true);
        if (productEntities.isEmpty()) {
            throw new IllegalArgumentException("No products on sell");
        } else {
            return productEntities;
        }
    }

    public List<ProductEntity> addProductsOnSell(List<ProductEntity> products) throws RuntimeException {
        try {
            return productRepository.saveAll(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add products on sell: " + e.getMessage());
        }
    }

    @Tool(
            name = "get_all_products",
            description = "Returns the name, type, and price of all available products. " +
                    "Use this when the user asks for the product list, available items, " +
                    "or what products exist."
    )
    public String getAllProducts() {
        System.out.println("Tool 'get_all_products' was called");

        record Product(String name, String type, String price) {}

        List<Product> products = List.of(
                new Product("iPhone 14 Pro Max", "Mobile", "109,900"),
                new Product("Samsung Galaxy S23 Ultra", "Mobile", "119,999"),
                new Product("OnePlus 11 Pro", "Mobile", "69,999"),
                new Product("Google Pixel 7 Pro", "Mobile", "89,999")
        );

        // Return a clean, readable string for the LLM
        StringBuilder sb = new StringBuilder("Available products:\n");
        for (Product p : products) {
            sb.append("- ").append(p.name)
                    .append(" (").append(p.type).append(") - ₹").append(p.price).append("\n");
        }
        return sb.toString();
    }

    @Tool(
            name = "get_one_products",
            description = "Returns the name, type, and price of the first product fetched in db"
    )
    public String getOneProducts() {
        System.out.println("Tool 'get_one_products' was called");

        record Product(String name, String type, String price) {}

        List<Product> products = List.of(
                new Product("iPhone 14 Pro Max", "Mobile", "109,900")
        );

        // Return a clean, readable string for the LLM
        StringBuilder sb = new StringBuilder("Available products:\n");
        for (Product p : products) {
            sb.append("- ").append(p.name)
                    .append(" (").append(p.type).append(") - ₹").append(p.price).append("\n");
        }
        return sb.toString();
    }

    @Tool(
            name = "default_response",
            description = "This is the default response when the user input doesn't match any other tool. "
    )
    public String getDefaultResponse() {
        return "Action not recognized. Please ask our customer service.";
    }

    @Tool(
            name = "get_msft_stock_price",
            description = "Returns the latest MSFT (Microsoft) stock price in USD. " +
                    "Use this when the user asks for Microsoft's stock price or asks what the stock price of MSFT is now."
    )
    public String getMsftStockPrice() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(MSFT_QUOTE_URL))
                    .header("User-Agent", STOCK_API_USER_AGENT)
                    .GET()
                    .build();
            String responseBody = HTTP_CLIENT
                    .send(request, HttpResponse.BodyHandlers.ofString())
                    .body();
            BigDecimal stockPrice = extractMsftStockPrice(responseBody);
            if (stockPrice == null) {
                return "Unable to retrieve MSFT stock price right now.";
            }
            return "MSFT current stock price is $" + stockPrice + " USD.";
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return "Unable to retrieve MSFT stock price right now.";
        } catch (IOException ex) {
            return "Unable to retrieve MSFT stock price right now.";
        }
    }

    static BigDecimal extractMsftStockPrice(String responseBody) throws IOException {
        JsonNode resultNode = OBJECT_MAPPER.readTree(responseBody)
                .path("quoteResponse")
                .path("result");
        if (!resultNode.isArray() || resultNode.isEmpty()) {
            return null;
        }
        JsonNode priceNode = resultNode.get(0).path("regularMarketPrice");
        if (!priceNode.isNumber()) {
            return null;
        }
        return priceNode.decimalValue();
    }
}
