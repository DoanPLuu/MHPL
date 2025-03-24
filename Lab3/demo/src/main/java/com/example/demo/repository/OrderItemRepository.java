package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi.product, SUM(oi.quantity) as total " +
           "FROM OrderItem oi " +
           "GROUP BY oi.product " +
           "ORDER BY total DESC")
    List<Object[]> findBestSellingProduct();
}