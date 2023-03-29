package com.himedia.item.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Integer> {
	Size findByName(String name);
}
