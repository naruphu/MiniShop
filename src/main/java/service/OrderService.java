package service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.OrderDAO;
import exception.OrderException;
import model.Order;
import model.OrderItem;
import model.OrderStatus;
import model.Product;
import util.HibernateUtil;

public class OrderService {
	private OrderDAO orderDAO;
	
	public OrderService(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	
	public List<Order> findByUser(int id) {
		return orderDAO.findByUser(id);	}
	
	
	public void updateStatus(int orderId, OrderStatus newStatus) {
		Session session = null;
		Transaction tr = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tr = session.beginTransaction();
			
			Order order = orderDAO.selectById(orderId, session);
			
			if(order == null) {
				throw new OrderException("Order not found");
			}
			
			OrderStatus currentStatus = order.getStatus();
			
			if(!isValidTransition(currentStatus, newStatus)) {
				throw new OrderException("Can not change order from " + currentStatus + " to " + newStatus);
			}
			
			if(newStatus == OrderStatus.CANCELLED) {
				for(OrderItem item : order.getOrderItems()) {
					Product product = item.getProduct();
					
					int restoredQuantity = product.getQuantity() + item.getQuantity();
					
					product.setQuantity(restoredQuantity);
				}
			}
			order.setStatus(newStatus);
			
			tr.commit();
			}
			catch (RuntimeException e) {

			    if(tr != null) {
			        tr.rollback();
			    }

			    e.printStackTrace();

			    throw e;
			}
			finally {

			    if(session != null) {
			        session.close();
			    }
			}
	}
	
	private boolean isValidTransition(OrderStatus currentStatus, OrderStatus newStatus) {
		switch (currentStatus) {
		case PENDING:
			return newStatus == OrderStatus.CONFIRMED || newStatus == OrderStatus.CANCELLED;
		case CONFIRMED:
            return newStatus == OrderStatus.SHIPPING || newStatus == OrderStatus.CANCELLED;
	
		case SHIPPING:
            return newStatus == OrderStatus.COMPLETED;

        case COMPLETED:
            return false;

        case CANCELLED:
            return false;

        default:
            return false;

		}
	}
}
