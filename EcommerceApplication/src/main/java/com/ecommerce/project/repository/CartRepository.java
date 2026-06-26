package com.ecommerce.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.project.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	@Query("select c from Cart c where c.user.email = ?1")
	public Cart findCartByEmail(String email);

	@Query("select c from Cart c where c.user.email = ?1 AND c.cartId = ?2")
	public Cart findCartByEmailAndCartId(String emailId, Long cartId);


}
