package com.rayyau.eshop.pymt.service;

import com.rayyau.eshop.payment.library.annotation.UserId;
import com.rayyau.eshop.payment.library.dto.OrderDto;
import com.rayyau.eshop.payment.library.dto.ProductDto;
import com.rayyau.eshop.pymt.dto.Order;
import com.rayyau.eshop.pymt.entity.OrderEntity;
import com.rayyau.eshop.pymt.entity.ProductEntity;
import com.rayyau.eshop.pymt.entity.ShipmentEntity;
import com.rayyau.eshop.pymt.enumeration.OrderStatus;
import com.rayyau.eshop.pymt.enumeration.ShipmentStatus;
import com.rayyau.eshop.pymt.mapper.OrderMapper;
import com.rayyau.eshop.pymt.repository.OrderRepository;
import com.rayyau.eshop.pymt.repository.ProductRepository;
import com.rayyau.eshop.pymt.repository.ShipmentRepository;
import com.rayyau.eshop.security.library.repository.UserRepository;
import com.rayyau.eshop.security.library.security.JwtUtil;
import io.jsonwebtoken.Jwt;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShipmentRepository shipmentRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public List<Order> getOrderByIdAndUserId(Long orderId, Long userId) {

//        return orderRepository.findAllByOrderIdAndUserId(orderId, userId)
//                .orElseThrow(() -> new RuntimeException("Orders not found"))
//                .stream()
//                .map(orderMapper::orderEntityToOrder)
//                .toList();
        return new ArrayList<>();
    }

    @Transactional
    public void saveOrder(OrderDto orderDto, Long userId) {
        log.info("UserId extracted in add-orders: {}", userId);

        OrderEntity orderEntity = orderMapper.orderDtoToOrderEntity(orderDto);
        orderEntity.setUserId(userId);
        orderRepository.saveAndFlush(orderEntity);
        //save shipment record to pending shipment queue
        List<ShipmentEntity> shipmentEntities = new ArrayList<>();
        for (ProductDto product : orderDto.getProducts()) {

            ShipmentEntity shipmentEntity = new ShipmentEntity();
            shipmentEntity.setOrder(orderEntity);
            shipmentEntity.setProductId(product.getProductId());
            shipmentEntity.setQuantity(product.getQuantity());
            shipmentEntity.setStatus(ShipmentStatus.PENDING);
            shipmentEntity.setPrice(product.getPrice());
            shipmentEntities.add(shipmentEntity);
        }
        log.info("shipmentEntities: {}", shipmentEntities);
        shipmentRepository.saveAll(shipmentEntities);

    }

    @Transactional
    public String saveNonPaidOrders(OrderDto orderDto, Long userId) {
        log.info("saving non-paid orders for user with id: {}", userId);
        log.info("order products: {}", orderDto.getProducts());

        OrderEntity orderEntity = orderMapper.orderDtoToOrderEntity(orderDto);
        orderEntity.setUserId(userId);
        orderEntity.setOrderStatus(OrderStatus.PENDING);
        String randomRefId = randomUuidNoHyphens();
        orderEntity.setOrderRefId(randomRefId);
        //set Products
        String productsStr = orderDto.getProducts().stream()
                .map(p -> p.getProductId() + ":" + p.getQuantity())
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        orderEntity.setProducts(productsStr);
        orderRepository.save(orderEntity);

        return randomRefId;
    }

    public OrderEntity getOrderByOrderRefId(String orderRefId) throws NullPointerException {
        Optional<OrderEntity> orderOpt =  orderRepository.findByOrderRefId(orderRefId);
        if(orderOpt.isPresent()) {
            return orderOpt.get();
        } else {
            throw new NullPointerException("Order not found for orderRefId: " + orderRefId);
        }
    }

    @Transactional
    public void saveOrder(OrderEntity orderEntity) {
        orderRepository.saveAndFlush(orderEntity);
    }


    @Tool(
            name = "get_latest_order",
            description = "Returns latest order information for the user. " +
                    "Use this when the user asks for their most recent order details, " +
                    "latest purchase information, or wants to see their last order." +
                    "Order information should include order ref ID, status, total price, and created time."
    )
    public String getLatestOrder(ToolContext toolContext) {

        try {
//            Long userId = (Long) toolContext.getContext().get("userId");
            Long userId = 1L; // TODO: replace with actual userId after the bug is fixed by Spring AI team (https://github.com/spring-projects/spring-ai/issues/4773)
            OrderEntity orderEntity = orderRepository.findTopByUserIdOrderByCreatedAtDesc(userId)
                    .orElseThrow(() -> new RuntimeException("No orders found for user with id: " + userId));

            // Return a clean, readable string for the LLM
            StringBuilder sb = new StringBuilder("Latest Order:\n");
            sb.append("Order ref ID: ").append(orderEntity.getOrderRefId()).append("\n");
            sb.append("Status: ").append(orderEntity.getOrderStatus()).append("\n");
            sb.append("Total Price: ").append(orderEntity.getTotalPrice()).append("\n");
            sb.append("Created At: ").append(orderEntity.getCreatedAt()).append("\n");
            sb.append("\n");
            sb.append("Purchased Products:").append("\n");
            String[] productArray = orderEntity.getProducts().split(",");
            for (String product : productArray) {
                String[] parts = product.split(":");
                if (parts.length == 2) {
                    String productName = "";
                    Optional<ProductEntity> productOpt = productRepository.findFirstById(Long.parseLong(parts[0]));
                    if(productOpt.isPresent()) {
                        productName = productOpt.get().getName();
                    }
                    sb.append("Product ID: ").append(parts[0]).append(", Product name: ").append(productName).append(", Quantity: ").append(parts[1]).append("\n");
                }
            }

            return sb.toString();
        } catch (NumberFormatException ex) {
            log.error("Failed to parse userId from tool context: {}", toolContext.getContext().get("userId"), ex);
            throw new IllegalArgumentException("Invalid userId in tool context");
        }

    }

    @Tool(
            name = "get_testing_order",
            description = "return testing string to user"
    )
    public String getTestingOrder() {
        log.info("Tool 'get_latest_order' was called");

        return "testing order";
    }

    private static String randomUuidNoHyphens() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
