package com.ecommerce.project.service;

import java.util.List;

import com.ecommerce.project.DTO.CartDTO;

import jakarta.transaction.Transactional;
public interface CartService {

	CartDTO addCart(Long productId, Integer quantity);

	 List<CartDTO> getAllCarts();

	 CartDTO getCart(String emailId, Long cartId);

	 @Transactional
	 CartDTO updateProductQuantityInCart(Long productId, int i);

	 String deleteProductFromCart(Long cartId, Long productId);

}
