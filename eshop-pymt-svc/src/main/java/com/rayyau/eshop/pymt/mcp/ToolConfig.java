package com.rayyau.eshop.pymt.mcp;

import com.rayyau.eshop.pymt.service.OrderService;
import com.rayyau.eshop.pymt.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@AllArgsConstructor
public class ToolConfig {

    private final ProductService productService;
    private final OrderService orderService;

    @Bean
    public List<ToolCallback> productTools() {
        return List.of(ToolCallbacks.from(productService, orderService));
    }
}