package service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.CartItemDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;
import model.CartItem;
import model.Order;
import model.OrderItem;
import model.OrderStatus;
import model.Product;
import model.User;
import util.HibernateUtil;

public class CheckoutService {
	private CartItemDAO cartItemDAO = new CartItemDAO();
	private OrderDAO orderDAO = new OrderDAO();
	private OrderItemDAO orderItemDAO = new OrderItemDAO();
	
	public Order checkout(int userId) {
		Session session = null;
		Transaction tr = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			tr = session.beginTransaction();
			
			User user = session.get(User.class, userId);
			
			if(user == null) {
				throw new IllegalArgumentException("User not found!");
			}
			
			List<CartItem> cartItems = cartItemDAO.findByUser(userId, session);
			
			if(cartItems == null || cartItems.isEmpty()) {
				throw new IllegalStateException("Cart is empty");
			}
			
			for(CartItem cartItem : cartItems) {
				
				Product product = cartItem.getProduct();
				
				int requestedQuantity = cartItem.getQuantity();
				int stockQuantity = product.getQuantity();
				
				if(requestedQuantity > stockQuantity) {
					throw new IllegalStateException("Not enough stock for product: " + product.getName());
				}
			}
			
			BigDecimal total = BigDecimal.ZERO;
			for(CartItem item : cartItems) {
				BigDecimal subtotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
				total = total.add(subtotal);
			}
			
			Order order = new Order(user, new Date(System.currentTimeMillis()), total, OrderStatus.PENDING, user.getAddress());
			orderDAO.save(order, session);
			
			for(CartItem item : cartItems) {
				Product product = item.getProduct();
				
				OrderItem orderItem = new OrderItem(order, product, item.getQuantity(), item.getProduct().getPrice());
				orderItemDAO.save(orderItem, session);
				
				int newQuantity = product.getQuantity() - item.getQuantity();
				product.setQuantity(newQuantity);
			}
			
			for(CartItem item : cartItems) {
				cartItemDAO.delete(item, session);
			}
			
			tr.commit();
			return order;
			
		} catch (Exception e) {
			if(tr != null) tr.rollback();
			throw e;
		}
		finally {
			if(session != null) session.close();
		}
	}
}
