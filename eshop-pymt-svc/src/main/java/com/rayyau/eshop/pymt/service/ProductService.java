package com.rayyau.eshop.pymt.service;

import com.rayyau.eshop.pymt.entity.ProductEntity;
import com.rayyau.eshop.pymt.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductEntity> getAllProductsOnSell() {
        List<ProductEntity> productEntities = productRepository.findAllByIsOnSell(true);
        if (productEntities.isEmpty()) {
            throw new IllegalArgumentException("No products on sell");
        } else {
            return productEntities;
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
}
