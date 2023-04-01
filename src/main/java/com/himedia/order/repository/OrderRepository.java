package com.himedia.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.order.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}
