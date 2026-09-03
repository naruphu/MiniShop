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
import exception.AppException;
import model.CartItem;
import model.Order;
import model.OrderItem;
import model.OrderStatus;
import model.Role;
import model.User;
import service.CheckoutService;
import util.AppContext;
import util.RoleUtils;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CheckoutService checkOutService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutServlet() {
    	checkOutService = AppContext.getCheckOutService();
        
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
		
		// 1. Lấy user đang đăng nhập
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");
		
		if(RoleUtils.isAdmin(user)) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		
		try {
			checkOutService.checkout(user.getId());
			session.setAttribute(
	                "message",
	                "Checkout successfully!"
	        );


	        response.sendRedirect("CartServlet");
		} catch (AppException e) {
			request.getSession().setAttribute(
			        "message",
			        e.getMessage()
			    );

			    response.sendRedirect("CartServlet");
		}

		
	}

}
