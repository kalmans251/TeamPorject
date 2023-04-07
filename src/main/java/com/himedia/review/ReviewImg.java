package com.himedia.review;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ReviewImg {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long idx;


private String url;

@ManyToOne(optional = false)
@JoinColumn(name = "review", nullable = false)
private Review review;
}
