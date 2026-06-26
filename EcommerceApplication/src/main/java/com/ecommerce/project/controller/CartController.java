package com.ecommerce.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.DTO.CartDTO;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.repository.CartItemRepository;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.service.CartService;
import com.ecommerce.project.utils.AuthUtil;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private AuthUtil authUtil;
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/carts/products/{productId}/quantity/{quantity}")
	ResponseEntity<CartDTO> addCart(@PathVariable Long productId, @PathVariable Integer quantity ){
		CartDTO cartDto = cartService.addCart(productId, quantity);
		return new ResponseEntity<CartDTO>(cartDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/carts")
	ResponseEntity<List<CartDTO>> getAllCarts(){
		List<CartDTO> carts = cartService.getAllCarts();
		return new ResponseEntity<List<CartDTO>>(carts, HttpStatus.OK);
	}
	
	@GetMapping("/carts/users/cart")
	ResponseEntity<CartDTO> getCartById(){
		String emailId = authUtil.loggedInEmail();
		Cart cart = cartRepository.findCartByEmail(emailId);
		Long cartId = cart.getCartId();
		CartDTO cartDto = cartService.getCart(emailId, cartId);
		return new ResponseEntity<CartDTO>(cartDto, HttpStatus.OK);
	}
	
	@PutMapping("/cart/products/{productId}/quantity/{operation}")
	ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long productId,
			@PathVariable String operation){
		CartDTO cartDTO = cartService.updateProductQuantityInCart(productId, operation.equalsIgnoreCase("delete") ? -1 : 1);
		return new ResponseEntity<>(cartDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/carts/{cartId}/product/{productId}")
	ResponseEntity<String> deleteCartProduct(@PathVariable Long cartId, @PathVariable Long productId){
		String status = cartService.deleteProductFromCart(cartId, productId);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
	
}
