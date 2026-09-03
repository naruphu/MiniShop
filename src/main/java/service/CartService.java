package service;

import dao.CartItemDAO;
import exception.CartException;
import model.CartItem;
import model.Product;
import model.User;

public class CartService {
	private CartItemDAO cartItemDAO;
	
	public CartService(CartItemDAO cartItemDAO) {
		this.cartItemDAO = cartItemDAO;
	}
	
	public void updateQuantity(int id, int newQuantity) {
		CartItem item = cartItemDAO.selectById(id);
		
		if(item == null) throw new CartException("Item not found");
		
		if(newQuantity <= 0) throw new CartException("Quantity must be greater than 0");
		
		int stock = item.getProduct().getQuantity();
		
		if(newQuantity > stock) throw new CartException("Only " + stock + " available");
		
		item.setQuantity(newQuantity);
		
		cartItemDAO.update(item);
	
	}
	
	public void addToCart(User user, Product product, int quantity) {
		if(quantity <= 0) throw new CartException("Quantity must be greater than 0");
		
		CartItem existingItem = cartItemDAO.findByUserAndProduct(user.getId(), product.getId());
		
		if(existingItem == null) {
			if(quantity > product.getQuantity()) throw new CartException("Only " + product.getQuantity() + " items available");
			
			CartItem cartItem = new CartItem(user, product, quantity);
			
			cartItemDAO.save(cartItem);
		}else {
			int newQuantity = existingItem.getQuantity() + quantity;
			
			if(newQuantity > product.getQuantity()) throw new CartException("Only " + product.getQuantity() + " items available and it had already been in your cart");
			
			existingItem.setQuantity(newQuantity);
			cartItemDAO.update(existingItem);
 		}
	}
	
	public void removeItem(int cartItemId) {
		CartItem cartItem = cartItemDAO.selectById(cartItemId);
		
		if(cartItem == null) throw new CartException("Cart item not found");
		
		cartItemDAO.delete(cartItem);
	}
}
