package com.ecommerce.project.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.DTO.CartDTO;
import com.ecommerce.project.DTO.ProductDTO;
import com.ecommerce.project.Exception.APIException;
import com.ecommerce.project.Exception.ResourceNotFoundException;
import com.ecommerce.project.model.Cart;
import com.ecommerce.project.model.CartItem;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.repository.CartItemRepository;
import com.ecommerce.project.repository.CartRepository;
import com.ecommerce.project.repository.ProductRepository;
import com.ecommerce.project.utils.AuthUtil;

import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private AuthUtil authUtil;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public CartDTO addCart(Long productId, Integer quantity) {
		Cart cart = createCart();
		
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("product", "productId", productId));;
		CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCartId(), productId);
				
		
		if(cartItem != null) {
			throw new APIException("Product "+ product.getProductName() + " already exists." );
		}
		
		if(product.getQuantity() == 0) {
			throw new APIException(product.getProductName() + " is not available.");
		}
		if(product.getQuantity() < quantity){
			throw new APIException("Please make an order of the " + product.getProductName() + " less than or equal to "+ product.getQuantity());	
		}
		
		CartItem newCartItem = new CartItem();
		newCartItem.setProduct(product);
		newCartItem.setCart(cart);
		newCartItem.setDiscount(product.getDiscount());
		newCartItem.setProductPrice(product.getSpecialPrice());
		newCartItem.setQuantity(quantity);
		
		cartItemRepository.save(newCartItem);
		product.setQuantity(product.getQuantity()-quantity);
		cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));
		cartRepository.save(cart);
		CartDTO cartDto = modelMapper.map(cart, CartDTO.class);
		
		List<CartItem> cartItems = cart.getCartItems();
		
		Stream<ProductDTO> productDtoStream = cartItems.stream().map(item -> {
			ProductDTO productDto = modelMapper.map(item.getProduct(), ProductDTO.class);
			productDto.setQuantity(item.getQuantity());
			return productDto;
		});
		cartDto.setProducts(productDtoStream.toList());
		return cartDto;
		
	}

	public Cart createCart() {
		Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInUser().getEmail());
		if(userCart != null) {
			return userCart;
		}
		 Cart cart = new Cart();
		 cart.setTotalPrice(0.0);
		 cart.setUser(authUtil.loggedInUser());
		 Cart newCart = cartRepository.save(cart);
		 return newCart;
		 
	}

	@Override
	public List<CartDTO> getAllCarts() {
		List<Cart> carts = cartRepository.findAll();
		if(carts.size() == 0) {
			throw new APIException("No carts Found");
		}
		
		List<CartDTO> cartDtos = carts.stream().map(cart -> {
			CartDTO cartDto = modelMapper.map(cart, CartDTO.class);
			List<ProductDTO> productDtos = cart.getCartItems().stream()
					.map(p -> modelMapper.map(p.getProduct(), ProductDTO.class))
					.collect(Collectors.toList());
			cartDto.setProducts(productDtos);
			return cartDto;
		}).collect(Collectors.toList());
		
		return cartDtos;
	}

	@Override
	public CartDTO getCart(String emailId, Long cartId) {
		Cart cart = cartRepository.findCartByEmailAndCartId(emailId, cartId);
		if(cart == null)
			throw new ResourceNotFoundException("cart", "cartId", cartId);
		cart.getCartItems().stream().forEach(c -> c.getProduct().setQuantity(c.getQuantity()));
		List<ProductDTO> products = cart.getCartItems().stream().map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());
		
		CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
		cartDTO.setProducts(products);
		return cartDTO;
	}

	@Transactional
	@Override
	public CartDTO updateProductQuantityInCart(Long productId, int quantity) {
		
		String emailId = authUtil.loggedInEmail();
		Cart userCart = cartRepository.findCartByEmail(emailId);
		Long cartId = userCart.getCartId();
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("cart", "cartId", cartId));
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("product", "productId", productId));
		if(product.getQuantity() == 0) {
			throw new APIException(product.getProductName() + " is not available");
		}
		if(product.getQuantity() < quantity) {
			throw new APIException("Please, make an order of the "+ product.getProductName()
			+" less than or equal to the quanity "+ product.getQuantity());
		}
		CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);
		if(cartItem == null) {
			throw new APIException("Product "+product.getProductName()+" not exists." );
		}
		
		int newQuantity = cartItem.getQuantity() + quantity;
		if(newQuantity < 0) {
			throw new APIException("The Resulting quantity should not be negative.");
		}
		if(newQuantity == 0) {
			deleteProductFromCart(cartId, productId);
		}
		cartItem.setProductPrice(product.getSpecialPrice());
		cartItem.setQuantity(cartItem.getQuantity() + quantity);
		cartItem.setDiscount(product.getDiscount());
		cart.setTotalPrice(cart.getTotalPrice() + (cart.getTotalPrice() * quantity));
		cartRepository.save(cart);
		CartItem updatedCartItem = cartItemRepository.save(cartItem);
		if(updatedCartItem.getQuantity() == 0) {
			cartItemRepository.deleteById(updatedCartItem.getCartItemId());
		}
		CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
		List<CartItem> cartItems = cart.getCartItems();
		Stream<ProductDTO> products = cartItems.stream().map(item -> {
			ProductDTO productDTO = modelMapper.map(item.getProduct(), ProductDTO.class);
			productDTO.setQuantity(item.getQuantity());
			return productDTO;
		});
		cartDTO.setProducts(products.toList());
		return null;
	}

	@Override
	public String deleteProductFromCart(Long cartId, Long productId) {
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() ->  new ResourceNotFoundException("Cart", "cartId", cartId));
		CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);
		if(cartItem == null) {
			throw new ResourceNotFoundException("Product", "productId", productId);
		}
		cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice()*cartItem.getQuantity()));
		cartItemRepository.deleteCartItemByProductIdAndCartId(cartId, productId);
		return "Product " + productId + " has been removed from the cart " + cartId;
	}
}
