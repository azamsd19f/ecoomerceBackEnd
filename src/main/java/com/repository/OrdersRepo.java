package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Orders;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {

	List<Orders> findByOrderStatus(String orderStatus);

}
