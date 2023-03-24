package com.himedia.cartitem;

import com.himedia.cart.Cart;
import com.himedia.item.entity.Item;

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
	@JoinColumn(name ="cart_id")
	private Cart cart;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_id")
	private Item item;
	
	private int count; //각 상품의 갯수
	
	public static CartItem createCartItem(Cart cart, Item item, int amount) {
		CartItem cartItem = new CartItem();
		cartItem.setCart(cart);
		cartItem.setItem(item);
		cartItem.setCount(amount);
		return cartItem;
	}
	
	//이미 담겨있는 물건 또 담을 경우 수량 증가
	public void addCount(int count) {
		this.count += count;
	}
	
	
}
