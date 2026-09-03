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
import model.Order;
import model.User;
import service.OrderService;
import util.AppContext;
import util.RoleUtils;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	private OrderService orderService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        orderService = AppContext.getOrderService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");
		
		if(!RoleUtils.isLoggedIn(user)) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		List<Order> orders = orderService.findByUser(user.getId());
		
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("orders.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
