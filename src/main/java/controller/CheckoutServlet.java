package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CartItemDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;
import model.CartItem;
import model.Order;
import model.OrderItem;
import model.OrderStatus;
import model.Role;
import model.User;
import service.CheckoutService;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		OrderDAO orderDAO = new OrderDAO();
//		CartItemDAO cartItemDAO = new CartItemDAO();
//		OrderItemDAO orderItemDAO = new OrderItemDAO();
		
		
		// 1. Lấy user đang đăng nhập
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");
		
		if(user == null || user.getRole() == Role.ADMIN) {
			response.sendRedirect("login.jsp");
			return;
		}
		
	
		CheckoutService checkoutService = new CheckoutService();
		
		try {
			checkoutService.checkout(user.getId());
			session.setAttribute(
	                "message",
	                "Checkout successfully!"
	        );


	        response.sendRedirect("CartServlet");
		} catch (Exception e) {
			request.getSession().setAttribute(
			        "message",
			        e.getMessage()
			    );

			    response.sendRedirect("CartServlet");
		}
		
//		// 2. Lấy cart của user
//		List<CartItem> cartItems = cartItemDAO.findByUser(user.getId());
//		
//		if(cartItems == null || cartItems.isEmpty()) {
//			session.setAttribute("message", "Your cart is empty!");
//			response.sendRedirect("cart.jsp");
//			return;
//		}
//		// 3. Tính tổng tiền
//		BigDecimal total = BigDecimal.ZERO;
//		for(CartItem item : cartItems) {
//			BigDecimal subtotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
//			total = total.add(subtotal);
//		}
//		
//		// 4. Tạo Order
//		Order order = new Order(user, new Date(System.currentTimeMillis()), total, OrderStatus.PENDING, user.getAddress());
//		// 5. Lưu Order
//		orderDAO.save(order);
//
//		// 6. Tạo OrderItem
//		for(CartItem item : cartItems) {
//			OrderItem orderItem = new OrderItem(order, item.getProduct(), item.getQuantity(), item.getProduct().getPrice());
//			 // 7. Lưu OrderItem
//			orderItemDAO.save(orderItem);
//		}
//		
//		// 8. Xóa cart sau khi mua
//		for(CartItem item : cartItems) {
//			cartItemDAO.delete(item);
//		}
		
//		// 9. Redirect
//		session.setAttribute(
//                "message",
//                "Checkout successfully!"
//        );
//
//
//        response.sendRedirect("CartServlet");
		
	}

}
