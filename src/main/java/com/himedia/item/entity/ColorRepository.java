package com.himedia.item.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Integer> {
	Color findByName(String name);
}
