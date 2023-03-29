package com.himedia.cartitem;

import java.time.LocalDateTime;

import com.himedia.item.entity.ItemSellingInform;
import com.himedia.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class CartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_item_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="member_idx")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="itemSellingInform_id")
	private ItemSellingInform itemSellingInform;
	
	private Integer count; //각 상품의 갯수
	
	private LocalDateTime regDate;
	public static CartItem createCartItem(Member member, ItemSellingInform itemSellingInform, Integer amount) {
		CartItem cartItem = new CartItem();
		cartItem.setMember(member);
		cartItem.setItemSellingInform(itemSellingInform);
		cartItem.setCount(amount);
		return cartItem;
	}
	
	//이미 담겨있는 물건 또 담을 경우 수량 증가
	public void addCount(int count) {
		this.count += count;
	}
	
	
}
