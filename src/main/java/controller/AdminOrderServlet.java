package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.OrderDAO;
import exception.AppException;
import model.Order;
import model.OrderStatus;
import model.Role;
import model.User;
import service.OrderService;
import util.AppContext;
import util.RoleUtils;

/**
 * Servlet implementation class AdminOrderServlet
 */
@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminOrderServlet() {
		orderService = AppContext.getOrderService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		OrderDAO orderdao = new OrderDAO();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");

		if (!RoleUtils.isAdmin(user)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		List<Order> orders = orderdao.findAll();

		request.setAttribute("orders", orders);
		request.getRequestDispatcher("admin-orders.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");

		if (!RoleUtils.isAdmin(user)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		try {
			String orderIdStr = request.getParameter("orderId");
			String statusStr = request.getParameter("status");

			int orderId = Integer.parseInt(orderIdStr);

			OrderStatus newStatus = OrderStatus.valueOf(statusStr);
			// convert từ "SHIPPED" thành OrderStatus.SHIPPED

			orderService.updateStatus(orderId, newStatus);

			request.getSession().setAttribute("message", "Update status successfully!");
		} catch (AppException e) {
			request.getSession().setAttribute("message", e.getMessage());
		} finally {
			response.sendRedirect("AdminOrderServlet");
		}

	}

}
