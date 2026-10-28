package com.example.demo.service;




import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.OrderNotFoundException;
import com.example.demo.model.FoodOrder;



@Service
public class FoodOrderService {

    private final List<FoodOrder> orders = new ArrayList<>();
    private Long nextId = 1L;

    // 1. Add a new order
    public FoodOrder addOrder(FoodOrder order) {
        order.setOrderId(nextId++);
        orders.add(order);
        return order;
    }

    // 2. Get all orders
    public List<FoodOrder> getAllOrders() {
        return orders;
    }

    // 3. Get order by ID
    public FoodOrder getOrderById(Long id) {
        return orders.stream()
                .filter(order -> order.getOrderId().equals(id))
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    // 4. Update complete order
    public FoodOrder updateOrder(Long id, FoodOrder updatedOrder) {
        FoodOrder existingOrder = getOrderById(id);

        if (existingOrder == null) {
            return null;
        }

        existingOrder.setCustomerName(updatedOrder.getCustomerName());
        existingOrder.setFoodItem(updatedOrder.getFoodItem());
        existingOrder.setQuantity(updatedOrder.getQuantity());
        existingOrder.setPrice(updatedOrder.getPrice());

        return existingOrder;
    }

    // 5. Delete order by ID
    public boolean deleteOrder(Long id) {
        return orders.removeIf(
                order -> order.getOrderId().equals(id)
        );
    }

    // 6. Count total orders
    public int getTotalOrders() {
        return orders.size();
    }

    // 7. Get orders by customer name
    public List<FoodOrder> getOrdersByCustomerName(
            String customerName) {

        return orders.stream()
                .filter(order -> order.getCustomerName()
                        .equalsIgnoreCase(customerName))
                .toList();
    }
 // Partial update order
    public FoodOrder patchOrder(Long id, FoodOrder updates) {

        FoodOrder existingOrder = getOrderById(id);

        if (updates.getCustomerName() != null) {
            existingOrder.setCustomerName(updates.getCustomerName());
        }

        if (updates.getFoodItem() != null) {
            existingOrder.setFoodItem(updates.getFoodItem());
        }

        if (updates.getQuantity() > 0) {
            existingOrder.setQuantity(updates.getQuantity());
        }

        if (updates.getPrice() != null) {
            existingOrder.setPrice(updates.getPrice());
        }

        return existingOrder;
    }
}

