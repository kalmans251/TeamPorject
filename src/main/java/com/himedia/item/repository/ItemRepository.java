package com.himedia.item.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.himedia.item.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
	List<Item> findBySubject(String subject);
}
