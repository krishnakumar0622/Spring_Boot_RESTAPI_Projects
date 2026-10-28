
package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.FoodOrder;
import com.example.demo.service.FoodOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class FoodOrderController {

    private final FoodOrderService foodOrderService;

    public FoodOrderController(FoodOrderService foodOrderService) {
        this.foodOrderService = foodOrderService;
    }

    // 1. Add a new order
    @PostMapping
    public ResponseEntity<FoodOrder> addOrder(
            @Valid @RequestBody FoodOrder order) {

        FoodOrder savedOrder = foodOrderService.addOrder(order);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedOrder);
    }

    // 2. Get all orders
    @GetMapping
    public ResponseEntity<List<FoodOrder>> getAllOrders() {
        return ResponseEntity.ok(
                foodOrderService.getAllOrders());
    }

    // 3. Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodOrder> getOrderById(
            @PathVariable Long id) {

        FoodOrder order = foodOrderService.getOrderById(id);

        return ResponseEntity.ok(order);
    }

    // 4. Update complete order
    @PutMapping("/{id}")
    public ResponseEntity<FoodOrder> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody FoodOrder order) {

        FoodOrder updatedOrder =
                foodOrderService.updateOrder(id, order);

        return ResponseEntity.ok(updatedOrder);
    }

    // 5. Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(
            @PathVariable Long id) {

        boolean deleted = foodOrderService.deleteOrder(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order not found with id: " + id);
        }

        return ResponseEntity.ok("Order deleted successfully");
    }

    // 6. Count total orders
    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalOrders() {
        return ResponseEntity.ok(
                foodOrderService.getTotalOrders());
    }

    // 7. Get orders by customer name
    @GetMapping("/customer/{customerName}")
    public ResponseEntity<List<FoodOrder>> getOrdersByCustomerName(
            @PathVariable String customerName) {

        return ResponseEntity.ok(
                foodOrderService.getOrdersByCustomerName(customerName));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<FoodOrder> patchOrder(
            @PathVariable Long id,
            @RequestBody FoodOrder updates) {

        return ResponseEntity.ok(
                foodOrderService.patchOrder(id, updates));
    }
}
